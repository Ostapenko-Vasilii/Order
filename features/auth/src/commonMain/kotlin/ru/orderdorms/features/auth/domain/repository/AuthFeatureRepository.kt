package ru.orderdorms.features.auth.domain.repository

import ru.orderdorms.core.domain.auth.AuthTokens
import ru.orderdorms.core.domain.auth.RetryResponse
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either

interface AuthFeatureRepository {
    suspend fun login(email: String, password: String): Either<DomainError, AuthTokens>

    suspend fun checkInviteCode(code: String): Either<DomainError, Unit>
    suspend fun sendRegistrationEmail(email: String): Either<DomainError, RetryResponse>
    suspend fun resendRegistrationCode(): Either<DomainError, RetryResponse>
    suspend fun verifyRegistrationCode(code: String): Either<DomainError, Unit>
    suspend fun setRegistrationPassword(password: String): Either<DomainError, AuthTokens>

    suspend fun sendForgotPasswordEmail(email: String): Either<DomainError, RetryResponse>
    suspend fun verifyForgotPasswordCode(code: String): Either<DomainError, Unit>
    suspend fun setForgotPassword(password: String): Either<DomainError, Unit>
}
