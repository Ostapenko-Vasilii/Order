package ru.orderdorms.features.services.di

import org.koin.dsl.module
import ru.orderdorms.features.services.data.repository.ServicesRepositoryImpl
import ru.orderdorms.features.services.domain.repository.ServicesRepository
import ru.orderdorms.features.services.domain.usecase.GetQuickActionsUseCase
import ru.orderdorms.features.services.domain.usecase.GetServicesUseCase
import ru.orderdorms.features.services.domain.usecase.ToggleQuickActionUseCase

val servicesModule = module {
    single<ServicesRepository> { ServicesRepositoryImpl(get()) }
    factory { GetServicesUseCase(get()) }
    factory { GetQuickActionsUseCase(get()) }
    factory { ToggleQuickActionUseCase(get()) }
}
