package ru.orderdorms.features.auth.presentation

import kotlinx.serialization.Serializable

@Serializable
enum class AuthScreen {
    Welcome,
    Login,
    Invitation,
    Email,
    Verify,
    Authorized,
    Password,
}

