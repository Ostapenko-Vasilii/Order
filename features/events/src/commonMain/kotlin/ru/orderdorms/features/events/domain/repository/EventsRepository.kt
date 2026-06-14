package ru.orderdorms.features.events.domain.repository

import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.features.events.domain.model.Event

interface EventsRepository {
    suspend fun getEvents(): Either<DomainError, List<Event>>
}
