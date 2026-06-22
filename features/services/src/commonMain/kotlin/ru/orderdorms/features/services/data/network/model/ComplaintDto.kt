package ru.orderdorms.features.services.data.network.model

import kotlinx.serialization.Serializable
import ru.orderdorms.features.services.domain.model.complaint.Complaint
import ru.orderdorms.features.services.domain.model.complaint.ComplaintStatus

@Serializable
data class ComplaintCreateRequestDto(
    val text: String
)

@Serializable
data class ComplaintResponseDto(
    val id: String,
    val text: String,
    val status: String,
    val answer: String? = null,
    val createdAt: String
)

fun ComplaintResponseDto.toDomain(): Complaint = Complaint(
    id = id,
    text = text,
    status = when (status) {
        "REVIEWED" -> ComplaintStatus.REVIEWED
        "RESOLVED" -> ComplaintStatus.RESOLVED
        "REJECTED" -> ComplaintStatus.REJECTED
        else -> ComplaintStatus.PENDING
    },
    answer = answer,
    createdAt = createdAt
)
