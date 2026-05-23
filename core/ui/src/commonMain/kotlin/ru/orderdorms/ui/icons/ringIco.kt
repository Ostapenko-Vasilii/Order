package ru.orderdorms.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ringIco: ImageVector
    get() {
        if (_ringIco != null) {
            return _ringIco!!
        }
        _ringIco = ImageVector.Builder(
            name = "ringIco",
            defaultWidth = 16.dp,
            defaultHeight = 20.dp,
            viewportWidth = 16f,
            viewportHeight = 20f
        ).apply {
            path(fill = SolidColor(Color(0xFF1D1B20))) {
                moveTo(0f, 17f)
                verticalLineTo(15f)
                horizontalLineTo(2f)
                verticalLineTo(8f)
                curveTo(2f, 6.617f, 2.417f, 5.392f, 3.25f, 4.325f)
                curveTo(4.083f, 3.242f, 5.167f, 2.533f, 6.5f, 2.2f)
                verticalLineTo(1.5f)
                curveTo(6.5f, 1.083f, 6.642f, 0.733f, 6.925f, 0.45f)
                curveTo(7.225f, 0.15f, 7.583f, 0f, 8f, 0f)
                curveTo(8.417f, 0f, 8.767f, 0.15f, 9.05f, 0.45f)
                curveTo(9.35f, 0.733f, 9.5f, 1.083f, 9.5f, 1.5f)
                verticalLineTo(2.2f)
                curveTo(10.833f, 2.533f, 11.917f, 3.242f, 12.75f, 4.325f)
                curveTo(13.583f, 5.392f, 14f, 6.617f, 14f, 8f)
                verticalLineTo(15f)
                horizontalLineTo(16f)
                verticalLineTo(17f)
                horizontalLineTo(0f)
                close()
                moveTo(8f, 20f)
                curveTo(7.45f, 20f, 6.975f, 19.808f, 6.575f, 19.425f)
                curveTo(6.192f, 19.025f, 6f, 18.55f, 6f, 18f)
                horizontalLineTo(10f)
                curveTo(10f, 18.55f, 9.8f, 19.025f, 9.4f, 19.425f)
                curveTo(9.017f, 19.808f, 8.55f, 20f, 8f, 20f)
                close()
                moveTo(4f, 15f)
                horizontalLineTo(12f)
                verticalLineTo(8f)
                curveTo(12f, 6.9f, 11.608f, 5.958f, 10.825f, 5.175f)
                curveTo(10.042f, 4.392f, 9.1f, 4f, 8f, 4f)
                curveTo(6.9f, 4f, 5.958f, 4.392f, 5.175f, 5.175f)
                curveTo(4.392f, 5.958f, 4f, 6.9f, 4f, 8f)
                verticalLineTo(15f)
                close()
            }
        }.build()

        return _ringIco!!
    }

@Suppress("ObjectPropertyName")
private var _ringIco: ImageVector? = null
