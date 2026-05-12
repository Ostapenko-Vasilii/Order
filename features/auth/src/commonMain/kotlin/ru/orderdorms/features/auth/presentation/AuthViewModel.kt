package ru.orderdorms.features.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class AuthViewModel : ViewModel() {
    private val settings: Settings = Settings()
    private val json = Json { ignoreUnknownKeys = true }

    private val _state = MutableStateFlow(loadState())
    val state = _state.asStateFlow()

    private fun loadState(): AuthData {
        val saved: String? = settings.getStringOrNull("auth_data")
        return if (saved != null) {
            try {
                json.decodeFromString<AuthData>(saved)
            } catch (e: Exception) {
                AuthData()
            }
        } else {
            AuthData()
        }
    }

    private fun saveState() {
        viewModelScope.launch {
            val serialized = json.encodeToString(AuthData.serializer(), _state.value)
            settings["auth_data"] = serialized
        }
    }

    fun setRoute(route: String) {
        _state.update { it.copy(currentScreen = routeToScreen(route)) }
        saveState()
    }

    private fun routeToScreen(route: String): AuthScreen {
        return when (route) {
            "welcome" -> AuthScreen.Welcome
            "login" -> AuthScreen.Login
            "invitation" -> AuthScreen.Invitation
            "email" -> AuthScreen.Email
            "verify" -> AuthScreen.Verify
            "password" -> AuthScreen.Password
            else -> AuthScreen.Welcome
        }
    }

    private fun screenToRoute(screen: AuthScreen): String {
        return when (screen) {
            AuthScreen.Welcome -> "welcome"
            AuthScreen.Login -> "login"
            AuthScreen.Invitation -> "invitation"
            AuthScreen.Email -> "email"
            AuthScreen.Verify -> "verify"
            AuthScreen.Password -> "password"
        }
    }

    fun getInitialRoute(): String = screenToRoute(_state.value.currentScreen)

    fun updateInvitationCode(code: String) {
        _state.update { it.copy(invitationCode = code) }
        saveState()
    }

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email) }
        saveState()
    }

    fun setRegistration(isRegistration: Boolean) {
        _state.update { it.copy(isRegistration = isRegistration) }
        saveState()
    }

    fun clearState() {
        _state.value = AuthData()
        settings.remove("auth_data")
    }
}
