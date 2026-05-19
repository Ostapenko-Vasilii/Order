package ru.orderdorms.features.auth.domain.usecase

import ru.orderdorms.core.domain.auth.AuthTokens
import ru.orderdorms.core.domain.auth.RetryResponse
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.auth.domain.repository.AuthFeatureRepository

class LoginUseCaseImpl(
    private val repository: AuthFeatureRepository,
) : LoginUseCase {
    override suspend fun invoke(email: String, password: String): Either<DomainError, AuthTokens> =
        repository.login(email, password)
}

class CheckInviteCodeUseCaseImpl(
    private val repository: AuthFeatureRepository,
) : CheckInviteCodeUseCase {
    override suspend fun invoke(code: String): Either<DomainError, Unit> =
        repository.checkInviteCode(code)
}

class SendRegistrationEmailUseCaseImpl(
    private val repository: AuthFeatureRepository,
) : SendRegistrationEmailUseCase {
    override suspend fun invoke(email: String): Either<DomainError, RetryResponse> =
        repository.sendRegistrationEmail(email)
}

class ResendRegistrationCodeUseCaseImpl(
    private val repository: AuthFeatureRepository,
) : ResendRegistrationCodeUseCase {
    override suspend fun invoke(): Either<DomainError, RetryResponse> =
        repository.resendRegistrationCode()
}

class VerifyRegistrationCodeUseCaseImpl(
    private val repository: AuthFeatureRepository,
) : VerifyRegistrationCodeUseCase {
    override suspend fun invoke(code: String): Either<DomainError, Unit> =
        repository.verifyRegistrationCode(code)
}

class SetRegistrationPasswordUseCaseImpl(
    private val repository: AuthFeatureRepository,
) : SetRegistrationPasswordUseCase {
    override suspend fun invoke(password: String): Either<DomainError, AuthTokens> =
        repository.setRegistrationPassword(password)
}

class SendForgotPasswordEmailUseCaseImpl(
    private val repository: AuthFeatureRepository,
) : SendForgotPasswordEmailUseCase {
    override suspend fun invoke(email: String): Either<DomainError, RetryResponse> =
        repository.sendForgotPasswordEmail(email)
}

class VerifyForgotPasswordCodeUseCaseImpl(
    private val repository: AuthFeatureRepository,
) : VerifyForgotPasswordCodeUseCase {
    override suspend fun invoke(code: String): Either<DomainError, Unit> =
        repository.verifyForgotPasswordCode(code)
}

class SetForgotPasswordUseCaseImpl(
    private val repository: AuthFeatureRepository,
) : SetForgotPasswordUseCase {
    override suspend fun invoke(password: String): Either<DomainError, Unit> =
        repository.setForgotPassword(password)
}
