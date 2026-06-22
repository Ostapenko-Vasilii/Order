package ru.orderdorms.features.services.domain.model.complaint

enum class ComplaintStatus {
    PENDING, REVIEWED, RESOLVED, REJECTED
}

data class Complaint(
    val id: String,
    val text: String,
    val status: ComplaintStatus,
    val answer: String? = null,
    val createdAt: String
)
