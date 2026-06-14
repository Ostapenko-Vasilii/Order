package ru.orderdorms.features.services.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.orderdorms.core.domain.model.Service
import ru.orderdorms.features.services.domain.repository.ServicesRepository

class GetServicesUseCase(private val repository: ServicesRepository) {
    operator fun invoke(): Flow<List<Service>> = repository.getAllServices()
}

class GetQuickActionsUseCase(private val repository: ServicesRepository) {
    operator fun invoke(): Flow<List<Service>> = repository.getQuickActions()
}

class ToggleQuickActionUseCase(private val repository: ServicesRepository) {
    suspend operator fun invoke(serviceId: String) = repository.toggleQuickAction(serviceId)
}
