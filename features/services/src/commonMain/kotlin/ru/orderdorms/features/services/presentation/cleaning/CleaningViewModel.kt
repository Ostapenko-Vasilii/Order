package ru.orderdorms.features.services.presentation.cleaning

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
import ru.orderdorms.features.services.domain.usecase.GetCleaningInfoUseCase

class CleaningViewModel(
    private val getCleaningInfoUseCase: GetCleaningInfoUseCase
) {
    private val scope: CoroutineScope = MainScope()

    var state by mutableStateOf(CleaningState())
        private set

    init {
        loadCleaningInfo()
    }

    fun loadCleaningInfo() {
        scope.launch {
            state = state.copy(isLoading = true, errorMessage = null)
            when (val result = getCleaningInfoUseCase()) {
                is Either.Left -> {
                    state = state.copy(
                        isLoading = false,
                        errorMessage = result.value.message
                    )
                }
                is Either.Right -> {
                    state = state.copy(
                        isLoading = false,
                        infoMarkdown = result.value
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
fun rememberCleaningViewModel(): CleaningViewModel {
    val koin = KoinPlatform.getKoin()
    val viewModel = remember {
        CleaningViewModel(
            getCleaningInfoUseCase = koin.get()
        )
    }
    DisposableEffect(viewModel) {
        onDispose { viewModel.dispose() }
    }
    return viewModel
}
