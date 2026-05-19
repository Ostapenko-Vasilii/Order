package ru.orderdorms.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform
import ru.orderdorms.TempScreen
import ru.orderdorms.core.domain.auth.AuthRepository
import ru.orderdorms.features.auth.presentation.AuthFlow

@Serializable
enum class RootScreen {
    AUTH,
    TEMP,
}

class RootController(
    private val authRepository: AuthRepository,
) {
    private val _screen = MutableStateFlow(
        if (authRepository.isAuthorized()) RootScreen.TEMP else RootScreen.AUTH
    )

    val screen = _screen.asStateFlow()

    fun onAuthorized() {
        _screen.value = RootScreen.TEMP
    }

    fun onLogout() {
        authRepository.clearAuth()
        _screen.value = RootScreen.AUTH
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
        RootController(
            authRepository = KoinPlatform.getKoin().get(),
        )
    }
