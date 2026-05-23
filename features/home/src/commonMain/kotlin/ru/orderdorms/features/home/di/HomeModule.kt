package ru.orderdorms.features.home.di

import org.koin.dsl.module
import ru.orderdorms.features.home.data.repository.HomeRepositoryImpl
import ru.orderdorms.features.home.domain.repository.HomeRepository
import ru.orderdorms.features.home.domain.usecase.HomeUseCases
import ru.orderdorms.features.home.domain.usecase.HomeUseCasesImpl

import ru.orderdorms.features.home.data.repository.BuiltInQuickActionsRepository
import ru.orderdorms.features.home.domain.repository.QuickActionsRepository

val homeModule = module {
    single<HomeRepository> { HomeRepositoryImpl() }
    single<HomeUseCases> { HomeUseCasesImpl(get(), get()) }
    single<QuickActionsRepository> { BuiltInQuickActionsRepository(get()) }
}
