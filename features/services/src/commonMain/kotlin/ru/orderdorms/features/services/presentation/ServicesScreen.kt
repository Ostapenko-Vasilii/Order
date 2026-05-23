package ru.orderdorms.features.services.presentation

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.components.ServiceCard
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun ServicesFlow() {
    val viewModel = rememberServicesViewModel()
    ServicesScreen(
        state = viewModel.state,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onToggleQuickAction = viewModel::toggleQuickAction
    )
}

@Composable
fun ServicesScreen(
    state: ServicesState,
    onSearchQueryChange: (String) -> Unit,
    onToggleQuickAction: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {
        // Header
        Text(
            text = "Все сервисы",
            style = OrderTheme.typography.displayMedium.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(Dimensions.regularPadding)
        )

        // Search Bar
        Box(
            modifier = Modifier
                .padding(horizontal = Dimensions.regularPadding)
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(Dimensions.smallCornerRadius))
                .background(Color(0xFFE0E0E0))
                .padding(horizontal = Dimensions.smallPadding),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Manual search icon placeholder
                Box(modifier = Modifier.size(20.dp).background(Color.Gray, CircleShape))
                Spacer(modifier = Modifier.width(Dimensions.smallPadding))
                Text(
                    text = "Поиск сервисов",
                    color = Color.Gray,
                    style = OrderTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.regularPadding))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = Dimensions.regularPadding,
                end = Dimensions.regularPadding,
                bottom = Dimensions.largePadding
            ),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.smallPadding),
            verticalArrangement = Arrangement.spacedBy(Dimensions.smallPadding),
            modifier = Modifier.fillMaxSize()
        ) {
            // Quick Panel Section
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Быстрая Панель Сервисов",
                    style = OrderTheme.typography.displayMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(vertical = Dimensions.smallPadding)
                )
            }

            items(state.quickActions) { service ->
                ServiceCard(
                    title = service.title,
                    subtitle = service.description,
                    icon = service.icon,
                    onClick = { onToggleQuickAction(service.id) }
                )
            }

            // Fill empty slots
            val emptySlots = 4 - state.quickActions.size
            if (emptySlots > 0) {
                items(emptySlots) {
                    Box(
                        modifier = Modifier
                            .height(100.dp)
                            .clip(RoundedCornerShape(Dimensions.regularCornerRadius))
                            .background(Color(0xFFE0E0E0).copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+", color = Color.Gray, fontSize = 24.sp)
                    }
                }
            }

            // Others Section
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Остальное",
                    style = OrderTheme.typography.displayMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = Dimensions.regularPadding, bottom = Dimensions.smallPadding)
                )
            }

            items(state.filteredOtherServices) { service ->
                ServiceCard(
                    title = service.title,
                    subtitle = service.description,
                    icon = service.icon,
                    onClick = { onToggleQuickAction(service.id) }
                )
            }
        }
    }
}
