package ru.orderdorms.core.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InviteCodeRequestDto(
    val code: String,
)

@Serializable
data class EmailRequestDto(
    val email: String,
)

@Serializable
data class VerificationCodeRequestDto(
    val code: String,
)

@Serializable
data class PasswordRequestDto(
    val password: String,
)

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String,
)

@Serializable
data class InviteCodeResponseDto(
    @SerialName("invite_token")
    val inviteToken: String,
)

@Serializable
data class TempTokenResponseDto(
    @SerialName("temp_token")
    val tempToken: String,
    @SerialName("next_retry_after")
    val nextRetryAfter: Int,
)

@Serializable
data class ResendCodeResponseDto(
    @SerialName("next_retry_after")
    val nextRetryAfter: Int,
    val message: String? = null,
)

@Serializable
data class FinalRegisterTokenResponseDto(
    @SerialName("final_register_token")
    val finalRegisterToken: String,
)

@Serializable
data class ResetTokenResponseDto(
    @SerialName("reset_token")
    val resetToken: String,
)

@Serializable
data class TokensResponseDto(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String,
)
