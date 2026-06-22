package ru.orderdorms.features.news.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.news.domain.model.NewsItem
import ru.orderdorms.features.news.domain.repository.NewsRepository

class GetNewsUseCase(private val repository: NewsRepository) {
    operator fun invoke(): Flow<Either<DomainError, List<NewsItem>>> = repository.getAllNews()
}
