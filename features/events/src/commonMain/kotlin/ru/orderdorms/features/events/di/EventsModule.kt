package ru.orderdorms.features.events.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.orderdorms.core.data.di.USE_MOCK_API
import ru.orderdorms.core.data.network.BackendUrlProvider
import ru.orderdorms.features.events.data.EventsRepositoryImpl
import ru.orderdorms.features.events.data.network.EventsApiService
import ru.orderdorms.features.events.data.network.MockEventsApiService
import ru.orderdorms.features.events.data.network.RemoteEventsApiService
import ru.orderdorms.features.events.domain.repository.EventsRepository
import ru.orderdorms.features.events.presentation.EventsViewModel

val eventsModule = module {
    single<EventsApiService> {
        if (USE_MOCK_API) {
            MockEventsApiService()
        } else {
            RemoteEventsApiService(
                httpClient = get(named("finalAuthorizedClient")),
                baseUrl = BackendUrlProvider.resolve()
            )
        }
    }

    single<EventsRepository> { EventsRepositoryImpl(get()) }
    
    factory { EventsViewModel(get()) }
}
