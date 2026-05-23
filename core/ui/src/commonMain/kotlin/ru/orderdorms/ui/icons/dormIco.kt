package ru.orderdorms.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val dormIco: ImageVector
    get() {
        if (_dormIco != null) {
            return _dormIco!!
        }
        _dormIco = ImageVector.Builder(
            name = "dormIco",
            defaultWidth = 19.dp,
            defaultHeight = 19.dp,
            viewportWidth = 19f,
            viewportHeight = 19f
        ).apply {
            path(fill = SolidColor(Color(0xFF34383A))) {
                moveTo(0f, 18.75f)
                verticalLineTo(5.208f)
                horizontalLineTo(5.208f)
                verticalLineTo(0f)
                horizontalLineTo(18.75f)
                verticalLineTo(18.75f)
                horizontalLineTo(0f)
                close()
                moveTo(13.542f, 16.667f)
                horizontalLineTo(16.667f)
                verticalLineTo(2.083f)
                horizontalLineTo(7.292f)
                verticalLineTo(5.208f)
                horizontalLineTo(13.542f)
                verticalLineTo(16.667f)
                close()
                moveTo(7.292f, 16.667f)
                horizontalLineTo(11.458f)
                verticalLineTo(7.292f)
                horizontalLineTo(7.292f)
                verticalLineTo(16.667f)
                close()
                moveTo(2.083f, 16.667f)
                horizontalLineTo(5.208f)
                verticalLineTo(7.292f)
                horizontalLineTo(2.083f)
                verticalLineTo(16.667f)
                close()
            }
        }.build()

        return _dormIco!!
    }

@Suppress("ObjectPropertyName")
private var _dormIco: ImageVector? = null
