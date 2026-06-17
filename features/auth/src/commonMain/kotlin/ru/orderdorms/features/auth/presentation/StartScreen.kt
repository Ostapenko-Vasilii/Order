package ru.orderdorms.features.auth.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import ru.orderdorms.features.auth.resources.Res
import ru.orderdorms.features.auth.resources.login
import ru.orderdorms.features.auth.resources.register
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