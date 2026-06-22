package ru.orderdorms.features.services.presentation.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.orderdorms.features.services.domain.model.booking.BookingResource
import ru.orderdorms.features.services.domain.model.booking.BookingSlot
import ru.orderdorms.features.services.domain.model.booking.BookingStatus
import ru.orderdorms.features.services.domain.model.booking.UserBooking
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.components.OrderButton
import ru.orderdorms.ui.icons.ArrowBack
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun BookingFlow(onBack: () -> Unit) {
    val viewModel = rememberBookingViewModel()
    BookingScreen(
        state = viewModel.state,
        onBack = onBack,
        onResourceSelect = viewModel::selectResource,
        onDaySelect = viewModel::selectDay,
        onSlotSelect = viewModel::selectSlot,
        onBookClick = viewModel::bookSelectedSlot,
        onCancelClick = viewModel::cancelBooking,
        onRefresh = viewModel::loadResources,
    )
}

@Composable
fun BookingScreen(
    state: BookingState,
    onBack: () -> Unit,
    onResourceSelect: (String) -> Unit,
    onDaySelect: (Int) -> Unit,
    onSlotSelect: (String) -> Unit,
    onBookClick: () -> Unit,
    onCancelClick: (String) -> Unit,
    @Suppress("UNUSED_PARAMETER") onRefresh: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(Color(0xFFF2F2F2)),
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.smallPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onBack() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Black,
                )
            }

            Text(
                text = "Бронирование",
                style = OrderTheme.typography.displayMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.padding(start = Dimensions.regularPadding),
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(Dimensions.regularPadding),
            verticalArrangement = Arrangement.spacedBy(Dimensions.regularPadding),
        ) {
            // Resource Selection
            item {
                Text(
                    text = "Выберите место",
                    style = OrderTheme.typography.displayMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = Dimensions.smallPadding)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.smallPadding),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(state.resources) { resource ->
                        ResourceItem(
                            resource = resource,
                            isSelected = resource.id == state.selectedResourceId,
                            onClick = { onResourceSelect(resource.id) },
                        )
                    }
                }
            }

            // Day Selection
            if (state.daySlots.isNotEmpty()) {
                item {
                    Text(
                        text = "Выберите день",
                        style = OrderTheme.typography.displayMedium.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = Dimensions.smallPadding)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(Dimensions.smallPadding),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(state.daySlots.size) { index ->
                            val day = state.daySlots[index]
                            val isSelected = index == state.selectedDayIndex
                            DayItem(
                                date = day.date,
                                isSelected = isSelected,
                                onClick = { onDaySelect(index) },
                            )
                        }
                    }
                }
            }

            // Slots Grid
            item {
                val currentDay = state.currentDaySlots
                if (currentDay != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(Dimensions.regularCornerRadius))
                            .background(Color.White)
                            .padding(Dimensions.regularPadding),
                        verticalArrangement = Arrangement.spacedBy(Dimensions.smallPadding),
                    ) {
                        Text(
                            text = "Выберите время",
                            style = OrderTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = Dimensions.smallPadding),
                        )
                        
                        val columns = 3
                        val slots = currentDay.slots
                        val rows = ((slots.size + columns - 1) / columns)
                        
                        for (i in 0 until rows) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(Dimensions.smallPadding),
                            ) {
                                for (j in 0 until columns) {
                                    val index = i * columns + j
                                    if (index < slots.size) {
                                        val slot = slots[index]
                                        Box(modifier = Modifier.weight(1f)) {
                                            SlotItem(
                                                slot = slot,
                                                isSelected = slot.id == state.selectedSlotId,
                                                onClick = { if (slot.isAvailable) onSlotSelect(slot.id) },
                                            )
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }

                        if (state.error != null && !state.isLoading && !state.isBooking && !state.isCancelling) {
                            Text(
                                text = state.error.message,
                                color = Color.Red,
                                style = OrderTheme.typography.labelMedium,
                                modifier = Modifier.padding(top = Dimensions.regularPadding),
                            )
                        }

                        Spacer(modifier = Modifier.height(Dimensions.regularPadding))
                        
                        OrderButton(
                            onClick = onBookClick,
                            text = "Забронировать",
                            isLoading = state.isBooking,
                            isActive = state.selectedSlotId != null,
                        )
                    }
                }
            }

            // User Bookings
            item {
                Text(
                    text = "Мои бронирования",
                    style = OrderTheme.typography.displayMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(vertical = Dimensions.smallPadding),
                )
            }

            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(Dimensions.largePadding),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = OrderTheme.colors.activeColor)
                    }
                }
            } else {
                items(state.userBookings) { booking ->
                    UserBookingItem(
                        booking = booking,
                        onCancelClick = { onCancelClick(booking.id) },
                        isCancelling = state.isCancelling
                    )
                }
            }
        }
    }
}

@Composable
fun ResourceItem(resource: BookingResource, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimensions.smallCornerRadius))
            .background(if (isSelected) OrderTheme.colors.activeColor else Color.White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = resource.name,
                color = if (isSelected) Color.White else Color.Black,
                style = OrderTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}

@Composable
fun DayItem(date: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimensions.smallCornerRadius))
            .background(if (isSelected) OrderTheme.colors.activeColor.copy(alpha = 0.8f) else Color.White)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = date,
            color = if (isSelected) Color.White else Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun SlotItem(slot: BookingSlot, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = when {
        !slot.isAvailable -> Color(0xFFE0E0E0)
        isSelected -> OrderTheme.colors.activeColor.copy(alpha = 0.2f)
        else -> Color.White
    }
    val borderColor = when {
        isSelected -> OrderTheme.colors.activeColor
        else -> Color.LightGray
    }
    val textColor = if (!slot.isAvailable) Color.Gray else Color.Black

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(Dimensions.smallCornerRadius))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(Dimensions.smallCornerRadius))
            .clickable(enabled = slot.isAvailable) { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = slot.timeRange,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        )
    }
}

@Composable
fun UserBookingItem(booking: UserBooking, onCancelClick: () -> Unit, isCancelling: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimensions.regularCornerRadius))
            .background(Color.White)
            .padding(Dimensions.regularPadding),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = booking.resourceName,
                    style = OrderTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = booking.date,
                    style = OrderTheme.typography.labelMedium,
                    color = Color.Gray
                )
            }
            BookingStatusBadge(booking.status)
        }
        Spacer(modifier = Modifier.height(Dimensions.smallPadding))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Время: ${booking.timeRange}",
                style = OrderTheme.typography.bodyLarge
            )
            
            if (booking.status == BookingStatus.CONFIRMED) {
                Text(
                    text = "Отменить",
                    color = Color.Red,
                    style = OrderTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.clickable(enabled = !isCancelling) { onCancelClick() }
                )
            }
        }
    }
}

@Composable
fun BookingStatusBadge(status: BookingStatus) {
    val (text, color) = when (status) {
        BookingStatus.CONFIRMED -> "Подтверждено" to Color(0xFF388E3C)
        BookingStatus.CANCELLED -> "Отменено" to Color(0xFFD32F2F)
        BookingStatus.COMPLETED -> "Завершено" to Color(0xFF1976D2)
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.1f))
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Text(
            text = text,
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}
