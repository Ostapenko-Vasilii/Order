package ru.orderdorms.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import order.core.ui.generated.resources.Res
import order.core.ui.generated.resources.event_date_template
import order.core.ui.generated.resources.event_title_template
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun EventCard(
    title: String,
    subtitle: String,
    imageUrl: String?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(Dimensions.largeCornerRadius))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF5F5F5),
                        Color(0xFFE0E0E0)
                    )
                )
            ),
    ) {
        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Gradient overlay for text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.5f)
                            ),
                            startY = 0.5f
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(Dimensions.regularPadding)
        ) {
            Text(
                text = title,
                style = OrderTheme.typography.displayMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (imageUrl != null) Color.White else Color(0xFF374151)
                )
            )
            Text(
                text = subtitle,
                style = OrderTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp,
                    color = if (imageUrl != null) Color.White.copy(alpha = 0.8f) else Color(0xFF6B7280)
                )
            )
        }
    }
}

@Preview
@Composable
fun EventCardPreview() {
    OrderTheme {
        Box(modifier = Modifier.padding(Dimensions.largePadding)) {
            EventCard(
                title = stringResource(Res.string.event_title_template),
                subtitle = stringResource(Res.string.event_date_template),
                imageUrl = null
            )
        }
    }
}
