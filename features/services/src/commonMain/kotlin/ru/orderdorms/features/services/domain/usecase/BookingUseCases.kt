package ru.orderdorms.features.services.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.services.domain.model.booking.BookingResource
import ru.orderdorms.features.services.domain.model.booking.DaySlots
import ru.orderdorms.features.services.domain.model.booking.UserBooking
import ru.orderdorms.features.services.domain.repository.BookingRepository

class GetBookingResourcesUseCase(private val repository: BookingRepository) {
    operator fun invoke(): Flow<Either<DomainError, List<BookingResource>>> = repository.getResources()
}

class GetAvailableSlotsUseCase(private val repository: BookingRepository) {
    operator fun invoke(resourceId: String): Flow<Either<DomainError, List<DaySlots>>> = 
        repository.getAvailableSlots(resourceId)
}

class GetUserBookingsUseCase(private val repository: BookingRepository) {
    operator fun invoke(): Flow<Either<DomainError, List<UserBooking>>> = repository.getUserBookings()
}

class BookSlotUseCase(private val repository: BookingRepository) {
    suspend operator fun invoke(slotId: String): Either<DomainError, Unit> = repository.bookSlot(slotId)
}

class CancelBookingUseCase(private val repository: BookingRepository) {
    suspend operator fun invoke(bookingId: String): Either<DomainError, Unit> = repository.cancelBooking(bookingId)
}
