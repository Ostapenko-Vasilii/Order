package ru.orderdorms.features.news.presentation

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.orderdorms.features.news.domain.model.NewsItem
import ru.orderdorms.features.news.domain.model.NewsType
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun NewsFlow() {
    val viewModel = rememberNewsViewModel()
    NewsScreen(
        state = viewModel.state,
        onTypeSelect = viewModel::onTypeSelect,
        onRefresh = viewModel::loadNews,
    )
}

@Composable
fun NewsScreen(
    state: NewsState,
    onTypeSelect: (NewsType?) -> Unit,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {
        Text(
            text = "Все новости",
            style = OrderTheme.typography.displayMedium.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(Dimensions.regularPadding)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = Dimensions.regularPadding),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                FilterChip(
                    text = "Все",
                    isSelected = state.selectedType == null,
                    onClick = { onTypeSelect(null) }
                )
            }
            items(NewsType.entries) { type ->
                FilterChip(
                    text = type.title,
                    isSelected = state.selectedType == type,
                    onClick = { onTypeSelect(type) }
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.regularPadding))

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = OrderTheme.colors.activeColor)
            }
        } else if (state.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = state.error.message,
                    color = Color.Red,
                    modifier = Modifier.clickable { onRefresh() }
                )
            }
        } else {
            val groupedNews = remember(state.filteredNews) {
                val now = kotlinx.datetime.Clock.System.now()
                val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
                state.filteredNews.groupBy {
                    if (it.timestamp.toLocalDateTime(TimeZone.currentSystemDefault()).date == today) {
                        "СЕГОДНЯ"
                    } else {
                        "РАНЕЕ"
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = Dimensions.largePadding),
                verticalArrangement = Arrangement.spacedBy(Dimensions.regularPadding)
            ) {
                groupedNews.forEach { (header, items) ->
                    item {
                        Text(
                            text = header,
                            style = OrderTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            ),
                            modifier = Modifier.padding(horizontal = Dimensions.regularPadding)
                        )
                    }
                    items(items) { item ->
                        NewsCard(item = item)
                    }
                }
            }
        }
    }
}

@Composable
fun FilterChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color(0xFF333333) else Color(0xFFE0E0E0))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun NewsCard(item: NewsItem) {
    val headerColor = when (item.type) {
        NewsType.URGENT -> Color(0xFFE57373)
        NewsType.PERSONAL -> Color(0xFF81C784)
        NewsType.ANNOUNCEMENT -> Color(0xFF1976D2)
        NewsType.EVENT -> Color(0xFF9E9E9E)
        else -> Color.Gray
    }

    val hasColoredHeader = item.type == NewsType.URGENT || item.type == NewsType.PERSONAL

    Column(
        modifier = Modifier
            .padding(horizontal = Dimensions.regularPadding)
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimensions.regularCornerRadius))
            .background(Color.White)
    ) {
        if (hasColoredHeader) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(headerColor)
                    .padding(horizontal = Dimensions.regularPadding),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = item.type.title,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }

        Column(modifier = Modifier.padding(Dimensions.regularPadding)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = item.title,
                    style = OrderTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = formatTimestamp(item.timestamp),
                    style = OrderTheme.typography.labelMedium,
                    color = Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = item.content,
                style = OrderTheme.typography.bodyLarge,
                color = Color.DarkGray
            )

            if (!hasColoredHeader) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(headerColor.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = item.type.title,
                        color = headerColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

private fun formatTimestamp(timestamp: Instant): String {
    val now = kotlinx.datetime.Clock.System.now()
    val diff = now.epochSeconds - timestamp.epochSeconds
    return when {
        diff < 60L -> "только что"
        diff < 3600L -> "${diff / 60L} мин. назад"
        diff < 86400L -> "${diff / 3600L} ч. назад"
        else -> "${diff / 86400L} дн. назад"
    }
}
