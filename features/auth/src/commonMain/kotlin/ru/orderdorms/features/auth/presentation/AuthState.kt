package ru.orderdorms.features.auth.presentation

import kotlinx.serialization.Serializable

@Serializable
enum class AuthScreen {
    Welcome,
    Login,
    Invitation,
    Email,
    Verify,
    Password,
}

@Serializable
enum class AuthFlowMode {
    Registration,
    ResetPassword,
}

data class AuthUiState(
    val currentScreen: AuthScreen = AuthScreen.Welcome,
    val flowMode: AuthFlowMode = AuthFlowMode.Registration,
    val isLoading: Boolean = false,
    val loginEmail: String = "",
    val loginPassword: String = "",
    val invitationCode: String = "",
    val email: String = "",
    val verifyCode: String = "",
    val password: String = "",
    val passwordRepeat: String = "",
    val verifyCodeSubmitted: Boolean = false,
    val inviteErrorFromBackend: String? = null,
    val loginErrorFromBackend: String? = null,
    val emailErrorFromBackend: String? = null,
    val verifyErrorFromBackend: String? = null,
    val passwordErrorFromBackend: String? = null,
    val nextRetryAfterSeconds: Int? = null,
)