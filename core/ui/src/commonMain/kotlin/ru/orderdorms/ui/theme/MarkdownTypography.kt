package ru.orderdorms.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mikepenz.markdown.m3.markdownTypography
import com.mikepenz.markdown.model.MarkdownTypography

@Composable
fun getMarkdownTypography(): MarkdownTypography {
    val inter = InterFontFamily()
    val manrope = ManropeFontFamily()
    
    return markdownTypography(
        h1 = OrderTheme.typography.displayMedium.copy(
            fontFamily = manrope,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        ),
        h2 = OrderTheme.typography.displaySmall.copy(
            fontFamily = manrope,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        ),
        h3 = OrderTheme.typography.displaySmall.copy(
            fontFamily = manrope,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        ),
        text = OrderTheme.typography.bodyLarge.copy(
            fontFamily = inter,
            fontSize = 16.sp
        ),
        paragraph = OrderTheme.typography.bodyLarge.copy(
            fontFamily = inter,
            fontSize = 16.sp
        )
    )
}
