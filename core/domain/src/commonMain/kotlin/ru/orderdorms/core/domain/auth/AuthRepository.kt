package ru.orderdorms.core.domain.auth

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
)

data class RetryResponse(
    val nextRetryAfter: Int,
    val message: String? = null,
)

interface AuthRepository {
    suspend fun checkInviteCode(code: String): Either<DomainError, Unit>
    suspend fun sendRegistrationEmail(email: String): Either<DomainError, RetryResponse>
    suspend fun resendRegistrationCode(): Either<DomainError, RetryResponse>
    suspend fun verifyRegistrationEmailCode(code: String): Either<DomainError, Unit>
    suspend fun setRegistrationPassword(password: String): Either<DomainError, AuthTokens>

    suspend fun login(email: String, password: String): Either<DomainError, AuthTokens>

    suspend fun sendForgotPasswordEmail(email: String): Either<DomainError, RetryResponse>
    suspend fun verifyForgotPasswordCode(code: String): Either<DomainError, Unit>
    suspend fun setForgotPassword(password: String): Either<DomainError, Unit>

    fun isAuthorized(): Boolean
    fun clearAuth()
}