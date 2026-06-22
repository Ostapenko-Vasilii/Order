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
import ru.orderdorms.features.services.data.network.model.RequestCreateRequestDto
import ru.orderdorms.features.services.data.network.model.RequestResponseDto
import ru.orderdorms.features.services.data.network.model.toDomain
import ru.orderdorms.features.services.domain.model.request.MaintenanceRequest
import ru.orderdorms.features.services.domain.model.request.MaintenanceRequestStatus
import ru.orderdorms.features.services.domain.repository.RequestRepository
import kotlin.time.Duration.Companion.minutes

class RequestRepositoryImpl(
    private val mockDataSource: RequestMockDataSource,
    private val apiDataSource: RequestApiDataSource,
    private val clock: Clock,
    private val useMock: Boolean = true
) : RequestRepository {

    private var cachedData: List<MaintenanceRequest>? = null
    private var lastFetchTime: Instant? = null
    private val cacheDuration = 5.minutes

    override fun getUserRequests(): Flow<Either<DomainError, List<MaintenanceRequest>>> = flow {
        val now = clock.now()
        val cache = cachedData
        val last = lastFetchTime

        if (cache != null && last != null && (now - last) < cacheDuration) {
            emit(Either.Right(cache))
        } else {
            try {
                val data = if (useMock) {
                    mockDataSource.getRequests()
                } else {
                    apiDataSource.getRequests()
                }
                cachedData = data
                lastFetchTime = now
                emit(Either.Right(data))
            } catch (e: Exception) {
                emit(Either.Left(DomainError(
                    code = "REQUESTS_FETCH_ERROR",
                    message = e.message ?: "Failed to fetch requests"
                )))
            }
        }
    }

    override suspend fun submitRequest(
        roomNumber: String,
        description: String,
        note: String
    ): Either<DomainError, Unit> {
        return try {
            if (useMock) {
                mockDataSource.submitRequest(roomNumber, description, note)
            } else {
                apiDataSource.submitRequest(roomNumber, description, note)
            }
            cachedData = null // Invalidate cache
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(DomainError(
                code = "REQUEST_SUBMIT_ERROR",
                message = e.message ?: "Failed to submit request"
            ))
        }
    }
}

interface RequestApiDataSource {
    suspend fun getRequests(): List<MaintenanceRequest>
    suspend fun submitRequest(roomNumber: String, description: String, note: String)
}

class KtorRequestApiDataSource(
    private val httpClient: HttpClient
) : RequestApiDataSource {
    override suspend fun getRequests(): List<MaintenanceRequest> {
        val response: List<RequestResponseDto> = httpClient.get("requests").body()
        return response.map { it.toDomain() }
    }

    override suspend fun submitRequest(roomNumber: String, description: String, note: String) {
        httpClient.post("requests") {
            setBody(RequestCreateRequestDto(roomNumber, description, note))
        }
    }
}

class RequestMockDataSource {
    private val requests = mutableListOf(
        MaintenanceRequest(
            id = "1",
            roomNumber = "404",
            description = "Протекает кран",
            note = "Желательно до обеда",
            status = MaintenanceRequestStatus.PENDING,
            createdAt = "2024-06-18T10:00:00Z"
        ),
        MaintenanceRequest(
            id = "2",
            roomNumber = "404",
            description = "Перегорела лампочка",
            note = "",
            status = MaintenanceRequestStatus.COMPLETED,
            comment = "Лампочка заменена",
            createdAt = "2024-06-15T08:30:00Z"
        )
    )

    fun getRequests(): List<MaintenanceRequest> = requests.toList()

    fun submitRequest(roomNumber: String, description: String, note: String) {
        if (description.contains("error", ignoreCase = true)) {
            throw Exception("Ошибка на стороне сервера: Не удалось обработать заявку.")
        }

        requests.add(0, MaintenanceRequest(
            id = (requests.size + 1).toString(),
            roomNumber = roomNumber,
            description = description,
            note = note,
            status = MaintenanceRequestStatus.PENDING,
            createdAt = "2024-06-19T12:00:00Z"
        ))
    }
}
