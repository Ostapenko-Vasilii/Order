package ru.orderdorms.features.services.data.network.model

import kotlinx.serialization.Serializable
import ru.orderdorms.features.services.domain.model.request.MaintenanceRequest
import ru.orderdorms.features.services.domain.model.request.MaintenanceRequestStatus

@Serializable
data class RequestCreateRequestDto(
    val roomNumber: String,
    val description: String,
    val note: String
)

@Serializable
data class RequestResponseDto(
    val id: String,
    val roomNumber: String,
    val description: String,
    val note: String,
    val status: String,
    val comment: String? = null,
    val createdAt: String
)

fun RequestResponseDto.toDomain(): MaintenanceRequest = MaintenanceRequest(
    id = id,
    roomNumber = roomNumber,
    description = description,
    note = note,
    status = when (status) {
        "ACCEPTED" -> MaintenanceRequestStatus.ACCEPTED
        "COMPLETED" -> MaintenanceRequestStatus.COMPLETED
        "REJECTED" -> MaintenanceRequestStatus.REJECTED
        else -> MaintenanceRequestStatus.PENDING
    },
    comment = comment,
    createdAt = createdAt
)
