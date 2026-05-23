package ru.orderdorms.core.data.network

import kotlinx.coroutines.delay
import ru.orderdorms.core.data.network.model.FinalRegisterTokenResponseDto
import ru.orderdorms.core.data.network.model.InviteCodeResponseDto
import ru.orderdorms.core.data.network.model.ResendCodeResponseDto
import ru.orderdorms.core.data.network.model.ResetTokenResponseDto
import ru.orderdorms.core.data.network.model.TempTokenResponseDto
import ru.orderdorms.core.data.network.model.TokensResponseDto
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either

/**
 * Mock-реализация [AuthApiService] для тестирования без бэка.
 * Возвращает предопределённые данные и имитирует задержку сети.
 */
class MockAuthApiService : AuthApiService {
    companion object {
        // Mock токены
        private const val MOCK_INVITE_TOKEN = "mock_invite_token_12345"
        private const val MOCK_TEMP_TOKEN = "mock_temp_token_67890"
        private const val MOCK_FINAL_REGISTER_TOKEN = "mock_final_register_token_abc"
        private const val MOCK_RESET_TOKEN = "mock_reset_token_def"
        private const val MOCK_ACCESS_TOKEN = "mock_access_token_xyz789"
        private const val MOCK_REFRESH_TOKEN = "mock_refresh_token_xyz456"

        // Для имитации ошибок - используйте эти коды в соответствующих методах
        private const val INVALID_INVITE_CODE = "INVALID"
        private const val INVALID_EMAIL = "invalid@test.com"
        private const val ERROR_EMAIL = "error@test.com"
    }

    // Имитирует задержку сетевого запроса (200-500ms)
    private suspend fun simulateNetworkDelay() {
        delay((200..500).random().toLong())
    }

    override suspend fun checkInviteCode(code: String): Either<DomainError, InviteCodeResponseDto> {
        simulateNetworkDelay()

        return if (code == INVALID_INVITE_CODE) {
            Either.Left(
                DomainError(
                    code = "INVALID_CODE",
                    message = "Неверный код приглашения"
                )
            )
        } else {
            Either.Right(
                InviteCodeResponseDto(inviteToken = MOCK_INVITE_TOKEN)
            )
        }
    }

    override suspend fun sendRegistrationEmail(
        inviteToken: String,
        email: String
    ): Either<DomainError, TempTokenResponseDto> {
        simulateNetworkDelay()

        return if (email == INVALID_EMAIL) {
            Either.Left(
                DomainError(
                    code = "INVALID_EMAIL",
                    message = "Некорректный адрес электронной почты"
                )
            )
        } else if (email == ERROR_EMAIL) {
            Either.Left(
                DomainError(
                    code = "EMAIL_ALREADY_EXISTS",
                    message = "Пользователь с таким email уже существует"
                )
            )
        } else {
            Either.Right(
                TempTokenResponseDto(
                    tempToken = MOCK_TEMP_TOKEN,
                    nextRetryAfter = 60
                )
            )
        }
    }

    override suspend fun resendRegistrationCode(tempToken: String): Either<DomainError, ResendCodeResponseDto> {
        simulateNetworkDelay()

        return Either.Right(
            ResendCodeResponseDto(
                nextRetryAfter = 60,
                message = "Код отправлен на email"
            )
        )
    }

    override suspend fun verifyRegistrationCode(
        tempToken: String,
        code: String
    ): Either<DomainError, FinalRegisterTokenResponseDto> {
        simulateNetworkDelay()

        return if (code == "000000") {
            Either.Left(
                DomainError(
                    code = "INVALID_CODE",
                    message = "Неверный код верификации"
                )
            )
        } else {
            Either.Right(
                FinalRegisterTokenResponseDto(
                    finalRegisterToken = MOCK_FINAL_REGISTER_TOKEN
                )
            )
        }
    }

    override suspend fun setRegistrationPassword(
        finalRegisterToken: String,
        password: String
    ): Either<DomainError, TokensResponseDto> {
        simulateNetworkDelay()

        return if (password.length < 8) {
            Either.Left(
                DomainError(
                    code = "WEAK_PASSWORD",
                    message = "Пароль должен содержать минимум 8 символов"
                )
            )
        } else {
            Either.Right(
                TokensResponseDto(
                    accessToken = MOCK_ACCESS_TOKEN,
                    refreshToken = MOCK_REFRESH_TOKEN
                )
            )
        }
    }

    override suspend fun login(email: String, password: String): Either<DomainError, TokensResponseDto> {
        simulateNetworkDelay()

        return if (email == "test@example.com" && password == "password123") {
            Either.Right(
                TokensResponseDto(
                    accessToken = MOCK_ACCESS_TOKEN,
                    refreshToken = MOCK_REFRESH_TOKEN
                )
            )
        } else {
            Either.Left(
                DomainError(
                    code = "INVALID_CREDENTIALS",
                    message = "Неверные учетные данные"
                )
            )
        }
    }

    override suspend fun sendForgotPasswordEmail(email: String): Either<DomainError, TempTokenResponseDto> {
        simulateNetworkDelay()

        return if (email.isEmpty()) {
            Either.Left(
                DomainError(
                    code = "INVALID_EMAIL",
                    message = "Email не может быть пустым"
                )
            )
        } else {
            Either.Right(
                TempTokenResponseDto(
                    tempToken = MOCK_TEMP_TOKEN,
                    nextRetryAfter = 300
                )
            )
        }
    }

    override suspend fun verifyForgotPasswordCode(
        tempToken: String,
        code: String
    ): Either<DomainError, ResetTokenResponseDto> {
        simulateNetworkDelay()

        return if (code == "000000") {
            Either.Left(
                DomainError(
                    code = "INVALID_CODE",
                    message = "Неверный код"
                )
            )
        } else {
            Either.Right(
                ResetTokenResponseDto(
                    resetToken = MOCK_RESET_TOKEN
                )
            )
        }
    }

    override suspend fun setForgotPassword(
        resetToken: String,
        password: String
    ): Either<DomainError, Unit> {
        simulateNetworkDelay()

        return if (password.length < 8) {
            Either.Left(
                DomainError(
                    code = "WEAK_PASSWORD",
                    message = "Пароль должен содержать минимум 8 символов"
                )
            )
        } else {
            Either.Right(Unit)
        }
    }
}

