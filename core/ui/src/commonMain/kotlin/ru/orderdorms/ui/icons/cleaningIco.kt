package ru.orderdorms.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val cleaningIco: ImageVector
    get() {
        if (_cleaningIco != null) {
            return _cleaningIco!!
        }
        _cleaningIco = ImageVector.Builder(
            name = "cleaningIco",
            defaultWidth = 18.dp,
            defaultHeight = 22.dp,
            viewportWidth = 18f,
            viewportHeight = 22f
        ).apply {
            path(fill = SolidColor(Color(0xFF34383A))) {
                moveTo(0f, 22f)
                verticalLineTo(15f)
                curveTo(0f, 13.617f, 0.488f, 12.438f, 1.462f, 11.462f)
                curveTo(2.438f, 10.488f, 3.617f, 10f, 5f, 10f)
                horizontalLineTo(6f)
                verticalLineTo(2f)
                curveTo(6f, 1.45f, 6.196f, 0.979f, 6.588f, 0.587f)
                curveTo(6.979f, 0.196f, 7.45f, 0f, 8f, 0f)
                horizontalLineTo(10f)
                curveTo(10.55f, 0f, 11.021f, 0.196f, 11.413f, 0.587f)
                curveTo(11.804f, 0.979f, 12f, 1.45f, 12f, 2f)
                verticalLineTo(10f)
                horizontalLineTo(13f)
                curveTo(14.383f, 10f, 15.563f, 10.488f, 16.538f, 11.462f)
                curveTo(17.513f, 12.438f, 18f, 13.617f, 18f, 15f)
                verticalLineTo(22f)
                horizontalLineTo(0f)
                close()
                moveTo(2f, 20f)
                horizontalLineTo(4f)
                verticalLineTo(17f)
                curveTo(4f, 16.717f, 4.096f, 16.479f, 4.287f, 16.288f)
                curveTo(4.479f, 16.096f, 4.717f, 16f, 5f, 16f)
                curveTo(5.283f, 16f, 5.521f, 16.096f, 5.713f, 16.288f)
                curveTo(5.904f, 16.479f, 6f, 16.717f, 6f, 17f)
                verticalLineTo(20f)
                horizontalLineTo(8f)
                verticalLineTo(17f)
                curveTo(8f, 16.717f, 8.096f, 16.479f, 8.288f, 16.288f)
                curveTo(8.479f, 16.096f, 8.717f, 16f, 9f, 16f)
                curveTo(9.283f, 16f, 9.521f, 16.096f, 9.712f, 16.288f)
                curveTo(9.904f, 16.479f, 10f, 16.717f, 10f, 17f)
                verticalLineTo(20f)
                horizontalLineTo(12f)
                verticalLineTo(17f)
                curveTo(12f, 16.717f, 12.096f, 16.479f, 12.288f, 16.288f)
                curveTo(12.479f, 16.096f, 12.717f, 16f, 13f, 16f)
                curveTo(13.283f, 16f, 13.521f, 16.096f, 13.712f, 16.288f)
                curveTo(13.904f, 16.479f, 14f, 16.717f, 14f, 17f)
                verticalLineTo(20f)
                horizontalLineTo(16f)
                verticalLineTo(15f)
                curveTo(16f, 14.167f, 15.708f, 13.458f, 15.125f, 12.875f)
                curveTo(14.542f, 12.292f, 13.833f, 12f, 13f, 12f)
                horizontalLineTo(5f)
                curveTo(4.167f, 12f, 3.458f, 12.292f, 2.875f, 12.875f)
                curveTo(2.292f, 13.458f, 2f, 14.167f, 2f, 15f)
                verticalLineTo(20f)
                close()
                moveTo(10f, 10f)
                verticalLineTo(2f)
                horizontalLineTo(8f)
                verticalLineTo(10f)
                horizontalLineTo(10f)
                close()
            }
        }.build()

        return _cleaningIco!!
    }

@Suppress("ObjectPropertyName")
private var _cleaningIco: ImageVector? = null
