package ru.orderdorms.features.events.data.network

import ru.orderdorms.features.events.domain.model.Event

interface EventsApiService {
    suspend fun fetchEvents(): List<Event>
}
