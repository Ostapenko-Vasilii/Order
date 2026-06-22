package ru.orderdorms.features.services.presentation.faq

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
import org.koin.mp.KoinPlatform
import ru.orderdorms.features.services.domain.model.faq.FaqQuestion
import ru.orderdorms.features.services.domain.usecase.GetFaqCategoriesUseCase

class FaqViewModel(
    private val getFaqCategoriesUseCase: GetFaqCategoriesUseCase,
) {
    private val scope: CoroutineScope = MainScope()

    var state by mutableStateOf(FaqState())
        private set

    init {
        loadCategories()
    }

    fun loadCategories() {
        state = state.copy(isLoading = true, error = null)
        getFaqCategoriesUseCase()
            .onEach { result ->
                result.fold(
                    left = { error ->
                        state = state.copy(isLoading = false, error = error)
                    },
                    right = { categories ->
                        state = state.copy(isLoading = false, categories = categories)
                    },
                )
            }
            .launchIn(scope)
    }

    fun onSearchQueryChange(query: String) {
        state = state.copy(searchQuery = query)
    }

    fun onQuestionClick(question: FaqQuestion?) {
        state = state.copy(selectedQuestion = question)
    }

    fun dispose() {
        scope.cancel()
    }
}

@Composable
fun rememberFaqViewModel(): FaqViewModel {
    val koin = KoinPlatform.getKoin()
    val viewModel = remember {
        FaqViewModel(
            getFaqCategoriesUseCase = koin.get()
        )
    }
    DisposableEffect(viewModel) {
        onDispose { viewModel.dispose() }
    }
    return viewModel
}
