package ru.orderdorms.features.auth.domain.usecase

import ru.orderdorms.core.domain.auth.AuthTokens
import ru.orderdorms.core.domain.auth.RetryResponse
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either

interface LoginUseCase {
    suspend operator fun invoke(email: String, password: String): Either<DomainError, AuthTokens>
}

interface CheckInviteCodeUseCase {
    suspend operator fun invoke(code: String): Either<DomainError, Unit>
}

interface SendRegistrationEmailUseCase {
    suspend operator fun invoke(email: String): Either<DomainError, RetryResponse>
}

interface ResendRegistrationCodeUseCase {
    suspend operator fun invoke(): Either<DomainError, RetryResponse>
}

interface VerifyRegistrationCodeUseCase {
    suspend operator fun invoke(code: String): Either<DomainError, Unit>
}

interface SetRegistrationPasswordUseCase {
    suspend operator fun invoke(password: String): Either<DomainError, AuthTokens>
}

interface SendForgotPasswordEmailUseCase {
    suspend operator fun invoke(email: String): Either<DomainError, RetryResponse>
}

interface VerifyForgotPasswordCodeUseCase {
    suspend operator fun invoke(code: String): Either<DomainError, Unit>
}

interface SetForgotPasswordUseCase {
    suspend operator fun invoke(password: String): Either<DomainError, Unit>
}
