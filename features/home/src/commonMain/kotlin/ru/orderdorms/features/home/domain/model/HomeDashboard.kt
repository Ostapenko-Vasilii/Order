package ru.orderdorms.features.home.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class HomeDashboard(
    val userName: String,
    val events: List<Event>,
    val importantNews: List<News>,
    val quickActions: List<Service>,
)

data class Event(
    val id: String,
    val title: String,
    val date: String,
    val imageUrl: String?,
)

data class News(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
)

data class Action(
    val id: String,
    val title: String,
    val icon: ImageVector,
)
