package ru.orderdorms.features.home.domain.model
import androidx.compose.ui.graphics.vector.ImageVector
data class Service(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val url: String? = null,
)
