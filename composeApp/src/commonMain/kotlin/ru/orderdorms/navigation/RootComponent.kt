package ru.orderdorms.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable
import ru.orderdorms.TempScreen
import ru.orderdorms.features.auth.presentation.AuthFlow

@Serializable
enum class RootScreen {
    AUTH,
    TEMP,
}

class RootController(
) {
    private var isAuthorized: Boolean = false

    private val _screen = MutableStateFlow(if (isAuthorized) RootScreen.TEMP else RootScreen.AUTH)

    val screen = _screen.asStateFlow()

    fun onAuthorized() {
        isAuthorized = true
        _screen.value = if (isAuthorized) RootScreen.TEMP else RootScreen.AUTH
    }

    fun onLogout() {
        isAuthorized = false
        _screen.value = if (isAuthorized) RootScreen.TEMP else RootScreen.AUTH
    }
}

@Composable
fun RootContent(rootController: RootController) {
    val screen by rootController.screen.collectAsState()

    when (screen) {
        RootScreen.AUTH -> AuthFlow(onAuthorized = rootController::onAuthorized)
        RootScreen.TEMP -> TempScreen(onLogout = rootController::onLogout)
    }
}

@Composable
fun rememberRootController(): RootController =
    remember {
        RootController()
    }



