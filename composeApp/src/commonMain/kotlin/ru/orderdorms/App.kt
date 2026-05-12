package ru.orderdorms

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.orderdorms.features.auth.presentation.AuthFlow
import ru.orderdorms.ui.theme.OrderTheme

@Composable
@Preview
fun App() {
    OrderTheme {
        AuthFlow()
    }
}
