package ru.orderdorms.features.services.presentation.requests

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.features.services.domain.model.request.MaintenanceRequest

data class RequestsState(
    val requests: List<MaintenanceRequest> = emptyList(),
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val error: DomainError? = null,
    val roomNumber: String = "",
    val description: String = "",
    val note: String = ""
)
