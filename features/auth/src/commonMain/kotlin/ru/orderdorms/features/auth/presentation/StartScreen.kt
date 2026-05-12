package ru.orderdorms.features.auth.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import order.features.auth.generated.resources.Res
import order.features.auth.generated.resources.login
import order.features.auth.generated.resources.register
import org.jetbrains.compose.resources.stringResource
import ru.orderdorms.ui.components.OrderButton

@Composable
fun StartScreen() {
    Column {
        Column {
            OrderButton(
                text = stringResource(Res.string.login),
                onClick = { /* TODO */ }
            )
            OrderButton(
                text = stringResource(Res.string.register),
                onClick = { /* TODO */ }
            )
        }
    }
}