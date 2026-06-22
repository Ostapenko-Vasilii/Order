package ru.orderdorms.features.services.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.services.domain.model.request.MaintenanceRequest

interface RequestRepository {
    fun getUserRequests(): Flow<Either<DomainError, List<MaintenanceRequest>>>
    suspend fun submitRequest(roomNumber: String, description: String, note: String): Either<DomainError, Unit>
}
