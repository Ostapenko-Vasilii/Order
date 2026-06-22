package ru.orderdorms.features.services.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.services.domain.model.faq.FaqCategory

interface FaqRepository {
    fun getFaqCategories(): Flow<Either<DomainError, List<FaqCategory>>>
}
