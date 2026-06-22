package ru.orderdorms.features.services.presentation.complaints

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.features.services.domain.model.complaint.Complaint

data class ComplaintsState(
    val complaints: List<Complaint> = emptyList(),
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val error: DomainError? = null,
    val text: String = ""
)
