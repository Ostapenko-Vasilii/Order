package ru.orderdorms.features.home.domain.repository
import ru.orderdorms.features.home.domain.model.Service
interface QuickActionsRepository {
    fun getQuickActions(): List<Service>
}
