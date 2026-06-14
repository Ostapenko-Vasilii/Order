package ru.orderdorms.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import order.core.ui.generated.resources.Inter_Bold
import order.core.ui.generated.resources.Inter_Medium
import order.core.ui.generated.resources.Inter_Regular
import order.core.ui.generated.resources.Manrope_Bold
import order.core.ui.generated.resources.Manrope_SemiBold
import order.core.ui.generated.resources.Res
import org.jetbrains.compose.resources.Font


@Composable
fun InterFontFamily() = FontFamily(
    Font(resource = Res.font.Inter_Regular, weight = FontWeight.Normal),

    Font(resource = Res.font.Inter_Regular, weight = FontWeight.Normal),
    Font(resource = Res.font.Inter_Medium, weight = FontWeight.Medium),
    Font(resource = Res.font.Inter_Bold, weight = FontWeight.Bold)
)

@Composable
fun ManropeFontFamily() = FontFamily(
    Font(resource = Res.font.Manrope_Bold, weight = FontWeight.Bold),
    Font( resource = Res.font.Manrope_SemiBold, weight = FontWeight.SemiBold)
)