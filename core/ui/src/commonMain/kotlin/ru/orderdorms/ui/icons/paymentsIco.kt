package ru.orderdorms.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val paymentsIco: ImageVector
    get() {
        if (_paymentsIco != null) {
            return _paymentsIco!!
        }
        _paymentsIco = ImageVector.Builder(
            name = "paymentsIco",
            defaultWidth = 28.dp,
            defaultHeight = 20.dp,
            viewportWidth = 28f,
            viewportHeight = 20f
        ).apply {
            path(fill = SolidColor(Color(0xFF34383A))) {
                moveTo(16.25f, 11.25f)
                curveTo(15.208f, 11.25f, 14.323f, 10.885f, 13.594f, 10.156f)
                curveTo(12.865f, 9.427f, 12.5f, 8.542f, 12.5f, 7.5f)
                curveTo(12.5f, 6.458f, 12.865f, 5.573f, 13.594f, 4.844f)
                curveTo(14.323f, 4.115f, 15.208f, 3.75f, 16.25f, 3.75f)
                curveTo(17.292f, 3.75f, 18.177f, 4.115f, 18.906f, 4.844f)
                curveTo(19.635f, 5.573f, 20f, 6.458f, 20f, 7.5f)
                curveTo(20f, 8.542f, 19.635f, 9.427f, 18.906f, 10.156f)
                curveTo(18.177f, 10.885f, 17.292f, 11.25f, 16.25f, 11.25f)
                close()
                moveTo(7.5f, 15f)
                curveTo(6.813f, 15f, 6.224f, 14.755f, 5.734f, 14.266f)
                curveTo(5.245f, 13.776f, 5f, 13.188f, 5f, 12.5f)
                verticalLineTo(2.5f)
                curveTo(5f, 1.813f, 5.245f, 1.224f, 5.734f, 0.734f)
                curveTo(6.224f, 0.245f, 6.813f, 0f, 7.5f, 0f)
                horizontalLineTo(25f)
                curveTo(25.688f, 0f, 26.276f, 0.245f, 26.766f, 0.734f)
                curveTo(27.255f, 1.224f, 27.5f, 1.813f, 27.5f, 2.5f)
                verticalLineTo(12.5f)
                curveTo(27.5f, 13.188f, 27.255f, 13.776f, 26.766f, 14.266f)
                curveTo(26.276f, 14.755f, 25.688f, 15f, 25f, 15f)
                horizontalLineTo(7.5f)
                close()
                moveTo(10f, 12.5f)
                horizontalLineTo(22.5f)
                curveTo(22.5f, 11.813f, 22.745f, 11.224f, 23.234f, 10.734f)
                curveTo(23.724f, 10.245f, 24.313f, 10f, 25f, 10f)
                verticalLineTo(5f)
                curveTo(24.313f, 5f, 23.724f, 4.755f, 23.234f, 4.266f)
                curveTo(22.745f, 3.776f, 22.5f, 3.188f, 22.5f, 2.5f)
                horizontalLineTo(10f)
                curveTo(10f, 3.188f, 9.755f, 3.776f, 9.266f, 4.266f)
                curveTo(8.776f, 4.755f, 8.188f, 5f, 7.5f, 5f)
                verticalLineTo(10f)
                curveTo(8.188f, 10f, 8.776f, 10.245f, 9.266f, 10.734f)
                curveTo(9.755f, 11.224f, 10f, 11.813f, 10f, 12.5f)
                close()
                moveTo(23.75f, 20f)
                horizontalLineTo(2.5f)
                curveTo(1.813f, 20f, 1.224f, 19.755f, 0.734f, 19.266f)
                curveTo(0.245f, 18.776f, 0f, 18.188f, 0f, 17.5f)
                verticalLineTo(3.75f)
                horizontalLineTo(2.5f)
                verticalLineTo(17.5f)
                horizontalLineTo(23.75f)
                verticalLineTo(20f)
                close()
            }
        }.build()

        return _paymentsIco!!
    }

@Suppress("ObjectPropertyName")
private var _paymentsIco: ImageVector? = null
