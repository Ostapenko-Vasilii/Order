package ru.orderdorms.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ArrowBack: ImageVector
    get() {
        if (_ArrowBack != null) {
            return _ArrowBack!!
        }
        _ArrowBack = ImageVector.Builder(
            name = "ArrowBack",
            defaultWidth = 11.dp,
            defaultHeight = 11.dp,
            viewportWidth = 11f,
            viewportHeight = 11f
        ).apply {
            path(fill = SolidColor(Color(0xFFD2D5D8))) {
                moveTo(2.55f, 6f)
                lineTo(6.283f, 9.733f)
                lineTo(5.333f, 10.667f)
                lineTo(0f, 5.333f)
                lineTo(5.333f, 0f)
                lineTo(6.283f, 0.933f)
                lineTo(2.55f, 4.667f)
                horizontalLineTo(10.667f)
                verticalLineTo(6f)
                horizontalLineTo(2.55f)
                close()
            }
        }.build()

        return _ArrowBack!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowBack: ImageVector? = null
