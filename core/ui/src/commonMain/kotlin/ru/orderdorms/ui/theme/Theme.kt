package ru.orderdorms.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Typography
import com.mikepenz.markdown.compose.LocalMarkdownTypography
import com.mikepenz.markdown.model.MarkdownTypography

data class AppColors(
    val onBackground: Color,
    val primaryTextColor : Color,
    val activeColor: Color ,
    val bgPlaceholderColor: Color = Color.Gray
)



private val LightAppColors = AppColors(
    onBackground = OnBackground,
    primaryTextColor = PrimaryTextColor,
    activeColor = ActiveColor,
)

private val DarkAppColors = AppColors(
    onBackground = OnBackgroundDark,
    primaryTextColor = PrimaryTextColorDark,
    activeColor = ActiveColorDark,
)

private val LocalAppColors = staticCompositionLocalOf { LightAppColors }
private val LocalAppTypography = staticCompositionLocalOf { Typography() }

private fun buildColorSchemeFromApp(colors: AppColors): ColorScheme =
    lightColorScheme(
        onBackground = colors.onBackground,
    )

private fun buildDarkColorSchemeFromApp(colors: AppColors): ColorScheme =
    darkColorScheme(
        onBackground = colors.onBackground,
    )

@Composable
fun OrderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val appColors = if (darkTheme) DarkAppColors else LightAppColors
    val materialColors = if (darkTheme) buildDarkColorSchemeFromApp(appColors) else buildColorSchemeFromApp(appColors)

    val appTypography = getAppTypography()
    val markdownTypography = getMarkdownTypography()

    CompositionLocalProvider(
        LocalAppColors provides appColors,
        LocalAppTypography provides appTypography,
        LocalMarkdownTypography provides markdownTypography
    ) {
        MaterialTheme(
            colorScheme = materialColors,
            typography = appTypography,
            content = content,
        )
    }
}

object OrderTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    val typography: Typography
        @Composable
        get() = LocalAppTypography.current

    val markdownTypography: MarkdownTypography
        @Composable
        get() = LocalMarkdownTypography.current
}
