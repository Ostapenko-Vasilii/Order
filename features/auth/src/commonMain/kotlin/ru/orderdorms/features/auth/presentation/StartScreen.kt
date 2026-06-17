package ru.orderdorms.features.auth.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import ru.orderdorms.ui.components.OrderButton

@Composable
fun StartScreen() {
    Column {
        Column {
            OrderButton(
                text = "Войти",
                onClick = { /* TODO */ }
            )
            OrderButton(
                text = "Зарегистрироваться",
                onClick = { /* TODO */ }
            )
        }
    }
}