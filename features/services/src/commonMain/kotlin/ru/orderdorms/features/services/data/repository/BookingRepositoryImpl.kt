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
import ru.orderdorms.features.services.data.network.model.BookSlotRequestDto
import ru.orderdorms.features.services.data.network.model.BookingResourceDto
import ru.orderdorms.features.services.data.network.model.BookingSlotDto
import ru.orderdorms.features.services.data.network.model.DaySlotsDto
import ru.orderdorms.features.services.data.network.model.UserBookingDto
import ru.orderdorms.features.services.data.network.model.toDomain
import ru.orderdorms.features.services.domain.model.booking.BookingResource
import ru.orderdorms.features.services.domain.model.booking.BookingSlot
import ru.orderdorms.features.services.domain.model.booking.BookingStatus
import ru.orderdorms.features.services.domain.model.booking.DaySlots
import ru.orderdorms.features.services.domain.model.booking.UserBooking
import ru.orderdorms.features.services.domain.repository.BookingRepository
import kotlin.time.Duration.Companion.minutes

class BookingRepositoryImpl(
    private val mockDataSource: BookingMockDataSource,
    private val apiDataSource: BookingApiDataSource,
    private val clock: Clock,
    private val useMock: Boolean = true
) : BookingRepository {

    private var cachedSlots: Map<String, List<DaySlots>> = emptyMap()
    private var lastSlotsFetchTime: Map<String, Instant> = emptyMap()
    private val cacheDuration = 5.minutes

    override fun getResources(): Flow<Either<DomainError, List<BookingResource>>> = flow {
        try {
            val data = if (useMock) {
                mockDataSource.getResources()
            } else {
                apiDataSource.getResources()
            }
            emit(Either.Right(data))
        } catch (e: Exception) {
            emit(Either.Left(DomainError(code = "BOOKING_RESOURCES_ERROR", message = e.message ?: "Failed to fetch resources")))
        }
    }

    override fun getAvailableSlots(resourceId: String): Flow<Either<DomainError, List<DaySlots>>> = flow {
        val now = clock.now()
        val cache = cachedSlots[resourceId]
        val last = lastSlotsFetchTime[resourceId]

        if (cache != null && last != null && (now - last) < cacheDuration) {
            emit(Either.Right(cache))
        } else {
            try {
                val data = if (useMock) {
                    mockDataSource.getAvailableSlots(resourceId)
                } else {
                    apiDataSource.getAvailableSlots(resourceId)
                }
                cachedSlots = cachedSlots + (resourceId to data)
                lastSlotsFetchTime = lastSlotsFetchTime + (resourceId to now)
                emit(Either.Right(data))
            } catch (e: Exception) {
                emit(Either.Left(DomainError(code = "BOOKING_SLOTS_ERROR", message = e.message ?: "Failed to fetch slots")))
            }
        }
    }

    override fun getUserBookings(): Flow<Either<DomainError, List<UserBooking>>> = flow {
        try {
            val data = if (useMock) {
                mockDataSource.getUserBookings()
            } else {
                apiDataSource.getUserBookings()
            }
            emit(Either.Right(data))
        } catch (e: Exception) {
            emit(Either.Left(DomainError(code = "USER_BOOKINGS_ERROR", message = e.message ?: "Failed to fetch user bookings")))
        }
    }

    override suspend fun bookSlot(slotId: String): Either<DomainError, Unit> {
        return try {
            if (useMock) {
                mockDataSource.bookSlot(slotId)
            } else {
                apiDataSource.bookSlot(slotId)
            }
            cachedSlots = emptyMap() // Invalidate all slots cache
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(DomainError(code = "BOOK_SLOT_ERROR", message = e.message ?: "Failed to book slot"))
        }
    }

    override suspend fun cancelBooking(bookingId: String): Either<DomainError, Unit> {
        return try {
            if (useMock) {
                mockDataSource.cancelBooking(bookingId)
            } else {
                apiDataSource.cancelBooking(bookingId)
            }
            cachedSlots = emptyMap() // Invalidate cache
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(DomainError(code = "CANCEL_BOOKING_ERROR", message = e.message ?: "Failed to cancel booking"))
        }
    }
}

interface BookingApiDataSource {
    suspend fun getResources(): List<BookingResource>
    suspend fun getAvailableSlots(resourceId: String): List<DaySlots>
    suspend fun getUserBookings(): List<UserBooking>
    suspend fun bookSlot(slotId: String)
    suspend fun cancelBooking(bookingId: String)
}

class KtorBookingApiDataSource(
    private val httpClient: HttpClient
) : BookingApiDataSource {
    override suspend fun getResources(): List<BookingResource> {
        val response: List<BookingResourceDto> = httpClient.get("booking/resources").body()
        return response.map { it.toDomain() }
    }

    override suspend fun getAvailableSlots(resourceId: String): List<DaySlots> {
        val response: List<DaySlotsDto> = httpClient.get("booking/slots") {
            url { parameters.append("resourceId", resourceId) }
        }.body()
        return response.map { it.toDomain() }
    }

    override suspend fun getUserBookings(): List<UserBooking> {
        val response: List<UserBookingDto> = httpClient.get("booking/my").body()
        return response.map { it.toDomain() }
    }

    override suspend fun bookSlot(slotId: String) {
        httpClient.post("booking/book") {
            setBody(BookSlotRequestDto(slotId))
        }
    }

    override suspend fun cancelBooking(bookingId: String) {
        httpClient.post("booking/cancel/$bookingId")
    }
}

class BookingMockDataSource {
    private val resources = listOf(
        BookingResource("r1", "400я комната", "Комната для занятий"),
        BookingResource("r2", "500я комната", "Комната отдыха"),
        BookingResource("r3", "Настольный теннис", "Спортивная зона")
    )

    private val availableSlots = mutableMapOf(
        "r1" to mutableListOf(
            DaySlots("2024-06-20", listOf(
                BookingSlot("s1", "08:00-08:30", true),
                BookingSlot("s2", "08:30-09:00", true)
            )),
            DaySlots("2024-06-21", listOf(
                BookingSlot("s3", "10:00-10:30", true)
            ))
        ),
        "r2" to mutableListOf(
            DaySlots("2024-06-20", listOf(
                BookingSlot("s4", "12:00-13:00", true)
            ))
        ),
        "r3" to mutableListOf(
            DaySlots("2024-06-20", listOf(
                BookingSlot("s5", "18:00-19:00", true)
            ))
        )
    )

    private val userBookings = mutableListOf<UserBooking>()

    fun getResources(): List<BookingResource> = resources
    
    fun getAvailableSlots(resourceId: String): List<DaySlots> = 
        availableSlots[resourceId]?.toList() ?: emptyList()

    fun getUserBookings(): List<UserBooking> = userBookings.toList()

    fun bookSlot(slotId: String) {
        if (slotId == "s1") throw Exception("Бронирование сейчас недоступно.")

        var foundResourceId: String? = null
        var foundDayIndex: Int = -1
        var foundSlotIndex: Int = -1

        availableSlots.forEach { (resId, days) ->
            days.forEachIndexed { dIdx, day ->
                day.slots.forEachIndexed { sIdx, slot ->
                    if (slot.id == slotId) {
                        if (!slot.isAvailable) throw Exception("Слот уже забронирован")
                        foundResourceId = resId
                        foundDayIndex = dIdx
                        foundSlotIndex = sIdx
                    }
                }
            }
        }

        if (foundResourceId != null) {
            val resource = resources.find { it.id == foundResourceId }!!
            val day = availableSlots[foundResourceId]!![foundDayIndex]
            val slot = day.slots[foundSlotIndex]

            // Update availability
            val updatedSlots = day.slots.toMutableList()
            updatedSlots[foundSlotIndex] = slot.copy(isAvailable = false)
            availableSlots[foundResourceId!!]!![foundDayIndex] = day.copy(slots = updatedSlots)

            userBookings.add(0, UserBooking(
                id = "b${userBookings.size + 1}",
                slotId = slotId,
                resourceName = resource.name,
                date = day.date,
                timeRange = slot.timeRange,
                status = BookingStatus.CONFIRMED,
                createdAt = "2024-06-19T14:00:00Z"
            ))
        } else {
            throw Exception("Слот не найден")
        }
    }

    fun cancelBooking(bookingId: String) {
        val bookingIndex = userBookings.indexOfFirst { it.id == bookingId }
        if (bookingIndex != -1) {
            val booking = userBookings[bookingIndex]
            if (booking.status == BookingStatus.CANCELLED) throw Exception("Уже отменено")
            
            userBookings[bookingIndex] = booking.copy(status = BookingStatus.CANCELLED)
            
            // Restore slot availability
            availableSlots.forEach { (_, days) ->
                days.forEachIndexed { dIdx, day ->
                    day.slots.forEachIndexed { sIdx, slot ->
                        if (slot.id == booking.slotId) {
                            val updatedSlots = day.slots.toMutableList()
                            updatedSlots[sIdx] = slot.copy(isAvailable = true)
                            days[dIdx] = day.copy(slots = updatedSlots)
                        }
                    }
                }
            }
        } else {
            throw Exception("Бронирование не найдено")
        }
    }
}
