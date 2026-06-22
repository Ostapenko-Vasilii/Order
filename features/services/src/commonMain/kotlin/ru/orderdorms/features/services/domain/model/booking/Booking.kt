package ru.orderdorms.features.services.domain.model.booking

enum class BookingStatus {
    CONFIRMED, CANCELLED, COMPLETED
}

data class BookingResource(
    val id: String,
    val name: String,
    val description: String? = null
)

data class BookingSlot(
    val id: String,
    val timeRange: String,
    val isAvailable: Boolean
)

data class DaySlots(
    val date: String,
    val slots: List<BookingSlot>
)

data class UserBooking(
    val id: String,
    val slotId: String,
    val resourceName: String,
    val date: String,
    val timeRange: String,
    val status: BookingStatus,
    val createdAt: String
)
