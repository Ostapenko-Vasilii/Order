package ru.orderdorms.di

import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import ru.orderdorms.core.data.di.networkModule
import ru.orderdorms.features.auth.di.authModule
import ru.orderdorms.features.home.di.homeModule
import ru.orderdorms.features.services.di.servicesModule

fun initKoinIfNeeded() {
    if (GlobalContext.getOrNull() != null) return

    startKoin {
        modules(
            networkModule,
            authModule,
            homeModule,
            servicesModule,
        )
    }
}
