package ru.orderdorms.features.services.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.orderdorms.features.services.data.network.CleaningApiService
import ru.orderdorms.features.services.data.network.MockCleaningApiService
import ru.orderdorms.features.services.data.repository.BookingApiDataSource
import ru.orderdorms.features.services.data.repository.BookingMockDataSource
import ru.orderdorms.features.services.data.repository.BookingRepositoryImpl
import ru.orderdorms.features.services.data.repository.CleaningRepositoryImpl
import ru.orderdorms.features.services.data.repository.ComplaintApiDataSource
import ru.orderdorms.features.services.data.repository.ComplaintMockDataSource
import ru.orderdorms.features.services.data.repository.ComplaintRepositoryImpl
import ru.orderdorms.features.services.data.repository.FaqApiDataSource
import ru.orderdorms.features.services.data.repository.FaqMockDataSource
import ru.orderdorms.features.services.data.repository.FaqRepositoryImpl
import ru.orderdorms.features.services.data.repository.KtorBookingApiDataSource
import ru.orderdorms.features.services.data.repository.KtorComplaintApiDataSource
import ru.orderdorms.features.services.data.repository.KtorFaqApiDataSource
import ru.orderdorms.features.services.data.repository.KtorRequestApiDataSource
import ru.orderdorms.features.services.data.repository.RequestApiDataSource
import ru.orderdorms.features.services.data.repository.RequestMockDataSource
import ru.orderdorms.features.services.data.repository.RequestRepositoryImpl
import ru.orderdorms.features.services.data.repository.ServicesRepositoryImpl
import ru.orderdorms.features.services.domain.repository.BookingRepository
import ru.orderdorms.features.services.domain.repository.CleaningRepository
import ru.orderdorms.features.services.domain.repository.ComplaintRepository
import ru.orderdorms.features.services.domain.repository.FaqRepository
import ru.orderdorms.features.services.domain.repository.RequestRepository
import ru.orderdorms.features.services.domain.repository.ServicesRepository
import ru.orderdorms.features.services.domain.usecase.BookSlotUseCase
import ru.orderdorms.features.services.domain.usecase.CancelBookingUseCase
import ru.orderdorms.features.services.domain.usecase.GetAvailableSlotsUseCase
import ru.orderdorms.features.services.domain.usecase.GetBookingResourcesUseCase
import ru.orderdorms.features.services.domain.usecase.GetCleaningInfoUseCase
import ru.orderdorms.features.services.domain.usecase.GetFaqCategoriesUseCase
import ru.orderdorms.features.services.domain.usecase.GetQuickActionsUseCase
import ru.orderdorms.features.services.domain.usecase.GetServicesUseCase
import ru.orderdorms.features.services.domain.usecase.GetUserBookingsUseCase
import ru.orderdorms.features.services.domain.usecase.GetUserComplaintsUseCase
import ru.orderdorms.features.services.domain.usecase.GetUserRequestsUseCase
import ru.orderdorms.features.services.domain.usecase.SubmitComplaintUseCase
import ru.orderdorms.features.services.domain.usecase.SubmitRequestUseCase
import ru.orderdorms.features.services.domain.usecase.ToggleQuickActionUseCase

val servicesModule = module {
    // Services
    single<ServicesRepository> { ServicesRepositoryImpl(get()) }
    factory { GetServicesUseCase(get()) }
    factory { GetQuickActionsUseCase(get()) }
    factory { ToggleQuickActionUseCase(get()) }

    // Cleaning Schedule
    // single<CleaningApiService> { RemoteCleaningApiService(get()) }
    single<CleaningApiService> { MockCleaningApiService() }
    single<CleaningRepository> { CleaningRepositoryImpl(get()) }
    factory { GetCleaningInfoUseCase(get()) }

    // FAQ (Knowledge Base)
    single { FaqMockDataSource() }
    single<FaqApiDataSource> { KtorFaqApiDataSource(get(named("finalAuthorizedClient"))) }
    
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

    // Maintenance Requests
    single { RequestMockDataSource() }
    single<RequestApiDataSource> { KtorRequestApiDataSource(get(named("finalAuthorizedClient"))) }
    single<RequestRepository> {
        RequestRepositoryImpl(
            mockDataSource = get(),
            apiDataSource = get(),
            clock = get(),
            useMock = true
        )
    }
    factory { GetUserRequestsUseCase(get()) }
    factory { SubmitRequestUseCase(get()) }

    // Complaints
    single { ComplaintMockDataSource() }
    single<ComplaintApiDataSource> { KtorComplaintApiDataSource(get(named("finalAuthorizedClient"))) }
    single<ComplaintRepository> {
        ComplaintRepositoryImpl(
            mockDataSource = get(),
            apiDataSource = get(),
            clock = get(),
            useMock = true
        )
    }
    factory { GetUserComplaintsUseCase(get()) }
    factory { SubmitComplaintUseCase(get()) }

    // Booking
    single { BookingMockDataSource() }
    single<BookingApiDataSource> { KtorBookingApiDataSource(get(named("finalAuthorizedClient"))) }
    single<BookingRepository> {
        BookingRepositoryImpl(
            mockDataSource = get(),
            apiDataSource = get(),
            clock = get(),
            useMock = true
        )
    }
    factory { GetBookingResourcesUseCase(get()) }
    factory { GetAvailableSlotsUseCase(get()) }
    factory { GetUserBookingsUseCase(get()) }
    factory { BookSlotUseCase(get()) }
    factory { CancelBookingUseCase(get()) }
}
