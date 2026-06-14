package ru.orderdorms.features.events.data

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.events.data.network.EventsApiService
import ru.orderdorms.features.events.domain.model.Event
import ru.orderdorms.features.events.domain.repository.EventsRepository

class EventsRepositoryImpl(
    private val apiService: EventsApiService
) : EventsRepository {
    override suspend fun getEvents(): Either<DomainError, List<Event>> {
        return try {
            Either.Right(apiService.fetchEvents())
        } catch (e: Exception) {
            Either.Left(DomainError(code = "UNKNOWN", message = e.message ?: "Unknown error"))
        }
    }
}
