package ru.orderdorms.features.events.presentation

import ru.orderdorms.features.events.domain.model.Event

data class EventsUiState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val filteredEvents: List<Event> = emptyList(),
    val searchQuery: String = "",
    val selectedEvent: Event? = null,
    val errorMessage: String? = null
)
