package ru.orderdorms.features.news.presentation

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
import ru.orderdorms.features.news.domain.model.NewsType
import ru.orderdorms.features.news.domain.usecase.GetNewsUseCase

class NewsViewModel(
    private val getNewsUseCase: GetNewsUseCase
) {
    private val scope: CoroutineScope = MainScope()

    var state by mutableStateOf(NewsState())
        private set

    init {
        loadNews()
    }

    fun loadNews() {
        state = state.copy(isLoading = true, error = null)
        getNewsUseCase()
            .onEach { result ->
                result.fold(
                    left = { error -> state = state.copy(isLoading = false, error = error) },
                    right = { news -> state = state.copy(isLoading = false, news = news) }
                )
            }
            .launchIn(scope)
    }

    fun onTypeSelect(type: NewsType?) {
        state = state.copy(selectedType = type)
    }

    fun dispose() {
        scope.cancel()
    }
}

@Composable
fun rememberNewsViewModel(): NewsViewModel {
    val koin = KoinPlatform.getKoin()
    val viewModel = remember {
        NewsViewModel(getNewsUseCase = koin.get())
    }
    DisposableEffect(viewModel) {
        onDispose { viewModel.dispose() }
    }
    return viewModel
}
