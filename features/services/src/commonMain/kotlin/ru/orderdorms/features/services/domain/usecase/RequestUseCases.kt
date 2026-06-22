package ru.orderdorms.features.services.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.services.domain.model.request.MaintenanceRequest
import ru.orderdorms.features.services.domain.repository.RequestRepository

class GetUserRequestsUseCase(private val repository: RequestRepository) {
    operator fun invoke(): Flow<Either<DomainError, List<MaintenanceRequest>>> = repository.getUserRequests()
}

class SubmitRequestUseCase(private val repository: RequestRepository) {
    suspend operator fun invoke(roomNumber: String, description: String, note: String): Either<DomainError, Unit> =
        repository.submitRequest(roomNumber, description, note)
}
