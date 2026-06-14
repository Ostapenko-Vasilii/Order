package ru.orderdorms.features.home.domain.repository

import ru.orderdorms.core.domain.model.Service
interface QuickActionsRepository {
    fun getQuickActions(): List<Service>
}