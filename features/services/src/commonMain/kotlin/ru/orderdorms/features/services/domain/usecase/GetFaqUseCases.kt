package ru.orderdorms.features.services.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.services.domain.model.faq.FaqCategory
import ru.orderdorms.features.services.domain.repository.FaqRepository

class GetFaqCategoriesUseCase(private val repository: FaqRepository) {
    operator fun invoke(): Flow<Either<DomainError, List<FaqCategory>>> = repository.getFaqCategories()
}
