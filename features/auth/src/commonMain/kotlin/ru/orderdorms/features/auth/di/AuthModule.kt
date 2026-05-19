package ru.orderdorms.features.auth.di

import org.koin.dsl.module
import ru.orderdorms.features.auth.data.repository.AuthFeatureRepositoryImpl
import ru.orderdorms.features.auth.domain.repository.AuthFeatureRepository
import ru.orderdorms.features.auth.domain.usecase.CheckInviteCodeUseCase
import ru.orderdorms.features.auth.domain.usecase.CheckInviteCodeUseCaseImpl
import ru.orderdorms.features.auth.domain.usecase.LoginUseCase
import ru.orderdorms.features.auth.domain.usecase.LoginUseCaseImpl
import ru.orderdorms.features.auth.domain.usecase.ResendRegistrationCodeUseCase
import ru.orderdorms.features.auth.domain.usecase.ResendRegistrationCodeUseCaseImpl
import ru.orderdorms.features.auth.domain.usecase.SendForgotPasswordEmailUseCase
import ru.orderdorms.features.auth.domain.usecase.SendForgotPasswordEmailUseCaseImpl
import ru.orderdorms.features.auth.domain.usecase.SendRegistrationEmailUseCase
import ru.orderdorms.features.auth.domain.usecase.SendRegistrationEmailUseCaseImpl
import ru.orderdorms.features.auth.domain.usecase.SetForgotPasswordUseCase
import ru.orderdorms.features.auth.domain.usecase.SetForgotPasswordUseCaseImpl
import ru.orderdorms.features.auth.domain.usecase.SetRegistrationPasswordUseCase
import ru.orderdorms.features.auth.domain.usecase.SetRegistrationPasswordUseCaseImpl
import ru.orderdorms.features.auth.domain.usecase.VerifyForgotPasswordCodeUseCase
import ru.orderdorms.features.auth.domain.usecase.VerifyForgotPasswordCodeUseCaseImpl
import ru.orderdorms.features.auth.domain.usecase.VerifyRegistrationCodeUseCase
import ru.orderdorms.features.auth.domain.usecase.VerifyRegistrationCodeUseCaseImpl

val authModule = module {
    single<AuthFeatureRepository> { AuthFeatureRepositoryImpl(get()) }

    single<LoginUseCase> { LoginUseCaseImpl(get()) }
    single<CheckInviteCodeUseCase> { CheckInviteCodeUseCaseImpl(get()) }
    single<SendRegistrationEmailUseCase> { SendRegistrationEmailUseCaseImpl(get()) }
    single<ResendRegistrationCodeUseCase> { ResendRegistrationCodeUseCaseImpl(get()) }
    single<VerifyRegistrationCodeUseCase> { VerifyRegistrationCodeUseCaseImpl(get()) }
    single<SetRegistrationPasswordUseCase> { SetRegistrationPasswordUseCaseImpl(get()) }

    single<SendForgotPasswordEmailUseCase> { SendForgotPasswordEmailUseCaseImpl(get()) }
    single<VerifyForgotPasswordCodeUseCase> { VerifyForgotPasswordCodeUseCaseImpl(get()) }
    single<SetForgotPasswordUseCase> { SetForgotPasswordUseCaseImpl(get()) }
}
