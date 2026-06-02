package ru.orderdorms.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ArrowRightIco: ImageVector
    get() {
        if (_ArrowRightIco != null) {
            return _ArrowRightIco!!
        }
        _ArrowRightIco = ImageVector.Builder(
            name = "ArrowRightIco",
            defaultWidth = 8.dp,
            defaultHeight = 12.dp,
            viewportWidth = 8f,
            viewportHeight = 12f
        ).apply {
            path(fill = SolidColor(Color(0xFF34383A))) {
                moveTo(4.6f, 6f)
                lineTo(0f, 1.4f)
                lineTo(1.4f, 0f)
                lineTo(7.4f, 6f)
                lineTo(1.4f, 12f)
                lineTo(0f, 10.6f)
                lineTo(4.6f, 6f)
                close()
            }
        }.build()

        return _ArrowRightIco!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowRightIco: ImageVector? = null
