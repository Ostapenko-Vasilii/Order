package ru.orderdorms.features.services.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Service(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val isQuickAction: Boolean = false,
)
