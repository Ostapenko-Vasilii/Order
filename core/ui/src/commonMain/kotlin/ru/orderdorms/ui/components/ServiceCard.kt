package ru.orderdorms.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.orderdorms.ui.icons.moreIco
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun ServiceCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    showMore: Boolean = true,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Dimensions.regularCornerRadius))
            .height(120.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFADADAD),
                        Color(0xFF696E71)
                    ),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            )
            .clickable { onClick() }
            .padding(Dimensions.smallPadding)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(Dimensions.smallPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(26.dp),
                )
                if (showMore) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(shape = CircleShape)
                            .clickable { onMoreClick() },
                        contentAlignment = Alignment.Center
                    ){
                    Icon(
                        imageVector = moreIco,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                    )
                    }
                }
            }
            
            Column(verticalArrangement = Arrangement.spacedBy(Dimensions.tinyPadding)) {
                Text(
                    text = title,
                    style = OrderTheme.typography.bodyLarge.copy(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    lineHeight = 16.sp
                )
                Text(
                    text = subtitle,
                    style = OrderTheme.typography.bodySmall.copy(
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 10.sp
                    )
                )
            }
        }
    }
}
