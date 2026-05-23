package ru.orderdorms.navigation

import androidx.compose.runtime.Composable
import ru.orderdorms.features.home.presentation.HomeFlow

// Навигация приложения теперь вынесена в RootComponent
@Composable
fun MainApp(onLogout: () -> Unit, onOpenServices: () -> Unit = {}) {
    // старая точка входа MainApp оставлена для совместимости — теперь делегирует в HomeFlow
    HomeFlow(onLogout = onLogout, onOpenServices = onOpenServices)
}



