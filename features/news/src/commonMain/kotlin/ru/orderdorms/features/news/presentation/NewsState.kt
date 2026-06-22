package ru.orderdorms.features.news.presentation

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.features.news.domain.model.NewsItem
import ru.orderdorms.features.news.domain.model.NewsType

data class NewsState(
    val news: List<NewsItem> = emptyList(),
    val selectedType: NewsType? = null, // null means "All"
    val isLoading: Boolean = false,
    val error: DomainError? = null
) {
    val filteredNews: List<NewsItem>
        get() = if (selectedType == null) news else news.filter { it.type == selectedType }
}
