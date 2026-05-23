package ru.orderdorms.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val priorityHighIco: ImageVector
    get() {
        if (_priorityHighIco != null) {
            return _priorityHighIco!!
        }
        _priorityHighIco = ImageVector.Builder(
            name = "priorityHighIco",
            defaultWidth = 4.dp,
            defaultHeight = 18.dp,
            viewportWidth = 4f,
            viewportHeight = 18f
        ).apply {
            path(fill = SolidColor(Color(0xFF34383A))) {
                moveTo(2f, 18f)
                curveTo(1.45f, 18f, 0.979f, 17.804f, 0.587f, 17.413f)
                curveTo(0.196f, 17.021f, 0f, 16.55f, 0f, 16f)
                curveTo(0f, 15.45f, 0.196f, 14.979f, 0.587f, 14.587f)
                curveTo(0.979f, 14.196f, 1.45f, 14f, 2f, 14f)
                curveTo(2.55f, 14f, 3.021f, 14.196f, 3.412f, 14.587f)
                curveTo(3.804f, 14.979f, 4f, 15.45f, 4f, 16f)
                curveTo(4f, 16.55f, 3.804f, 17.021f, 3.412f, 17.413f)
                curveTo(3.021f, 17.804f, 2.55f, 18f, 2f, 18f)
                close()
                moveTo(0f, 12f)
                verticalLineTo(0f)
                horizontalLineTo(4f)
                verticalLineTo(12f)
                horizontalLineTo(0f)
                close()
            }
        }.build()

        return _priorityHighIco!!
    }

@Suppress("ObjectPropertyName")
private var _priorityHighIco: ImageVector? = null
