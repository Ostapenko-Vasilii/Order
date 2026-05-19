package ru.orderdorms.core.data.auth

import ru.orderdorms.core.data.network.AuthApiService
import ru.orderdorms.core.domain.auth.AuthRepository
import ru.orderdorms.core.domain.auth.AuthTokens
import ru.orderdorms.core.domain.auth.RetryResponse
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either

class InMemoryAuthRepository(
    private val authApi: AuthApiService,
    private val tokenStorage: TokenStorage,
) : AuthRepository {

    override suspend fun checkInviteCode(code: String): Either<DomainError, Unit> {
        return when (val result = authApi.checkInviteCode(code)) {
            is Either.Left -> result
            is Either.Right -> {
                tokenStorage.setInviteToken(result.value.inviteToken)
                Either.Right(Unit)
            }
        }
    }

    override suspend fun sendRegistrationEmail(email: String): Either<DomainError, RetryResponse> {
        val inviteToken = tokenStorage.getInviteToken()
            ?: return Either.Left(DomainError(code = "MISSING_INVITE_TOKEN", message = "Сначала проверьте инвайт-код"))

        return when (val result = authApi.sendRegistrationEmail(inviteToken, email)) {
            is Either.Left -> result
            is Either.Right -> {
                tokenStorage.setTempToken(result.value.tempToken)
                Either.Right(RetryResponse(nextRetryAfter = result.value.nextRetryAfter))
            }
        }
    }

    override suspend fun resendRegistrationCode(): Either<DomainError, RetryResponse> {
        val tempToken = tokenStorage.getTempToken()
            ?: return Either.Left(DomainError(code = "MISSING_TEMP_TOKEN", message = "Сначала отправьте email"))

        return when (val result = authApi.resendRegistrationCode(tempToken)) {
            is Either.Left -> result
            is Either.Right -> {
                Either.Right(
                    RetryResponse(
                        nextRetryAfter = result.value.nextRetryAfter,
                        message = result.value.message,
                    )
                )
            }
        }
    }

    override suspend fun verifyRegistrationEmailCode(code: String): Either<DomainError, Unit> {
        val tempToken = tokenStorage.getTempToken()
            ?: return Either.Left(DomainError(code = "MISSING_TEMP_TOKEN", message = "Сначала отправьте email"))

        return when (val result = authApi.verifyRegistrationCode(tempToken, code)) {
            is Either.Left -> result
            is Either.Right -> {
                tokenStorage.setFinalRegisterToken(result.value.finalRegisterToken)
                Either.Right(Unit)
            }
        }
    }

    override suspend fun setRegistrationPassword(password: String): Either<DomainError, AuthTokens> {
        val finalRegisterToken = tokenStorage.getFinalRegisterToken()
            ?: return Either.Left(
                DomainError(
                    code = "MISSING_FINAL_TOKEN",
                    message = "Сначала подтвердите код из почты",
                )
            )

        return when (val result = authApi.setRegistrationPassword(finalRegisterToken, password)) {
            is Either.Left -> result
            is Either.Right -> {
                val tokens = AuthTokens(
                    accessToken = result.value.accessToken,
                    refreshToken = result.value.refreshToken,
                )
                persistAuth(tokens)
                tokenStorage.clearFlowTokens()
                Either.Right(tokens)
            }
        }
    }

    override suspend fun login(email: String, password: String): Either<DomainError, AuthTokens> {
        return when (val result = authApi.login(email, password)) {
            is Either.Left -> result
            is Either.Right -> {
                val tokens = AuthTokens(
                    accessToken = result.value.accessToken,
                    refreshToken = result.value.refreshToken,
                )
                persistAuth(tokens)
                Either.Right(tokens)
            }
        }
    }

    override suspend fun sendForgotPasswordEmail(email: String): Either<DomainError, RetryResponse> {
        return when (val result = authApi.sendForgotPasswordEmail(email)) {
            is Either.Left -> result
            is Either.Right -> {
                tokenStorage.setTempToken(result.value.tempToken)
                Either.Right(RetryResponse(nextRetryAfter = result.value.nextRetryAfter))
            }
        }
    }

    override suspend fun verifyForgotPasswordCode(code: String): Either<DomainError, Unit> {
        val tempToken = tokenStorage.getTempToken()
            ?: return Either.Left(DomainError(code = "MISSING_TEMP_TOKEN", message = "Сначала отправьте email"))

        return when (val result = authApi.verifyForgotPasswordCode(tempToken, code)) {
            is Either.Left -> result
            is Either.Right -> {
                tokenStorage.setResetToken(result.value.resetToken)
                Either.Right(Unit)
            }
        }
    }

    override suspend fun setForgotPassword(password: String): Either<DomainError, Unit> {
        val resetToken = tokenStorage.getResetToken()
            ?: return Either.Left(DomainError(code = "MISSING_RESET_TOKEN", message = "Сначала подтвердите код"))

        return when (val result = authApi.setForgotPassword(resetToken, password)) {
            is Either.Left -> result
            is Either.Right -> {
                tokenStorage.clearFlowTokens()
                Either.Right(Unit)
            }
        }
    }

    override fun isAuthorized(): Boolean = !tokenStorage.getAccessToken().isNullOrBlank()

    override fun clearAuth() {
        tokenStorage.clearAuthTokens()
        tokenStorage.clearFlowTokens()
    }

    private fun persistAuth(tokens: AuthTokens) {
        tokenStorage.setAccessToken(tokens.accessToken)
        tokenStorage.setRefreshToken(tokens.refreshToken)
    }
}