package ru.orderdorms.features.services.di

import org.koin.dsl.module
import ru.orderdorms.features.services.data.repository.FaqApiDataSource
import ru.orderdorms.features.services.data.repository.FaqMockDataSource
import ru.orderdorms.features.services.data.repository.FaqRepositoryImpl
import ru.orderdorms.features.services.data.repository.KtorFaqApiDataSource
import ru.orderdorms.features.services.data.repository.ServicesRepositoryImpl
import ru.orderdorms.features.services.domain.repository.FaqRepository
import ru.orderdorms.features.services.domain.repository.ServicesRepository
import ru.orderdorms.features.services.domain.usecase.GetFaqCategoriesUseCase
import ru.orderdorms.features.services.domain.usecase.GetQuickActionsUseCase
import ru.orderdorms.features.services.domain.usecase.GetServicesUseCase
import ru.orderdorms.features.services.domain.usecase.ToggleQuickActionUseCase

val servicesModule = module {
    // Services
    single<ServicesRepository> { ServicesRepositoryImpl(get()) }
    factory { GetServicesUseCase(get()) }
    factory { GetQuickActionsUseCase(get()) }
    factory { ToggleQuickActionUseCase(get()) }

    // FAQ (Knowledge Base)
    single { FaqMockDataSource() }
    single<FaqApiDataSource> { KtorFaqApiDataSource() }
    
    // Choose between mock and real data here or via a flag
    single<FaqRepository> { 
        FaqRepositoryImpl(
            mockDataSource = get(),
            apiDataSource = get(),
            clock = get(),
            useMock = true // Set to false to use real API
        ) 
    }
    
    factory { GetFaqCategoriesUseCase(get()) }
}
