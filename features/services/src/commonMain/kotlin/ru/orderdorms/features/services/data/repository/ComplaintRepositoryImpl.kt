package ru.orderdorms.features.services.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.services.data.network.model.ComplaintCreateRequestDto
import ru.orderdorms.features.services.data.network.model.ComplaintResponseDto
import ru.orderdorms.features.services.data.network.model.toDomain
import ru.orderdorms.features.services.domain.model.complaint.Complaint
import ru.orderdorms.features.services.domain.model.complaint.ComplaintStatus
import ru.orderdorms.features.services.domain.repository.ComplaintRepository
import kotlin.time.Duration.Companion.minutes

class ComplaintRepositoryImpl(
    private val mockDataSource: ComplaintMockDataSource,
    private val apiDataSource: ComplaintApiDataSource,
    private val clock: Clock,
    private val useMock: Boolean = true
) : ComplaintRepository {

    private var cachedData: List<Complaint>? = null
    private var lastFetchTime: Instant? = null
    private val cacheDuration = 5.minutes

    override fun getUserComplaints(): Flow<Either<DomainError, List<Complaint>>> = flow {
        val now = clock.now()
        val cache = cachedData
        val last = lastFetchTime

        if (cache != null && last != null && (now - last) < cacheDuration) {
            emit(Either.Right(cache))
        } else {
            try {
                val data = if (useMock) {
                    mockDataSource.getComplaints()
                } else {
                    apiDataSource.getComplaints()
                }
                cachedData = data
                lastFetchTime = now
                emit(Either.Right(data))
            } catch (e: Exception) {
                emit(Either.Left(DomainError(
                    code = "COMPLAINTS_FETCH_ERROR",
                    message = e.message ?: "Failed to fetch complaints"
                )))
            }
        }
    }

    override suspend fun submitComplaint(text: String): Either<DomainError, Unit> {
        return try {
            if (useMock) {
                mockDataSource.submitComplaint(text)
            } else {
                apiDataSource.submitComplaint(text)
            }
            cachedData = null // Invalidate cache
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(DomainError(
                code = "COMPLAINT_SUBMIT_ERROR",
                message = e.message ?: "Failed to submit complaint"
            ))
        }
    }
}

interface ComplaintApiDataSource {
    suspend fun getComplaints(): List<Complaint>
    suspend fun submitComplaint(text: String)
}

class KtorComplaintApiDataSource(
    private val httpClient: HttpClient
) : ComplaintApiDataSource {
    override suspend fun getComplaints(): List<Complaint> {
        val response: List<ComplaintResponseDto> = httpClient.get("complaints").body()
        return response.map { it.toDomain() }
    }

    override suspend fun submitComplaint(text: String) {
        httpClient.post("complaints") {
            setBody(ComplaintCreateRequestDto(text))
        }
    }
}

class ComplaintMockDataSource {
    private val complaints = mutableListOf(
        Complaint(
            id = "1",
            text = "Очень шумно на 3 этаже ночью",
            status = ComplaintStatus.PENDING,
            createdAt = "2024-06-18T23:00:00Z"
        ),
        Complaint(
            id = "2",
            text = "Нет горячей воды в душе",
            status = ComplaintStatus.RESOLVED,
            answer = "Проблема устранена, был сбой в котельной",
            createdAt = "2024-06-17T09:00:00Z"
        )
    )

    fun getComplaints(): List<Complaint> = complaints.toList()

    fun submitComplaint(text: String) {
        if (text.contains("error", ignoreCase = true)) {
            throw Exception("Ошибка на стороне сервера: Жалоба содержит недопустимые символы.")
        }

        complaints.add(0, Complaint(
            id = (complaints.size + 1).toString(),
            text = text,
            status = ComplaintStatus.PENDING,
            createdAt = "2024-06-19T13:00:00Z"
        ))
    }
}
