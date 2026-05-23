package ru.orderdorms.features.services.presentation

import ru.orderdorms.features.services.domain.model.Service

data class ServicesState(
    val allServices: List<Service> = emptyList(),
    val quickActions: List<Service> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
) {
    val filteredOtherServices: List<Service>
        get() = allServices.filter { service ->
            !service.isQuickAction && service.title.contains(searchQuery, ignoreCase = true)
        }
}
