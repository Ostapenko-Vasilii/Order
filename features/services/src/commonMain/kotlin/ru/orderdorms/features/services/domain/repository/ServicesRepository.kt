package ru.orderdorms.features.services.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.orderdorms.core.domain.model.Service

interface ServicesRepository {
    fun getAllServices(): Flow<List<Service>>
    fun getQuickActions(): Flow<List<Service>>
    suspend fun toggleQuickAction(serviceId: String)
    suspend fun setQuickActions(serviceIds: List<String>)
}