package ru.orderdorms.features.services.domain.repository

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either

interface CleaningRepository {
    suspend fun getCleaningInfo(): Either<DomainError, String>
}
