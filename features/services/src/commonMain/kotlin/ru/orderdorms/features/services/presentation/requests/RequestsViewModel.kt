package ru.orderdorms.features.services.presentation.requests

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform
import ru.orderdorms.features.services.domain.usecase.GetUserRequestsUseCase
import ru.orderdorms.features.services.domain.usecase.SubmitRequestUseCase

class RequestsViewModel(
    private val getUserRequestsUseCase: GetUserRequestsUseCase,
    private val submitRequestUseCase: SubmitRequestUseCase
) {
    private val scope: CoroutineScope = MainScope()

    var state by mutableStateOf(RequestsState())
        private set

    init {
        loadRequests()
    }

    fun loadRequests() {
        state = state.copy(isLoading = true, error = null)
        getUserRequestsUseCase()
            .onEach { result ->
                result.fold(
                    left = { error ->
                        state = state.copy(isLoading = false, error = error)
                    },
                    right = { requests ->
                        state = state.copy(isLoading = false, requests = requests)
                    }
                )
            }
            .launchIn(scope)
    }

    fun onRoomNumberChange(value: String) {
        state = state.copy(roomNumber = value)
    }

    fun onDescriptionChange(value: String) {
        state = state.copy(description = value)
    }

    fun onNoteChange(value: String) {
        state = state.copy(note = value)
    }

    fun submitRequest() {
        if (state.roomNumber.isBlank() || state.description.isBlank()) return

        scope.launch {
            state = state.copy(isSubmitting = true, error = null)
            val result = submitRequestUseCase(
                roomNumber = state.roomNumber,
                description = state.description,
                note = state.note
            )
            result.fold(
                left = { error ->
                    state = state.copy(isSubmitting = false, error = error)
                },
                right = {
                    state = state.copy(
                        isSubmitting = false,
                        roomNumber = "",
                        description = "",
                        note = ""
                    )
                    loadRequests()
                }
            )
        }
    }

    fun dispose() {
        scope.cancel()
    }
}

@Composable
fun rememberRequestsViewModel(): RequestsViewModel {
    val koin = KoinPlatform.getKoin()
    val viewModel = remember {
        RequestsViewModel(
            getUserRequestsUseCase = koin.get(),
            submitRequestUseCase = koin.get()
        )
    }
    DisposableEffect(viewModel) {
        onDispose { viewModel.dispose() }
    }
    return viewModel
}
