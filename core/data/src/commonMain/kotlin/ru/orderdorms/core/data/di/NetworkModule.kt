package ru.orderdorms.core.data.di

import com.russhwolf.settings.Settings
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlinx.serialization.json.Json
import ru.orderdorms.core.data.auth.InMemoryAuthRepository
import ru.orderdorms.core.data.auth.SettingsTokenStorage
import ru.orderdorms.core.data.auth.TokenStorage
import ru.orderdorms.core.data.network.AuthApiService
import ru.orderdorms.core.data.network.BackendUrlProvider
import ru.orderdorms.core.data.network.HttpClientFactory
import ru.orderdorms.core.data.network.KtorAuthApiService
import ru.orderdorms.core.domain.auth.AuthRepository

val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            explicitNulls = false
        }
    }

    single { Settings() }
    single<TokenStorage> { SettingsTokenStorage(get()) }

    single { HttpClientFactory(get()) }
    single(named("anonymousClient")) { get<HttpClientFactory>().createAnonymous() }
    single(named("finalAuthorizedClient")) { get<HttpClientFactory>().createFinalAuthorized { get<TokenStorage>().getAccessToken() } }

    single<AuthApiService> {
        KtorAuthApiService(
            httpClient = get(named("anonymousClient")),
            json = get(),
            baseUrl = BackendUrlProvider.resolve(),
        )
    }

    single<AuthRepository> { InMemoryAuthRepository(get(), get()) }
}


