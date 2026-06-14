package ru.orderdorms.features.events.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: String,
    val title: String,
    val date: String,
    val time: String,
    val place: String,
    val imageUrl: String?,
    val descriptionMarkdown: String
)
