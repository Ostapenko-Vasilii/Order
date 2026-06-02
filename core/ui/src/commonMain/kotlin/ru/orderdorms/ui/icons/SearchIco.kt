package ru.orderdorms.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val SeaechIco: ImageVector
    get() {
        if (_SeaechIco != null) {
            return _SeaechIco!!
        }
        _SeaechIco = ImageVector.Builder(
            name = "SeaechIco",
            defaultWidth = 17.dp,
            defaultHeight = 17.dp,
            viewportWidth = 17f,
            viewportHeight = 17f
        ).apply {
            path(fill = SolidColor(Color(0xFFB2B6BA))) {
                moveTo(15.217f, 16.5f)
                lineTo(9.442f, 10.725f)
                curveTo(8.983f, 11.092f, 8.456f, 11.382f, 7.86f, 11.596f)
                curveTo(7.265f, 11.81f, 6.631f, 11.917f, 5.958f, 11.917f)
                curveTo(4.293f, 11.917f, 2.884f, 11.34f, 1.73f, 10.186f)
                curveTo(0.577f, 9.033f, 0f, 7.624f, 0f, 5.958f)
                curveTo(0f, 4.293f, 0.577f, 2.884f, 1.73f, 1.73f)
                curveTo(2.884f, 0.577f, 4.293f, 0f, 5.958f, 0f)
                curveTo(7.624f, 0f, 9.033f, 0.577f, 10.186f, 1.73f)
                curveTo(11.34f, 2.884f, 11.917f, 4.293f, 11.917f, 5.958f)
                curveTo(11.917f, 6.631f, 11.81f, 7.265f, 11.596f, 7.86f)
                curveTo(11.382f, 8.456f, 11.092f, 8.983f, 10.725f, 9.442f)
                lineTo(16.5f, 15.217f)
                lineTo(15.217f, 16.5f)
                close()
                moveTo(5.958f, 10.083f)
                curveTo(7.104f, 10.083f, 8.078f, 9.682f, 8.88f, 8.88f)
                curveTo(9.682f, 8.078f, 10.083f, 7.104f, 10.083f, 5.958f)
                curveTo(10.083f, 4.813f, 9.682f, 3.839f, 8.88f, 3.036f)
                curveTo(8.078f, 2.234f, 7.104f, 1.833f, 5.958f, 1.833f)
                curveTo(4.813f, 1.833f, 3.839f, 2.234f, 3.036f, 3.036f)
                curveTo(2.234f, 3.839f, 1.833f, 4.813f, 1.833f, 5.958f)
                curveTo(1.833f, 7.104f, 2.234f, 8.078f, 3.036f, 8.88f)
                curveTo(3.839f, 9.682f, 4.813f, 10.083f, 5.958f, 10.083f)
                close()
            }
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 0.2f
            ) {
                moveTo(15.217f, 16.5f)
                lineTo(9.442f, 10.725f)
                curveTo(8.983f, 11.092f, 8.456f, 11.382f, 7.86f, 11.596f)
                curveTo(7.265f, 11.81f, 6.631f, 11.917f, 5.958f, 11.917f)
                curveTo(4.293f, 11.917f, 2.884f, 11.34f, 1.73f, 10.186f)
                curveTo(0.577f, 9.033f, 0f, 7.624f, 0f, 5.958f)
                curveTo(0f, 4.293f, 0.577f, 2.884f, 1.73f, 1.73f)
                curveTo(2.884f, 0.577f, 4.293f, 0f, 5.958f, 0f)
                curveTo(7.624f, 0f, 9.033f, 0.577f, 10.186f, 1.73f)
                curveTo(11.34f, 2.884f, 11.917f, 4.293f, 11.917f, 5.958f)
                curveTo(11.917f, 6.631f, 11.81f, 7.265f, 11.596f, 7.86f)
                curveTo(11.382f, 8.456f, 11.092f, 8.983f, 10.725f, 9.442f)
                lineTo(16.5f, 15.217f)
                lineTo(15.217f, 16.5f)
                close()
                moveTo(5.958f, 10.083f)
                curveTo(7.104f, 10.083f, 8.078f, 9.682f, 8.88f, 8.88f)
                curveTo(9.682f, 8.078f, 10.083f, 7.104f, 10.083f, 5.958f)
                curveTo(10.083f, 4.813f, 9.682f, 3.839f, 8.88f, 3.036f)
                curveTo(8.078f, 2.234f, 7.104f, 1.833f, 5.958f, 1.833f)
                curveTo(4.813f, 1.833f, 3.839f, 2.234f, 3.036f, 3.036f)
                curveTo(2.234f, 3.839f, 1.833f, 4.813f, 1.833f, 5.958f)
                curveTo(1.833f, 7.104f, 2.234f, 8.078f, 3.036f, 8.88f)
                curveTo(3.839f, 9.682f, 4.813f, 10.083f, 5.958f, 10.083f)
                close()
            }
        }.build()

        return _SeaechIco!!
    }

@Suppress("ObjectPropertyName")
private var _SeaechIco: ImageVector? = null
