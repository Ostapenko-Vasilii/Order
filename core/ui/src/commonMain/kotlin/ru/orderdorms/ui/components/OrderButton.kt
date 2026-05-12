package ru.orderdorms.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun OrderButton(
    onClick: () -> Unit,
    text: String,
    isActive: Boolean = true,
    isLoading: Boolean = false,
    containerColor: androidx.compose.ui.graphics.Color = OrderTheme.colors.activeColor,
    contentColor: androidx.compose.ui.graphics.Color = OrderTheme.colors.primaryTextColor,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .widthIn(max = 400.dp)
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(Dimensions.regularCornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = OrderTheme.colors.bgPlaceholderColor.copy(alpha = 0.5f),
            disabledContentColor = OrderTheme.colors.primaryTextColor.copy(alpha = 0.7f)
        ),
        enabled = isActive && !isLoading
    ) {
        AnimatedContent(
            targetState = isLoading,
            transitionSpec = {
                fadeIn(animationSpec = tween(200)) togetherWith
                        fadeOut(animationSpec = tween(200))
            },
            label = "ButtonContentAnimation"
        ) { loading ->
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = OrderTheme.colors.primaryTextColor,
                    strokeWidth = 3.dp,
                    strokeCap = StrokeCap.Round
                )
            } else {
                Text(
                    text = text,
                    style = OrderTheme.typography.bodyLarge
                )
            }
        }
    }
}