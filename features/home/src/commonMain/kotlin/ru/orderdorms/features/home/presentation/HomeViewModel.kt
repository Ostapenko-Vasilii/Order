package ru.orderdorms.features.home.presentation

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
import ru.orderdorms.features.home.domain.usecase.HomeUseCases

class HomeViewModel(
    private val homeUseCases: HomeUseCases,
) {
    private val scope: CoroutineScope = MainScope()

    var state by mutableStateOf(HomeUiState())
        private set

    init {
        refresh()
    }

    fun refresh() {
        scope.launch {
            state = state.copy(isLoading = true, errorMessage = null)
            when (val result = homeUseCases.loadDashboard()) {
                is Either.Left -> {
                    state = state.copy(
                        isLoading = false,
                        errorMessage = result.value.message,
                        dashboard = null,
                    )
                }

                is Either.Right -> {
                    state = state.copy(
                        isLoading = false,
                        dashboard = result.value,
                        errorMessage = null,
                    )
                }
            }
        }
    }

    fun dispose() {
        scope.cancel()
    }
}

@Composable
fun rememberHomeViewModel(): HomeViewModel {
    val koin = KoinPlatform.getKoin()
    val viewModel = remember {
        HomeViewModel(
            homeUseCases = koin.get(),
        )
    }
    DisposableEffect(viewModel) {
        onDispose { viewModel.dispose() }
    }
    return viewModel
}
