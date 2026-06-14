package ru.orderdorms.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val moreIco: ImageVector
    get() {
        if (_moreIco != null) {
            return _moreIco!!
        }
        _moreIco = ImageVector.Builder(
            name = "moreIco",
            defaultWidth = 22.dp,
            defaultHeight = 6.dp,
            viewportWidth = 22f,
            viewportHeight = 6f
        ).apply {
            path(fill = SolidColor(Color(0xFF34383A))) {
                moveTo(2.667f, 5.333f)
                curveTo(1.933f, 5.333f, 1.306f, 5.072f, 0.783f, 4.55f)
                curveTo(0.261f, 4.028f, 0f, 3.4f, 0f, 2.667f)
                curveTo(0f, 1.933f, 0.261f, 1.306f, 0.783f, 0.783f)
                curveTo(1.306f, 0.261f, 1.933f, 0f, 2.667f, 0f)
                curveTo(3.4f, 0f, 4.028f, 0.261f, 4.55f, 0.783f)
                curveTo(5.072f, 1.306f, 5.333f, 1.933f, 5.333f, 2.667f)
                curveTo(5.333f, 3.4f, 5.072f, 4.028f, 4.55f, 4.55f)
                curveTo(4.028f, 5.072f, 3.4f, 5.333f, 2.667f, 5.333f)
                close()
                moveTo(10.667f, 5.333f)
                curveTo(9.933f, 5.333f, 9.306f, 5.072f, 8.783f, 4.55f)
                curveTo(8.261f, 4.028f, 8f, 3.4f, 8f, 2.667f)
                curveTo(8f, 1.933f, 8.261f, 1.306f, 8.783f, 0.783f)
                curveTo(9.306f, 0.261f, 9.933f, 0f, 10.667f, 0f)
                curveTo(11.4f, 0f, 12.028f, 0.261f, 12.55f, 0.783f)
                curveTo(13.072f, 1.306f, 13.333f, 1.933f, 13.333f, 2.667f)
                curveTo(13.333f, 3.4f, 13.072f, 4.028f, 12.55f, 4.55f)
                curveTo(12.028f, 5.072f, 11.4f, 5.333f, 10.667f, 5.333f)
                close()
                moveTo(18.667f, 5.333f)
                curveTo(17.933f, 5.333f, 17.306f, 5.072f, 16.783f, 4.55f)
                curveTo(16.261f, 4.028f, 16f, 3.4f, 16f, 2.667f)
                curveTo(16f, 1.933f, 16.261f, 1.306f, 16.783f, 0.783f)
                curveTo(17.306f, 0.261f, 17.933f, 0f, 18.667f, 0f)
                curveTo(19.4f, 0f, 20.028f, 0.261f, 20.55f, 0.783f)
                curveTo(21.072f, 1.306f, 21.333f, 1.933f, 21.333f, 2.667f)
                curveTo(21.333f, 3.4f, 21.072f, 4.028f, 20.55f, 4.55f)
                curveTo(20.028f, 5.072f, 19.4f, 5.333f, 18.667f, 5.333f)
                close()
            }
        }.build()

        return _moreIco!!
    }

@Suppress("ObjectPropertyName")
private var _moreIco: ImageVector? = null
