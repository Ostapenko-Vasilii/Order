package ru.orderdorms.features.news.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.news.domain.model.NewsItem

interface NewsRepository {
    fun getAllNews(): Flow<Either<DomainError, List<NewsItem>>>
}
