package ru.orderdorms.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import ru.orderdorms.core.data.network.model.EmailRequestDto
import ru.orderdorms.core.data.network.model.FinalRegisterTokenResponseDto
import ru.orderdorms.core.data.network.model.InviteCodeRequestDto
import ru.orderdorms.core.data.network.model.InviteCodeResponseDto
import ru.orderdorms.core.data.network.model.LoginRequestDto
import ru.orderdorms.core.data.network.model.PasswordRequestDto
import ru.orderdorms.core.data.network.model.ResendCodeResponseDto
import ru.orderdorms.core.data.network.model.ResetTokenResponseDto
import ru.orderdorms.core.data.network.model.TempTokenResponseDto
import ru.orderdorms.core.data.network.model.TokensResponseDto
import ru.orderdorms.core.data.network.model.VerificationCodeRequestDto
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either

interface AuthApiService {
    suspend fun checkInviteCode(code: String): Either<DomainError, InviteCodeResponseDto>
    suspend fun sendRegistrationEmail(inviteToken: String, email: String): Either<DomainError, TempTokenResponseDto>
    suspend fun resendRegistrationCode(tempToken: String): Either<DomainError, ResendCodeResponseDto>
    suspend fun verifyRegistrationCode(tempToken: String, code: String): Either<DomainError, FinalRegisterTokenResponseDto>
    suspend fun setRegistrationPassword(finalRegisterToken: String, password: String): Either<DomainError, TokensResponseDto>

    suspend fun login(email: String, password: String): Either<DomainError, TokensResponseDto>

    suspend fun sendForgotPasswordEmail(email: String): Either<DomainError, TempTokenResponseDto>
    suspend fun verifyForgotPasswordCode(tempToken: String, code: String): Either<DomainError, ResetTokenResponseDto>
    suspend fun setForgotPassword(resetToken: String, password: String): Either<DomainError, Unit>
}

class KtorAuthApiService(
    private val httpClient: HttpClient,
    private val json: Json,
    private val baseUrl: String,
) : AuthApiService {

    override suspend fun checkInviteCode(code: String): Either<DomainError, InviteCodeResponseDto> =
        safeApiCall(json) {
            httpClient.post("$baseUrl/api/v1/auth/register/invite-code") {
                setBody(InviteCodeRequestDto(code))
            }
        }

    override suspend fun sendRegistrationEmail(
        inviteToken: String,
        email: String,
    ): Either<DomainError, TempTokenResponseDto> = safeApiCall(json) {
        httpClient.post("$baseUrl/api/v1/auth/register/mail") {
            header(HttpHeaders.Authorization, "Bearer $inviteToken")
            setBody(EmailRequestDto(email))
        }
    }

    override suspend fun resendRegistrationCode(tempToken: String): Either<DomainError, ResendCodeResponseDto> =
        safeApiCall(json) {
            httpClient.post("$baseUrl/api/v1/auth/send-email-code") {
                header(HttpHeaders.Authorization, "Bearer $tempToken")
            }
        }

    override suspend fun verifyRegistrationCode(
        tempToken: String,
        code: String,
    ): Either<DomainError, FinalRegisterTokenResponseDto> = safeApiCall(json) {
        httpClient.post("$baseUrl/api/v1/auth/register/email-code") {
            header(HttpHeaders.Authorization, "Bearer $tempToken")
            setBody(VerificationCodeRequestDto(code))
        }
    }

    override suspend fun setRegistrationPassword(
        finalRegisterToken: String,
        password: String,
    ): Either<DomainError, TokensResponseDto> = safeApiCall(json) {
        httpClient.post("$baseUrl/api/v1/auth/register/set-password") {
            header(HttpHeaders.Authorization, "Bearer $finalRegisterToken")
            setBody(PasswordRequestDto(password))
        }
    }

    override suspend fun login(email: String, password: String): Either<DomainError, TokensResponseDto> =
        safeApiCall(json) {
            httpClient.post("$baseUrl/api/v1/auth/login") {
                setBody(LoginRequestDto(email, password))
            }
        }

    override suspend fun sendForgotPasswordEmail(email: String): Either<DomainError, TempTokenResponseDto> =
        safeApiCall(json) {
            httpClient.post("$baseUrl/api/v1/auth/forgot-pass/mail") {
                setBody(EmailRequestDto(email))
            }
        }

    override suspend fun verifyForgotPasswordCode(
        tempToken: String,
        code: String,
    ): Either<DomainError, ResetTokenResponseDto> = safeApiCall(json) {
        httpClient.post("$baseUrl/api/v1/auth/forgot-pass/email-code") {
            header(HttpHeaders.Authorization, "Bearer $tempToken")
            setBody(VerificationCodeRequestDto(code))
        }
    }

    override suspend fun setForgotPassword(resetToken: String, password: String): Either<DomainError, Unit> =
        safeApiCall<Unit>(json) {
            httpClient.post("$baseUrl/api/v1/auth/forgot-pass/set-password") {
                header(HttpHeaders.Authorization, "Bearer $resetToken")
                setBody(PasswordRequestDto(password))
            }
        }
}
