package ru.orderdorms.features.home.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import ru.orderdorms.core.domain.model.Service
import ru.orderdorms.features.events.domain.model.Event as EventFromModule

data class HomeDashboard(
    val userName: String,
    val events: List<EventFromModule>,
    val importantNews: List<News>,
    val quickActions: List<Service>,
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
