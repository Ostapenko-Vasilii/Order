package ru.orderdorms.features.services.presentation

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
import ru.orderdorms.features.services.domain.usecase.GetQuickActionsUseCase
import ru.orderdorms.features.services.domain.usecase.GetServicesUseCase
import ru.orderdorms.features.services.domain.usecase.ToggleQuickActionUseCase

class ServicesViewModel(
    private val getServicesUseCase: GetServicesUseCase,
    private val getQuickActionsUseCase: GetQuickActionsUseCase,
    private val toggleQuickActionUseCase: ToggleQuickActionUseCase
) {
    private val scope: CoroutineScope = MainScope()

    var state by mutableStateOf(ServicesState())
        private set

    init {
        getServicesUseCase()
            .onEach { state = state.copy(allServices = it) }
            .launchIn(scope)

        getQuickActionsUseCase()
            .onEach { state = state.copy(quickActions = it) }
            .launchIn(scope)
    }

    fun onSearchQueryChange(query: String) {
        state = state.copy(searchQuery = query)
    }

    fun toggleQuickAction(serviceId: String) {
        scope.launch {
            toggleQuickActionUseCase(serviceId)
        }
    }

    fun dispose() {
        scope.cancel()
    }
}

@Composable
fun rememberServicesViewModel(): ServicesViewModel {
    val koin = KoinPlatform.getKoin()
    val viewModel = remember {
        ServicesViewModel(
            getServicesUseCase = koin.get(),
            getQuickActionsUseCase = koin.get(),
            toggleQuickActionUseCase = koin.get()
        )
    }
    DisposableEffect(viewModel) {
        onDispose { viewModel.dispose() }
    }
    return viewModel
}
