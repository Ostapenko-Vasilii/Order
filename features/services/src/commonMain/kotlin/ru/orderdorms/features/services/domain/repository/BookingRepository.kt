package ru.orderdorms.features.services.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.services.domain.model.booking.BookingResource
import ru.orderdorms.features.services.domain.model.booking.DaySlots
import ru.orderdorms.features.services.domain.model.booking.UserBooking

interface BookingRepository {
    fun getResources(): Flow<Either<DomainError, List<BookingResource>>>
    fun getAvailableSlots(resourceId: String): Flow<Either<DomainError, List<DaySlots>>>
    fun getUserBookings(): Flow<Either<DomainError, List<UserBooking>>>
    suspend fun bookSlot(slotId: String): Either<DomainError, Unit>
    suspend fun cancelBooking(bookingId: String): Either<DomainError, Unit>
}
