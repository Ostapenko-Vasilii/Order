package ru.orderdorms.features.services.data.network.model

import kotlinx.serialization.Serializable
import ru.orderdorms.features.services.domain.model.booking.BookingResource
import ru.orderdorms.features.services.domain.model.booking.BookingSlot
import ru.orderdorms.features.services.domain.model.booking.BookingStatus
import ru.orderdorms.features.services.domain.model.booking.DaySlots
import ru.orderdorms.features.services.domain.model.booking.UserBooking

@Serializable
data class BookingResourceDto(
    val id: String,
    val name: String,
    val description: String? = null
)

@Serializable
data class BookingSlotDto(
    val id: String,
    val timeRange: String,
    val isAvailable: Boolean
)

@Serializable
data class DaySlotsDto(
    val date: String,
    val slots: List<BookingSlotDto>
)

@Serializable
data class BookSlotRequestDto(
    val slotId: String
)

@Serializable
data class UserBookingDto(
    val id: String,
    val slotId: String,
    val resourceName: String,
    val date: String,
    val timeRange: String,
    val status: String,
    val createdAt: String
)

fun BookingResourceDto.toDomain(): BookingResource = BookingResource(
    id = id,
    name = name,
    description = description
)

fun BookingSlotDto.toDomain(): BookingSlot = BookingSlot(
    id = id,
    timeRange = timeRange,
    isAvailable = isAvailable
)

fun DaySlotsDto.toDomain(): DaySlots = DaySlots(
    date = date,
    slots = slots.map { it.toDomain() }
)

fun UserBookingDto.toDomain(): UserBooking = UserBooking(
    id = id,
    slotId = slotId,
    resourceName = resourceName,
    date = date,
    timeRange = timeRange,
    status = when (status) {
        "CANCELLED" -> BookingStatus.CANCELLED
        "COMPLETED" -> BookingStatus.COMPLETED
        else -> BookingStatus.CONFIRMED
    },
    createdAt = createdAt
)
