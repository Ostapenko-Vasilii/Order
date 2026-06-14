package ru.orderdorms.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import order.core.ui.generated.resources.Res
import order.core.ui.generated.resources.back_button
import order.core.ui.generated.resources.tab_events
import order.core.ui.generated.resources.tab_home
import order.core.ui.generated.resources.tab_notifications
import order.core.ui.generated.resources.tab_services
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform
import ru.orderdorms.core.domain.auth.AuthRepository
import ru.orderdorms.features.auth.presentation.AuthFlow
import ru.orderdorms.features.home.presentation.HomeFlow
import ru.orderdorms.features.events.presentation.EventsScreen
import androidx.compose.ui.platform.LocalUriHandler
import ru.orderdorms.core.domain.model.Service
import ru.orderdorms.features.services.presentation.faq.FaqFlow
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

private enum class MainTab(val titleRes: StringResource) {
    HOME(Res.string.tab_home),
    EVENTS(Res.string.tab_events),
    NOTIFICATIONS(Res.string.tab_notifications),
    SERVICES(Res.string.tab_services),
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
    val openedService = remember { mutableStateOf<Service?>(null) }

    when (screen) {
        RootScreen.AUTH -> AuthFlow(onAuthorized = rootController::onAuthorized)
        RootScreen.HOME -> {
            val uriHandler = LocalUriHandler.current
            if (openedService.value != null) {
                val service = openedService.value!!
                when (service.id) {
                    "1" -> {
                        LaunchedEffect(service.id) {
                            uriHandler.openUri("https://pay.urfu.ru/direct")
                            openedService.value = null
                        }
                    }
                    "7" -> {
                        FaqFlow(onBack = { openedService.value = null })
                    }
                    else -> {
                        ServiceDetailScreen(
                            service = service,
                            onBack = { openedService.value = null }
                        )
                    }
                }
            } else {
                Scaffold(
                    containerColor = OrderTheme.colors.bgPlaceholderColor,
                    bottomBar = {
                        NavigationBar(containerColor = OrderTheme.colors.onBackground) {
                            MainTab.entries.forEach { tab ->
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
                                            contentDescription = stringResource(tab.titleRes),
                                            tint = if (tab == tabState.value) OrderTheme.colors.activeColor else OrderTheme.colors.primaryTextColor
                                        )
                                    },
                                    label = { Text(stringResource(tab.titleRes), color = OrderTheme.colors.primaryTextColor) }
                                )
                            }
                        }
                    }
                ) { inner ->
                        Box(modifier = Modifier.padding(inner).fillMaxSize()) {
                        when (tabState.value) {
                            MainTab.HOME -> HomeFlow(onLogout = rootController::onLogout, onOpenServices = { tabState.value = MainTab.SERVICES })
                            MainTab.EVENTS -> EventsScreen()
                            MainTab.NOTIFICATIONS -> PlaceholderScreen(Res.string.tab_notifications)
                            MainTab.SERVICES -> ServicesFlow(onOpenService = { openedService.value = it })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaceholderScreen(resource: StringResource) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(resource), color = OrderTheme.colors.primaryTextColor)
    }
}

@Composable
private fun ServiceDetailScreen(
    service: Service,
    onBack: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = service.title, color = OrderTheme.colors.primaryTextColor)
            Button(onClick = onBack) {
                Text(text = stringResource(Res.string.back_button))
            }
        }
    }
}

@Composable
fun rememberRootController(): RootController =
    remember {
        RootController(
            authRepository = KoinPlatform.getKoin().get(),
        )
    }
