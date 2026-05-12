package ru.orderdorms.features.auth.presentation

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthScreen {
    @Serializable
    data object Welcome : AuthScreen()
    @Serializable
    data object Login : AuthScreen()
    @Serializable
    data object Invitation : AuthScreen()
    @Serializable
    data object Email : AuthScreen()
    @Serializable
    data object Verify : AuthScreen()
    @Serializable
    data object Password : AuthScreen()
}

@Serializable
data class AuthData(
    val currentScreen: AuthScreen = AuthScreen.Welcome,
    val invitationCode: String = "",
    val email: String = "",
    val isRegistration: Boolean = false
)
