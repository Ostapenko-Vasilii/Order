package ru.orderdorms.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val priorityInfoIco: ImageVector
    get() {
        if (_priorityInfoIco != null) {
            return _priorityInfoIco!!
        }
        _priorityInfoIco = ImageVector.Builder(
            name = "priorityInfoIco",
            defaultWidth = 20.dp,
            defaultHeight = 20.dp,
            viewportWidth = 20f,
            viewportHeight = 20f
        ).apply {
            path(fill = SolidColor(Color(0xFF34383A))) {
                moveTo(9f, 15f)
                horizontalLineTo(11f)
                verticalLineTo(9f)
                horizontalLineTo(9f)
                verticalLineTo(15f)
                close()
                moveTo(10.712f, 6.713f)
                curveTo(10.904f, 6.521f, 11f, 6.283f, 11f, 6f)
                curveTo(11f, 5.717f, 10.904f, 5.479f, 10.712f, 5.287f)
                curveTo(10.521f, 5.096f, 10.283f, 5f, 10f, 5f)
                curveTo(9.717f, 5f, 9.479f, 5.096f, 9.288f, 5.287f)
                curveTo(9.096f, 5.479f, 9f, 5.717f, 9f, 6f)
                curveTo(9f, 6.283f, 9.096f, 6.521f, 9.288f, 6.713f)
                curveTo(9.479f, 6.904f, 9.717f, 7f, 10f, 7f)
                curveTo(10.283f, 7f, 10.521f, 6.904f, 10.712f, 6.713f)
                close()
                moveTo(10f, 20f)
                curveTo(8.617f, 20f, 7.317f, 19.737f, 6.1f, 19.212f)
                curveTo(4.883f, 18.688f, 3.825f, 17.975f, 2.925f, 17.075f)
                curveTo(2.025f, 16.175f, 1.313f, 15.117f, 0.788f, 13.9f)
                curveTo(0.262f, 12.683f, 0f, 11.383f, 0f, 10f)
                curveTo(0f, 8.617f, 0.262f, 7.317f, 0.788f, 6.1f)
                curveTo(1.313f, 4.883f, 2.025f, 3.825f, 2.925f, 2.925f)
                curveTo(3.825f, 2.025f, 4.883f, 1.313f, 6.1f, 0.788f)
                curveTo(7.317f, 0.262f, 8.617f, 0f, 10f, 0f)
                curveTo(11.383f, 0f, 12.683f, 0.262f, 13.9f, 0.788f)
                curveTo(15.117f, 1.313f, 16.175f, 2.025f, 17.075f, 2.925f)
                curveTo(17.975f, 3.825f, 18.688f, 4.883f, 19.212f, 6.1f)
                curveTo(19.737f, 7.317f, 20f, 8.617f, 20f, 10f)
                curveTo(20f, 11.383f, 19.737f, 12.683f, 19.212f, 13.9f)
                curveTo(18.688f, 15.117f, 17.975f, 16.175f, 17.075f, 17.075f)
                curveTo(16.175f, 17.975f, 15.117f, 18.688f, 13.9f, 19.212f)
                curveTo(12.683f, 19.737f, 11.383f, 20f, 10f, 20f)
                close()
                moveTo(10f, 18f)
                curveTo(12.233f, 18f, 14.125f, 17.225f, 15.675f, 15.675f)
                curveTo(17.225f, 14.125f, 18f, 12.233f, 18f, 10f)
                curveTo(18f, 7.767f, 17.225f, 5.875f, 15.675f, 4.325f)
                curveTo(14.125f, 2.775f, 12.233f, 2f, 10f, 2f)
                curveTo(7.767f, 2f, 5.875f, 2.775f, 4.325f, 4.325f)
                curveTo(2.775f, 5.875f, 2f, 7.767f, 2f, 10f)
                curveTo(2f, 12.233f, 2.775f, 14.125f, 4.325f, 15.675f)
                curveTo(5.875f, 17.225f, 7.767f, 18f, 10f, 18f)
                close()
            }
        }.build()

        return _priorityInfoIco!!
    }

@Suppress("ObjectPropertyName")
private var _priorityInfoIco: ImageVector? = null
