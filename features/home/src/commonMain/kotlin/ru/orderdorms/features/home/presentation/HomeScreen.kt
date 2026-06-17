package ru.orderdorms.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.orderdorms.features.events.domain.model.Event
import ru.orderdorms.features.events.presentation.components.HomeEventsRow
import ru.orderdorms.features.home.presentation.components.QuickActionsRow
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.components.MarkDownBottomSheet
import ru.orderdorms.ui.components.NewsCard
import ru.orderdorms.ui.icons.ArrowRightIco
import ru.orderdorms.ui.icons.ringIco
import ru.orderdorms.ui.theme.OrderTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeUiState,
    onRefresh: () -> Unit,
    onOpenServices: () -> Unit,
) {
    var selectedEvent by remember { mutableStateOf<Event?>(null) }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF2F2F2))) {
        state.dashboard?.let { dashboard ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = Dimensions.largePadding)
            ) {
                item {
                    HomeTopBar(userName = dashboard.userName)
                }
                item {
                    HomeEventsRow(
                        events = dashboard.events,
                        onEventClick = { selectedEvent = it }
                    )
                }

                item {
                    SectionHeader(title = "Важные Новости", onShowAllClick = onOpenServices)
                }

                items(dashboard.importantNews) { news ->
                    NewsCard(
                        title = news.title,
                        subtitle = news.subtitle,
                        icon = news.icon,
                        modifier = Modifier
                            .padding(horizontal = Dimensions.regularPadding, vertical = Dimensions.tinyPadding)
                            .height(100.dp)
                            .fillMaxWidth()
                    )
                }

                item {
                    SectionHeader(title = "Быстрые Действия", onShowAllClick = onOpenServices)
                }

                item {
                    val uriHandler = LocalUriHandler.current
                    QuickActionsRow(
                        services = dashboard.quickActions,
                        onItemClick = { service ->
                            val url = service.url
                            if (url != null) {
                                uriHandler.openUri(url)
                            } else {
                                onOpenServices()
                            }
                        }
                    )
                }
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        state.errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center).clickable { onRefresh() }
            )
        }

        selectedEvent?.let { event ->
            MarkDownBottomSheet(
                data = event.descriptionMarkdown,
                onDismiss = {  selectedEvent = null }
            )
        }
    }
}

@Composable
private fun HomeTopBar(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.regularPadding)
            .padding(top = Dimensions.extraLargePadding, bottom = Dimensions.extraSmallPadding),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF333333))
            )
            Spacer(modifier = Modifier.width(Dimensions.smallPadding))
            Text(
                text = userName,
                style = OrderTheme.typography.displayMedium.copy(
                    fontSize = 24.sp
                )
            )
            Icon(
                imageVector = ArrowRightIco,
                contentDescription = null,
                modifier = Modifier.size(20.dp).padding(start = Dimensions.tinyPadding, bottom = Dimensions.tinyPadding + 2.dp),
                tint = Color(0xFF34383A))
        }
        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .background(Color(0xFF666666)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ringIco,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Color(0xFFE0E0E0)
            )
        }
    }
}


@Composable
private fun SectionHeader(
    title: String,
    onShowAllClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.regularPadding, vertical = Dimensions.smallPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = OrderTheme.typography.displayMedium.copy(
                fontSize = 18.sp,
            )
        )
        Text(
            text = "Показать Все",
            style = OrderTheme.typography.labelLarge.copy(
                color = Color.Gray,
            ),
            modifier = Modifier.clickable { onShowAllClick() }
        )
    }
}
