package ru.orderdorms.features.services.presentation.requests

import androidx.compose.foundation.background
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
import ru.orderdorms.features.services.domain.model.request.MaintenanceRequest
import ru.orderdorms.features.services.domain.model.request.MaintenanceRequestStatus
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.components.OrderButton
import ru.orderdorms.ui.components.OrderTextField
import ru.orderdorms.ui.icons.ArrowBack
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun RequestsFlow(onBack: () -> Unit) {
    val viewModel = rememberRequestsViewModel()
    RequestsScreen(
        state = viewModel.state,
        onBack = onBack,
        onRoomChange = viewModel::onRoomNumberChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onNoteChange = viewModel::onNoteChange,
        onSubmit = viewModel::submitRequest,
        onRefresh = viewModel::loadRequests,
    )
}

@Composable
fun RequestsScreen(
    state: RequestsState,
    onBack: () -> Unit,
    onRoomChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(Color(0xFFF2F2F2))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.smallPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Black
                )
            }

            Text(
                text = "Подать Заявку",
                style = OrderTheme.typography.displayMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = Dimensions.regularPadding)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(Dimensions.smallPadding),
            verticalArrangement = Arrangement.spacedBy(Dimensions.regularPadding)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(Dimensions.regularCornerRadius))
                        .background(Color.White)
                        .padding(Dimensions.smallPadding),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.smallPadding)
                ) {
                    OrderTextField(
                        value = state.roomNumber,
                        onValueChange = onRoomChange,
                        label = "Номер комнаты",
                        placeholder = "Например, 404"
                    )
                    OrderTextField(
                        value = state.description,
                        onValueChange = onDescriptionChange,
                        label = "Описание проблемы",
                        placeholder = "Что случилось?",
                        singleLine = false
                    )
                    OrderTextField(
                        value = state.note,
                        onValueChange = onNoteChange,
                        label = "Примечание",
                        placeholder = "Дополнительная информация (необязательно)",
                        singleLine = false
                    )
                    
                    if (state.error != null && !state.isLoading && !state.isSubmitting) {
                        Text(
                            text = state.error.message,
                            color = Color.Red,
                            style = OrderTheme.typography.labelMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(Dimensions.smallPadding))
                    OrderButton(
                        onClick = onSubmit,
                        text = "Отправить заявку",
                        isLoading = state.isSubmitting,
                        isActive = state.roomNumber.isNotBlank() && state.description.isNotBlank()
                    )
                }
            }

            item {
                Text(
                    text = "Мои заявки",
                    style = OrderTheme.typography.displayMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(vertical = Dimensions.smallPadding)
                )
            }

            if (state.isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(Dimensions.largePadding), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = OrderTheme.colors.activeColor)
                    }
                }
            } else {
                items(state.requests) { request ->
                    RequestItem(request)
                }
            }
        }
    }
}

@Composable
fun RequestItem(request: MaintenanceRequest) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimensions.regularCornerRadius))
            .background(Color.White)
            .padding(Dimensions.regularPadding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Комната ${request.roomNumber}",
                style = OrderTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            StatusBadge(request.status)
        }
        Spacer(modifier = Modifier.height(Dimensions.smallPadding))
        Text(
            text = request.description,
            style = OrderTheme.typography.bodyLarge
        )
        if (request.note.isNotBlank()) {
            Text(
                text = "Примечание: ${request.note}",
                style = OrderTheme.typography.labelMedium,
                color = Color.Gray
            )
        }
        if (request.comment != null) {
            Spacer(modifier = Modifier.height(Dimensions.smallPadding))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Dimensions.smallCornerRadius))
                    .background(Color(0xFFF5F5F5))
                    .padding(Dimensions.smallPadding)
            ) {
                Text(
                    text = "Комментарий: ${request.comment}",
                    style = OrderTheme.typography.labelMedium,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun StatusBadge(status: MaintenanceRequestStatus) {
    val (text, color) = when (status) {
        MaintenanceRequestStatus.PENDING -> "Подана" to Color(0xFFFFA000)
        MaintenanceRequestStatus.ACCEPTED -> "Принято" to Color(0xFF1976D2)
        MaintenanceRequestStatus.COMPLETED -> "Выполнено" to Color(0xFF388E3C)
        MaintenanceRequestStatus.REJECTED -> "Отклонено" to Color(0xFFD32F2F)
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.1f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
