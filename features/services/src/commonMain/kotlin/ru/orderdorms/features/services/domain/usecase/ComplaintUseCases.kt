package ru.orderdorms.features.services.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.services.domain.model.complaint.Complaint
import ru.orderdorms.features.services.domain.repository.ComplaintRepository

class GetUserComplaintsUseCase(private val repository: ComplaintRepository) {
    operator fun invoke(): Flow<Either<DomainError, List<Complaint>>> = repository.getUserComplaints()
}

class SubmitComplaintUseCase(private val repository: ComplaintRepository) {
    suspend operator fun invoke(text: String): Either<DomainError, Unit> =
        repository.submitComplaint(text)
}
