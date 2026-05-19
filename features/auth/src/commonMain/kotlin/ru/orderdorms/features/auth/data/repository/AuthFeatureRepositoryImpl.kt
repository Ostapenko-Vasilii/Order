package ru.orderdorms.features.auth.data.repository

import ru.orderdorms.core.domain.auth.AuthRepository
import ru.orderdorms.core.domain.auth.AuthTokens
import ru.orderdorms.core.domain.auth.RetryResponse
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.auth.domain.repository.AuthFeatureRepository

class AuthFeatureRepositoryImpl(
    private val repository: AuthRepository,
) : AuthFeatureRepository {
    override suspend fun login(email: String, password: String): Either<DomainError, AuthTokens> =
        repository.login(email, password)

    override suspend fun checkInviteCode(code: String): Either<DomainError, Unit> =
        repository.checkInviteCode(code)

    override suspend fun sendRegistrationEmail(email: String): Either<DomainError, RetryResponse> =
        repository.sendRegistrationEmail(email)

    override suspend fun resendRegistrationCode(): Either<DomainError, RetryResponse> =
        repository.resendRegistrationCode()

    override suspend fun verifyRegistrationCode(code: String): Either<DomainError, Unit> =
        repository.verifyRegistrationEmailCode(code)

    override suspend fun setRegistrationPassword(password: String): Either<DomainError, AuthTokens> =
        repository.setRegistrationPassword(password)

    override suspend fun sendForgotPasswordEmail(email: String): Either<DomainError, RetryResponse> =
        repository.sendForgotPasswordEmail(email)

    override suspend fun verifyForgotPasswordCode(code: String): Either<DomainError, Unit> =
        repository.verifyForgotPasswordCode(code)

    override suspend fun setForgotPassword(password: String): Either<DomainError, Unit> =
        repository.setForgotPassword(password)
}
