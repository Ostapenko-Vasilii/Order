package ru.orderdorms.features.services.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.services.domain.model.complaint.Complaint

interface ComplaintRepository {
    fun getUserComplaints(): Flow<Either<DomainError, List<Complaint>>>
    suspend fun submitComplaint(text: String): Either<DomainError, Unit>
}
