package ru.orderdorms.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val repairIco: ImageVector
    get() {
        if (_repairIco != null) {
            return _repairIco!!
        }
        _repairIco = ImageVector.Builder(
            name = "repairIco",
            defaultWidth = 20.dp,
            defaultHeight = 16.dp,
            viewportWidth = 20f,
            viewportHeight = 16f
        ).apply {
            path(fill = SolidColor(Color(0xFF34383A))) {
                moveTo(0f, 16f)
                verticalLineTo(6f)
                curveTo(0f, 5.45f, 0.196f, 4.979f, 0.587f, 4.588f)
                curveTo(0.979f, 4.196f, 1.45f, 4f, 2f, 4f)
                horizontalLineTo(5f)
                verticalLineTo(2f)
                curveTo(5f, 1.45f, 5.196f, 0.979f, 5.588f, 0.587f)
                curveTo(5.979f, 0.196f, 6.45f, 0f, 7f, 0f)
                horizontalLineTo(13f)
                curveTo(13.55f, 0f, 14.021f, 0.196f, 14.413f, 0.587f)
                curveTo(14.804f, 0.979f, 15f, 1.45f, 15f, 2f)
                verticalLineTo(4f)
                horizontalLineTo(18f)
                curveTo(18.55f, 4f, 19.021f, 4.196f, 19.413f, 4.588f)
                curveTo(19.804f, 4.979f, 20f, 5.45f, 20f, 6f)
                verticalLineTo(16f)
                horizontalLineTo(0f)
                close()
                moveTo(6f, 11f)
                verticalLineTo(12f)
                horizontalLineTo(4f)
                verticalLineTo(11f)
                horizontalLineTo(2f)
                verticalLineTo(14f)
                horizontalLineTo(18f)
                verticalLineTo(11f)
                horizontalLineTo(16f)
                verticalLineTo(12f)
                horizontalLineTo(14f)
                verticalLineTo(11f)
                horizontalLineTo(6f)
                close()
                moveTo(2f, 6f)
                verticalLineTo(9f)
                horizontalLineTo(4f)
                verticalLineTo(8f)
                horizontalLineTo(6f)
                verticalLineTo(9f)
                horizontalLineTo(14f)
                verticalLineTo(8f)
                horizontalLineTo(16f)
                verticalLineTo(9f)
                horizontalLineTo(18f)
                verticalLineTo(6f)
                horizontalLineTo(2f)
                close()
                moveTo(7f, 4f)
                horizontalLineTo(13f)
                verticalLineTo(2f)
                horizontalLineTo(7f)
                verticalLineTo(4f)
                close()
            }
        }.build()

        return _repairIco!!
    }

@Suppress("ObjectPropertyName")
private var _repairIco: ImageVector? = null
