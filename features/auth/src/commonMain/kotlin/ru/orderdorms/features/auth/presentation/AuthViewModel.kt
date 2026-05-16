package ru.orderdorms.features.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

class AuthNavigator(
    initialScreen: AuthScreen = AuthScreen.Welcome,
) {
    private val stack = mutableStateListOf(initialScreen)

    val currentScreen: AuthScreen
        get() = stack.last()

    val canGoBack: Boolean
        get() = stack.size > 1

    fun navigate(screen: AuthScreen) {
        if (stack.last() != screen) {
            stack.add(screen)
        }
    }

    fun back() {
        if (canGoBack) {
            stack.removeAt(stack.lastIndex)
        }
    }

    fun reset() {
        stack.clear()
        stack.add(AuthScreen.Welcome)
    }
}

@Composable
fun rememberAuthNavigator(): AuthNavigator = remember {
    AuthNavigator()
}
