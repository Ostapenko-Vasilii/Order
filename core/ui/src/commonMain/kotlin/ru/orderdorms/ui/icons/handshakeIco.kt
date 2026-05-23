package ru.orderdorms.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val handshakeIco: ImageVector
    get() {
        if (_handshakeIco != null) {
            return _handshakeIco!!
        }
        _handshakeIco = ImageVector.Builder(
            name = "handshakeIco",
            defaultWidth = 23.dp,
            defaultHeight = 20.dp,
            viewportWidth = 23f,
            viewportHeight = 20f
        ).apply {
            path(fill = SolidColor(Color(0xFF34383A))) {
                moveTo(10.885f, 18f)
                curveTo(10.952f, 18f, 11.018f, 17.983f, 11.085f, 17.95f)
                curveTo(11.152f, 17.917f, 11.202f, 17.883f, 11.235f, 17.85f)
                lineTo(19.435f, 9.65f)
                curveTo(19.635f, 9.45f, 19.781f, 9.225f, 19.872f, 8.975f)
                curveTo(19.964f, 8.725f, 20.01f, 8.475f, 20.01f, 8.225f)
                curveTo(20.01f, 7.958f, 19.964f, 7.704f, 19.872f, 7.463f)
                curveTo(19.781f, 7.221f, 19.635f, 7.008f, 19.435f, 6.825f)
                lineTo(15.185f, 2.575f)
                curveTo(15.002f, 2.375f, 14.789f, 2.229f, 14.547f, 2.138f)
                curveTo(14.306f, 2.046f, 14.052f, 2f, 13.785f, 2f)
                curveTo(13.535f, 2f, 13.285f, 2.046f, 13.035f, 2.138f)
                curveTo(12.785f, 2.229f, 12.56f, 2.375f, 12.36f, 2.575f)
                lineTo(12.085f, 2.85f)
                lineTo(13.935f, 4.725f)
                curveTo(14.185f, 4.958f, 14.368f, 5.225f, 14.485f, 5.525f)
                curveTo(14.602f, 5.825f, 14.66f, 6.142f, 14.66f, 6.475f)
                curveTo(14.66f, 7.175f, 14.422f, 7.762f, 13.948f, 8.238f)
                curveTo(13.472f, 8.712f, 12.885f, 8.95f, 12.185f, 8.95f)
                curveTo(11.852f, 8.95f, 11.531f, 8.892f, 11.222f, 8.775f)
                curveTo(10.914f, 8.658f, 10.643f, 8.483f, 10.41f, 8.25f)
                lineTo(8.535f, 6.4f)
                lineTo(4.16f, 10.775f)
                curveTo(4.11f, 10.825f, 4.073f, 10.879f, 4.048f, 10.938f)
                curveTo(4.023f, 10.996f, 4.01f, 11.058f, 4.01f, 11.125f)
                curveTo(4.01f, 11.258f, 4.06f, 11.379f, 4.16f, 11.488f)
                curveTo(4.26f, 11.596f, 4.377f, 11.65f, 4.51f, 11.65f)
                curveTo(4.577f, 11.65f, 4.643f, 11.633f, 4.71f, 11.6f)
                curveTo(4.777f, 11.567f, 4.827f, 11.533f, 4.86f, 11.5f)
                lineTo(8.26f, 8.1f)
                lineTo(9.66f, 9.5f)
                lineTo(6.285f, 12.9f)
                curveTo(6.235f, 12.95f, 6.198f, 13.004f, 6.173f, 13.063f)
                curveTo(6.148f, 13.121f, 6.135f, 13.183f, 6.135f, 13.25f)
                curveTo(6.135f, 13.383f, 6.185f, 13.5f, 6.285f, 13.6f)
                curveTo(6.385f, 13.7f, 6.502f, 13.75f, 6.635f, 13.75f)
                curveTo(6.702f, 13.75f, 6.768f, 13.733f, 6.835f, 13.7f)
                curveTo(6.902f, 13.667f, 6.952f, 13.633f, 6.985f, 13.6f)
                lineTo(10.385f, 10.225f)
                lineTo(11.785f, 11.625f)
                lineTo(8.41f, 15.025f)
                curveTo(8.36f, 15.058f, 8.323f, 15.108f, 8.297f, 15.175f)
                curveTo(8.273f, 15.242f, 8.26f, 15.308f, 8.26f, 15.375f)
                curveTo(8.26f, 15.508f, 8.31f, 15.625f, 8.41f, 15.725f)
                curveTo(8.51f, 15.825f, 8.627f, 15.875f, 8.76f, 15.875f)
                curveTo(8.827f, 15.875f, 8.889f, 15.863f, 8.948f, 15.837f)
                curveTo(9.006f, 15.813f, 9.06f, 15.775f, 9.11f, 15.725f)
                lineTo(12.51f, 12.35f)
                lineTo(13.91f, 13.75f)
                lineTo(10.51f, 17.15f)
                curveTo(10.46f, 17.2f, 10.422f, 17.254f, 10.398f, 17.313f)
                curveTo(10.373f, 17.371f, 10.36f, 17.433f, 10.36f, 17.5f)
                curveTo(10.36f, 17.633f, 10.414f, 17.75f, 10.523f, 17.85f)
                curveTo(10.631f, 17.95f, 10.752f, 18f, 10.885f, 18f)
                close()
                moveTo(10.86f, 20f)
                curveTo(10.243f, 20f, 9.698f, 19.796f, 9.222f, 19.388f)
                curveTo(8.748f, 18.979f, 8.468f, 18.467f, 8.385f, 17.85f)
                curveTo(7.818f, 17.767f, 7.343f, 17.533f, 6.96f, 17.15f)
                curveTo(6.577f, 16.767f, 6.343f, 16.292f, 6.26f, 15.725f)
                curveTo(5.693f, 15.642f, 5.222f, 15.404f, 4.847f, 15.012f)
                curveTo(4.472f, 14.621f, 4.243f, 14.15f, 4.16f, 13.6f)
                curveTo(3.527f, 13.517f, 3.01f, 13.242f, 2.61f, 12.775f)
                curveTo(2.21f, 12.308f, 2.01f, 11.758f, 2.01f, 11.125f)
                curveTo(2.01f, 10.792f, 2.072f, 10.471f, 2.197f, 10.163f)
                curveTo(2.322f, 9.854f, 2.502f, 9.583f, 2.735f, 9.35f)
                lineTo(8.535f, 3.575f)
                lineTo(11.81f, 6.85f)
                curveTo(11.843f, 6.9f, 11.893f, 6.938f, 11.96f, 6.963f)
                curveTo(12.027f, 6.988f, 12.093f, 7f, 12.16f, 7f)
                curveTo(12.31f, 7f, 12.435f, 6.954f, 12.535f, 6.863f)
                curveTo(12.635f, 6.771f, 12.685f, 6.65f, 12.685f, 6.5f)
                curveTo(12.685f, 6.433f, 12.672f, 6.367f, 12.648f, 6.3f)
                curveTo(12.623f, 6.233f, 12.585f, 6.183f, 12.535f, 6.15f)
                lineTo(8.96f, 2.575f)
                curveTo(8.777f, 2.375f, 8.564f, 2.229f, 8.323f, 2.138f)
                curveTo(8.081f, 2.046f, 7.827f, 2f, 7.56f, 2f)
                curveTo(7.31f, 2f, 7.06f, 2.046f, 6.81f, 2.138f)
                curveTo(6.56f, 2.229f, 6.335f, 2.375f, 6.135f, 2.575f)
                lineTo(2.61f, 6.125f)
                curveTo(2.46f, 6.275f, 2.335f, 6.45f, 2.235f, 6.65f)
                curveTo(2.135f, 6.85f, 2.068f, 7.05f, 2.035f, 7.25f)
                curveTo(2.002f, 7.45f, 2.002f, 7.654f, 2.035f, 7.863f)
                curveTo(2.068f, 8.071f, 2.135f, 8.267f, 2.235f, 8.45f)
                lineTo(0.785f, 9.9f)
                curveTo(0.502f, 9.517f, 0.293f, 9.096f, 0.16f, 8.637f)
                curveTo(0.027f, 8.179f, -0.023f, 7.717f, 0.01f, 7.25f)
                curveTo(0.043f, 6.783f, 0.16f, 6.329f, 0.36f, 5.887f)
                curveTo(0.56f, 5.446f, 0.835f, 5.05f, 1.185f, 4.7f)
                lineTo(4.71f, 1.175f)
                curveTo(5.11f, 0.792f, 5.556f, 0.5f, 6.048f, 0.3f)
                curveTo(6.539f, 0.1f, 7.043f, 0f, 7.56f, 0f)
                curveTo(8.077f, 0f, 8.581f, 0.1f, 9.073f, 0.3f)
                curveTo(9.564f, 0.5f, 10.002f, 0.792f, 10.385f, 1.175f)
                lineTo(10.66f, 1.45f)
                lineTo(10.935f, 1.175f)
                curveTo(11.335f, 0.792f, 11.781f, 0.5f, 12.273f, 0.3f)
                curveTo(12.764f, 0.1f, 13.268f, 0f, 13.785f, 0f)
                curveTo(14.302f, 0f, 14.806f, 0.1f, 15.297f, 0.3f)
                curveTo(15.789f, 0.5f, 16.227f, 0.792f, 16.61f, 1.175f)
                lineTo(20.835f, 5.4f)
                curveTo(21.218f, 5.783f, 21.51f, 6.225f, 21.71f, 6.725f)
                curveTo(21.91f, 7.225f, 22.01f, 7.733f, 22.01f, 8.25f)
                curveTo(22.01f, 8.767f, 21.91f, 9.271f, 21.71f, 9.762f)
                curveTo(21.51f, 10.254f, 21.218f, 10.692f, 20.835f, 11.075f)
                lineTo(12.635f, 19.25f)
                curveTo(12.402f, 19.483f, 12.131f, 19.667f, 11.823f, 19.8f)
                curveTo(11.514f, 19.933f, 11.193f, 20f, 10.86f, 20f)
                close()
            }
        }.build()

        return _handshakeIco!!
    }

@Suppress("ObjectPropertyName")
private var _handshakeIco: ImageVector? = null
