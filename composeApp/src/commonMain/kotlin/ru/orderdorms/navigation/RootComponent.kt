package ru.orderdorms.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform
import ru.orderdorms.core.domain.auth.AuthRepository
import ru.orderdorms.features.auth.presentation.AuthFlow
import ru.orderdorms.features.home.presentation.HomeFlow
import ru.orderdorms.features.services.presentation.ServicesFlow
import ru.orderdorms.ui.icons.calendarIco
import ru.orderdorms.ui.icons.dormIco
import ru.orderdorms.ui.icons.priorityInfoIco
import ru.orderdorms.ui.icons.repairIco
import ru.orderdorms.ui.theme.OrderTheme

@Serializable
enum class RootScreen {
    AUTH,
    HOME,
}

private enum class MainTab(val title: String) {
    HOME("Общага"),
    EVENTS("События"),
    NOTIFICATIONS("Оповещения"),
    SERVICES("Сервисы"),
}

class RootController(
    private val authRepository: AuthRepository,
) {
    private val _screen = MutableStateFlow(
        if (authRepository.isAuthorized()) RootScreen.HOME else RootScreen.AUTH
    )

    val screen = _screen.asStateFlow()

    fun onAuthorized() {
        _screen.value = RootScreen.HOME
    }

    fun onLogout() {
        authRepository.clearAuth()
        _screen.value = RootScreen.AUTH
    }
}

@Composable
fun RootContent(rootController: RootController) {
    val screen by rootController.screen.collectAsState()

    // remember selected tab only while in HOME
    val tabState = remember { mutableStateOf(MainTab.HOME) }

    when (screen) {
        RootScreen.AUTH -> AuthFlow(onAuthorized = rootController::onAuthorized)
        RootScreen.HOME -> {
            Scaffold(
                containerColor = OrderTheme.colors.bgPlaceholderColor,
                bottomBar = {
                    NavigationBar(containerColor = OrderTheme.colors.onBackground) {
                        MainTab.values().forEach { tab ->
                            NavigationBarItem(
                                selected = tab == tabState.value,
                                onClick = { tabState.value = tab },
                                icon = {
                                    val ico = when (tab) {
                                        MainTab.HOME -> dormIco
                                        MainTab.EVENTS -> calendarIco
                                        MainTab.NOTIFICATIONS -> priorityInfoIco
                                        MainTab.SERVICES -> repairIco
                                    }
                                    Icon(
                                        imageVector = ico,
                                        contentDescription = tab.title,
                                        tint = if (tab == tabState.value) OrderTheme.colors.activeColor else OrderTheme.colors.primaryTextColor
                                    )
                                },
                                label = { Text(tab.title, color = OrderTheme.colors.primaryTextColor) }
                            )
                        }
                    }
                }
            ) { inner ->
                    Box(modifier = Modifier.padding(inner).fillMaxSize()) {
                    when (tabState.value) {
                        MainTab.HOME -> HomeFlow(onLogout = rootController::onLogout, onOpenServices = { tabState.value = MainTab.SERVICES })
                        MainTab.EVENTS -> PlaceholderScreen("События")
                        MainTab.NOTIFICATIONS -> PlaceholderScreen("Оповещения")
                        MainTab.SERVICES -> ServicesFlow()
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaceholderScreen(title: String) {
    androidx.compose.foundation.layout.Box(
        modifier = androidx.compose.ui.Modifier.fillMaxSize()
    ) {
        Text(text = title, color = OrderTheme.colors.primaryTextColor)
    }
}

@Composable
fun rememberRootController(): RootController =
    remember {
        RootController(
            authRepository = KoinPlatform.getKoin().get(),
        )
    }
