package ru.orderdorms.features.events.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.events.domain.model.Event
import ru.orderdorms.features.events.domain.repository.EventsRepository

class EventsViewModel(
    private val repository: EventsRepository
) {
    private val scope: CoroutineScope = MainScope()

    var state by mutableStateOf(EventsUiState())
        private set

    init {
        loadEvents()
    }

    fun loadEvents() {
        scope.launch {
            state = state.copy(isLoading = true, errorMessage = null)
            when (val result = repository.getEvents()) {
                is Either.Left -> {
                    state = state.copy(
                        isLoading = false,
                        errorMessage = "Не удалось загрузить события"
                    )
                }
                is Either.Right -> {
                    state = state.copy(
                        isLoading = false,
                        events = result.value,
                        filteredEvents = filterEvents(result.value, state.searchQuery)
                    )
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        state = state.copy(
            searchQuery = query,
            filteredEvents = filterEvents(state.events, query)
        )
    }

    fun onEventSelected(event: Event?) {
        state = state.copy(selectedEvent = event)
    }

    private fun filterEvents(events: List<Event>, query: String): List<Event> {
        if (query.isBlank()) return events
        return events.filter { it.title.contains(query, ignoreCase = true) }
    }

    fun dispose() {
        scope.cancel()
    }
}

@Composable
fun rememberEventsViewModel(): EventsViewModel {
    val koin = KoinPlatform.getKoin()
    val viewModel = remember { koin.get<EventsViewModel>() }
    DisposableEffect(viewModel) {
        onDispose { viewModel.dispose() }
    }
    return viewModel
}
