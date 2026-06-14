package ru.orderdorms.features.events.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.orderdorms.features.events.domain.model.Event

class RemoteEventsApiService(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : EventsApiService {
    override suspend fun fetchEvents(): List<Event> {
        return httpClient.get("$baseUrl/events").body()
    }
}
