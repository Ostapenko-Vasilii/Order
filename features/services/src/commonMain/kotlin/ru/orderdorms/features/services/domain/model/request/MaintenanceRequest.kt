package ru.orderdorms.features.services.domain.model.request

enum class MaintenanceRequestStatus {
    PENDING, ACCEPTED, COMPLETED, REJECTED
}

data class MaintenanceRequest(
    val id: String,
    val roomNumber: String,
    val description: String,
    val note: String,
    val status: MaintenanceRequestStatus,
    val comment: String? = null,
    val createdAt: String
)
