package ru.orderdorms.features.news.data.network.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import ru.orderdorms.features.news.domain.model.NewsItem
import ru.orderdorms.features.news.domain.model.NewsType

@Serializable
data class NewsDto(
    val id: String,
    val title: String,
    val content: String,
    val type: String,
    val timestamp: String,
    val details: String? = null
)

fun NewsDto.toDomain(): NewsItem = NewsItem(
    id = id,
    title = title,
    content = content,
    type = when (type) {
        "URGENT" -> NewsType.URGENT
        "PERSONAL" -> NewsType.PERSONAL
        "ANNOUNCEMENT" -> NewsType.ANNOUNCEMENT
        "EVENT" -> NewsType.EVENT
        else -> NewsType.OTHER
    },
    timestamp = Instant.parse(timestamp),
    details = details
)
