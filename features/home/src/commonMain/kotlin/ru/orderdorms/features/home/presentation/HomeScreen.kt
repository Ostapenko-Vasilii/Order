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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.orderdorms.features.home.domain.model.HomeDashboard
import ru.orderdorms.features.home.presentation.components.QuickActionsRow
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.components.EventCard
import ru.orderdorms.ui.components.NewsCard
import ru.orderdorms.ui.icons.ringIco
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun HomeScreen(
    state: HomeUiState,
    onRefresh: () -> Unit,
    onOpenServices: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF2F2F2))) {
        state.dashboard?.let { dashboard ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = Dimensions.largePadding)
            ) {
                // Top Bar
                item {
                    HomeTopBar(userName = dashboard.userName)
                }

                // Search Bar
                item {
                    HomeSearchBar()
                }

                // Events LazyRow
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = Dimensions.regularPadding),
                        horizontalArrangement = Arrangement.spacedBy(Dimensions.smallPadding),
                        modifier = Modifier.padding(vertical = Dimensions.smallPadding)
                    ) {
                        items(dashboard.events) { event ->
                            EventCard(
                                title = event.title,
                                subtitle = event.date,
                                imageUrl = event.imageUrl,
                                modifier = Modifier.width(280.dp)
                            )
                        }
                    }
                }

                // Important News Section
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
                            .fillMaxWidth()
                    )
                }

                // Quick Actions Section
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
    }
}

@Composable
private fun HomeTopBar(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.regularPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF333333))
            )
            Spacer(modifier = Modifier.width(Dimensions.smallPadding))
            Text(
                text = userName,
                style = OrderTheme.typography.displayMedium.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = " >",
                style = OrderTheme.typography.displayMedium.copy(
                    fontSize = 24.sp,
                    color = Color.Gray
                )
            )
        }
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ringIco,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color(0xFF666666)
            )
        }
    }
}

@Composable
private fun HomeSearchBar() {
    Row(
        modifier = Modifier
            .padding(horizontal = Dimensions.regularPadding)
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(Dimensions.smallCornerRadius))
            .background(Color(0xFFE0E0E0))
            .padding(horizontal = Dimensions.smallPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.width(Dimensions.tinyPadding))
        Text(
            text = "Поиск",
            color = Color.Gray,
            style = OrderTheme.typography.bodyLarge
        )
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
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "Показать Все",
            style = OrderTheme.typography.bodyLarge.copy(
                color = Color.Gray,
                fontSize = 14.sp
            ),
            modifier = Modifier.clickable { onShowAllClick() }
        )
    }
}
