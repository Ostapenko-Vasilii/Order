package ru.orderdorms.features.news.domain.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
enum class NewsType(val title: String) {
    URGENT("Срочно"),
    PERSONAL("Лично вам"),
    ANNOUNCEMENT("Объявление"),
    EVENT("Событие"),
    OTHER("Прочее")
}

data class NewsItem(
    val id: String,
    val title: String,
    val content: String,
    val type: NewsType,
    val timestamp: Instant,
    val details: String? = null
)
