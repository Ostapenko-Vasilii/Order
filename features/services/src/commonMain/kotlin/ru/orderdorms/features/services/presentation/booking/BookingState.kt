package ru.orderdorms.features.services.presentation.booking

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.features.services.domain.model.booking.BookingResource
import ru.orderdorms.features.services.domain.model.booking.DaySlots
import ru.orderdorms.features.services.domain.model.booking.UserBooking

data class BookingState(
    val resources: List<BookingResource> = emptyList(),
    val daySlots: List<DaySlots> = emptyList(),
    val userBookings: List<UserBooking> = emptyList(),
    val selectedResourceId: String? = null,
    val selectedDayIndex: Int = 0,
    val selectedSlotId: String? = null,
    val isLoading: Boolean = false,
    val isBooking: Boolean = false,
    val isCancelling: Boolean = false,
    val error: DomainError? = null
) {
    val currentDaySlots: DaySlots?
        get() = daySlots.getOrNull(selectedDayIndex)
}
