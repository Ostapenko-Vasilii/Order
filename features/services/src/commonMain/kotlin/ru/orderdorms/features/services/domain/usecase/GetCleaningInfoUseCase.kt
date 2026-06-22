package ru.orderdorms.features.services.domain.usecase

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.services.domain.repository.CleaningRepository

class GetCleaningInfoUseCase(
    private val repository: CleaningRepository
) {
    suspend operator fun invoke(): Either<DomainError, String> {
        return repository.getCleaningInfo()
    }
}
