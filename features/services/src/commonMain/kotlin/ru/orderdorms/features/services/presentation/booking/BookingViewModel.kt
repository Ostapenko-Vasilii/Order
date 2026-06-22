package ru.orderdorms.features.services.presentation.booking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform
import ru.orderdorms.features.services.domain.usecase.BookSlotUseCase
import ru.orderdorms.features.services.domain.usecase.CancelBookingUseCase
import ru.orderdorms.features.services.domain.usecase.GetAvailableSlotsUseCase
import ru.orderdorms.features.services.domain.usecase.GetBookingResourcesUseCase
import ru.orderdorms.features.services.domain.usecase.GetUserBookingsUseCase

class BookingViewModel(
    private val getBookingResourcesUseCase: GetBookingResourcesUseCase,
    private val getAvailableSlotsUseCase: GetAvailableSlotsUseCase,
    private val getUserBookingsUseCase: GetUserBookingsUseCase,
    private val bookSlotUseCase: BookSlotUseCase,
    private val cancelBookingUseCase: CancelBookingUseCase
) {
    private val scope: CoroutineScope = MainScope()

    var state by mutableStateOf(BookingState())
        private set

    init {
        loadResources()
        loadUserBookings()
    }

    fun loadResources() {
        state = state.copy(isLoading = true, error = null)
        getBookingResourcesUseCase()
            .onEach { result ->
                result.fold(
                    left = { error -> state = state.copy(isLoading = false, error = error) },
                    right = { resources -> 
                        state = state.copy(isLoading = false, resources = resources)
                        if (state.selectedResourceId == null && resources.isNotEmpty()) {
                            selectResource(resources.first().id)
                        }
                    }
                )
            }
            .launchIn(scope)
    }

    fun loadUserBookings() {
        getUserBookingsUseCase()
            .onEach { result ->
                result.fold(
                    left = { /* Handle error if needed */ },
                    right = { bookings -> state = state.copy(userBookings = bookings) }
                )
            }
            .launchIn(scope)
    }

    fun selectResource(resourceId: String) {
        state = state.copy(selectedResourceId = resourceId, selectedDayIndex = 0, selectedSlotId = null)
        loadSlots(resourceId)
    }

    private fun loadSlots(resourceId: String) {
        state = state.copy(isLoading = true)
        getAvailableSlotsUseCase(resourceId)
            .onEach { result ->
                result.fold(
                    left = { error -> state = state.copy(isLoading = false, error = error) },
                    right = { slots -> state = state.copy(isLoading = false, daySlots = slots) }
                )
            }
            .launchIn(scope)
    }

    fun selectDay(index: Int) {
        state = state.copy(selectedDayIndex = index, selectedSlotId = null)
    }

    fun selectSlot(slotId: String) {
        state = state.copy(selectedSlotId = slotId)
    }

    fun bookSelectedSlot() {
        val slotId = state.selectedSlotId ?: return
        
        scope.launch {
            state = state.copy(isBooking = true, error = null)
            val result = bookSlotUseCase(slotId)
            result.fold(
                left = { error ->
                    state = state.copy(isBooking = false, error = error)
                },
                right = {
                    state = state.copy(isBooking = false, selectedSlotId = null)
                    state.selectedResourceId?.let { loadSlots(it) }
                    loadUserBookings()
                }
            )
        }
    }

    fun cancelBooking(bookingId: String) {
        scope.launch {
            state = state.copy(isCancelling = true, error = null)
            val result = cancelBookingUseCase(bookingId)
            result.fold(
                left = { error ->
                    state = state.copy(isCancelling = false, error = error)
                },
                right = {
                    state = state.copy(isCancelling = false)
                    state.selectedResourceId?.let { loadSlots(it) }
                    loadUserBookings()
                }
            )
        }
    }

    fun dispose() {
        scope.cancel()
    }
}

@Composable
fun rememberBookingViewModel(): BookingViewModel {
    val koin = KoinPlatform.getKoin()
    val viewModel = remember {
        BookingViewModel(
            getBookingResourcesUseCase = koin.get(),
            getAvailableSlotsUseCase = koin.get(),
            getUserBookingsUseCase = koin.get(),
            bookSlotUseCase = koin.get(),
            cancelBookingUseCase = koin.get()
        )
    }
    DisposableEffect(viewModel) {
        onDispose { viewModel.dispose() }
    }
    return viewModel
}
