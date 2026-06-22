package ru.orderdorms.features.services.presentation.complaints

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
import ru.orderdorms.features.services.domain.usecase.GetUserComplaintsUseCase
import ru.orderdorms.features.services.domain.usecase.SubmitComplaintUseCase

class ComplaintsViewModel(
    private val getUserComplaintsUseCase: GetUserComplaintsUseCase,
    private val submitComplaintUseCase: SubmitComplaintUseCase
) {
    private val scope: CoroutineScope = MainScope()

    var state by mutableStateOf(ComplaintsState())
        private set

    init {
        loadComplaints()
    }

    fun loadComplaints() {
        state = state.copy(isLoading = true, error = null)
        getUserComplaintsUseCase()
            .onEach { result ->
                result.fold(
                    left = { error ->
                        state = state.copy(isLoading = false, error = error)
                    },
                    right = { complaints ->
                        state = state.copy(isLoading = false, complaints = complaints)
                    }
                )
            }
            .launchIn(scope)
    }

    fun onTextChange(value: String) {
        state = state.copy(text = value)
    }

    fun submitComplaint() {
        if (state.text.isBlank()) return

        scope.launch {
            state = state.copy(isSubmitting = true, error = null)
            val result = submitComplaintUseCase(text = state.text)
            result.fold(
                left = { error ->
                    state = state.copy(isSubmitting = false, error = error)
                },
                right = {
                    state = state.copy(isSubmitting = false, text = "")
                    loadComplaints()
                }
            )
        }
    }

    fun dispose() {
        scope.cancel()
    }
}

@Composable
fun rememberComplaintsViewModel(): ComplaintsViewModel {
    val koin = KoinPlatform.getKoin()
    val viewModel = remember {
        ComplaintsViewModel(
            getUserComplaintsUseCase = koin.get(),
            submitComplaintUseCase = koin.get()
        )
    }
    DisposableEffect(viewModel) {
        onDispose { viewModel.dispose() }
    }
    return viewModel
}
