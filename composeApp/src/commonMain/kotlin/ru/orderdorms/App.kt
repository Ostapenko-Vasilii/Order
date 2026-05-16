package ru.orderdorms

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.orderdorms.navigation.RootContent
import ru.orderdorms.navigation.rememberRootController
import ru.orderdorms.ui.theme.OrderTheme

@Composable
@Preview
fun App() {
    OrderTheme {
        val rootController = rememberRootController()
        RootContent(rootController)
    }
}
