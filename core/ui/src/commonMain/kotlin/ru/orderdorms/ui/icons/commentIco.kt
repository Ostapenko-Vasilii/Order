package ru.orderdorms.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val commentIco: ImageVector
    get() {
        if (_commentIco != null) {
            return _commentIco!!
        }
        _commentIco = ImageVector.Builder(
            name = "commentIco",
            defaultWidth = 25.dp,
            defaultHeight = 25.dp,
            viewportWidth = 25f,
            viewportHeight = 25f
        ).apply {
            path(fill = SolidColor(Color(0xFF34383A))) {
                moveTo(5f, 15f)
                horizontalLineTo(20f)
                verticalLineTo(12.5f)
                horizontalLineTo(5f)
                verticalLineTo(15f)
                close()
                moveTo(5f, 11.25f)
                horizontalLineTo(20f)
                verticalLineTo(8.75f)
                horizontalLineTo(5f)
                verticalLineTo(11.25f)
                close()
                moveTo(5f, 7.5f)
                horizontalLineTo(20f)
                verticalLineTo(5f)
                horizontalLineTo(5f)
                verticalLineTo(7.5f)
                close()
                moveTo(25f, 25f)
                lineTo(20f, 20f)
                horizontalLineTo(2.5f)
                curveTo(1.813f, 20f, 1.224f, 19.755f, 0.734f, 19.266f)
                curveTo(0.245f, 18.776f, 0f, 18.188f, 0f, 17.5f)
                verticalLineTo(2.5f)
                curveTo(0f, 1.813f, 0.245f, 1.224f, 0.734f, 0.734f)
                curveTo(1.224f, 0.245f, 1.813f, 0f, 2.5f, 0f)
                horizontalLineTo(22.5f)
                curveTo(23.188f, 0f, 23.776f, 0.245f, 24.266f, 0.734f)
                curveTo(24.755f, 1.224f, 25f, 1.813f, 25f, 2.5f)
                verticalLineTo(25f)
                close()
                moveTo(2.5f, 17.5f)
                horizontalLineTo(21.063f)
                lineTo(22.5f, 18.906f)
                verticalLineTo(2.5f)
                horizontalLineTo(2.5f)
                verticalLineTo(17.5f)
                close()
            }
        }.build()

        return _commentIco!!
    }

@Suppress("ObjectPropertyName")
private var _commentIco: ImageVector? = null
