package ru.orderdorms.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.orderdorms.ui.resources.Res
import ru.orderdorms.ui.resources.news_text_template
import ru.orderdorms.ui.resources.news_title_template
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.orderdorms.ui.icons.priorityHighIco
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun NewsCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = (-6).dp)
                .background(
                    color = Color(0xFF212121),
                    shape = RoundedCornerShape(Dimensions.regularCornerRadius)
                )
        )
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFADADAD),
                            Color(0xFF696E71)
                        ),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    ),
                    shape = RoundedCornerShape(Dimensions.regularCornerRadius)
                )
                .padding(Dimensions.regularPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(Dimensions.regularPadding)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimensions.tinyPadding)
            ) {
                Text(
                    text = title,
                    style = OrderTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    style = OrderTheme.typography.labelLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

@Preview
@Composable
fun NewsCardPreview() {
    OrderTheme {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(24.dp)
        ) {
            NewsCard(
                title = stringResource(Res.string.news_title_template),
                subtitle = stringResource(Res.string.news_text_template),
                icon = priorityHighIco
            )
        }
    }
}
