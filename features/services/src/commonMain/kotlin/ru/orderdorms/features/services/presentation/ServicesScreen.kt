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
import order.core.ui.generated.resources.Res
import order.core.ui.generated.resources.all_services_title
import order.core.ui.generated.resources.other_services_title
import order.core.ui.generated.resources.quick_panel_title
import org.jetbrains.compose.resources.stringResource
import ru.orderdorms.core.domain.model.Service
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.components.ServiceCard
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun ServicesFlow(onOpenService: (Service) -> Unit = {}) {
    val viewModel = rememberServicesViewModel()
    ServicesScreen(
        state = viewModel.state,
        onToggleQuickAction = viewModel::toggleQuickAction,
        onServiceClick = onOpenService
    )
}

@Composable
fun ServicesScreen(
    state: ServicesState,
    onToggleQuickAction: (String) -> Unit,
    onServiceClick: (Service) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {
        // Header
        Text(
            text = stringResource(Res.string.all_services_title),
            style = OrderTheme.typography.displayMedium.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(Dimensions.regularPadding)
        )


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
                    text = stringResource(Res.string.quick_panel_title),
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
                    onClick = { onServiceClick(service) },
                    onMoreClick = { onToggleQuickAction(service.id) }
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
                    text = stringResource(Res.string.other_services_title),
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
                    onClick = { onServiceClick(service) },
                    onMoreClick = { onToggleQuickAction(service.id) }
                )
            }
        }
    }
}
