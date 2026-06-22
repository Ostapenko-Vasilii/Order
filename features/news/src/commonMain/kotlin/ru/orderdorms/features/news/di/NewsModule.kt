package ru.orderdorms.features.news.di

import org.koin.dsl.module
import ru.orderdorms.features.news.data.repository.KtorNewsApiDataSource
import ru.orderdorms.features.news.data.repository.NewsApiDataSource
import ru.orderdorms.features.news.data.repository.NewsMockDataSource
import ru.orderdorms.features.news.data.repository.NewsRepositoryImpl
import ru.orderdorms.features.news.domain.repository.NewsRepository
import ru.orderdorms.features.news.domain.usecase.GetNewsUseCase
import org.koin.core.qualifier.named

val newsModule = module {
    single { NewsMockDataSource(get()) }
    single<NewsApiDataSource> { KtorNewsApiDataSource(get(named("finalAuthorizedClient"))) }
    single<NewsRepository> {
        NewsRepositoryImpl(
            mockDataSource = get(),
            apiDataSource = get(),
            clock = get(),
            useMock = true
        )
    }
    factory { GetNewsUseCase(get()) }
}
