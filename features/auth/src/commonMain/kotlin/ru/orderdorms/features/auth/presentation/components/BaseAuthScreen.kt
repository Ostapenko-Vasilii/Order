package ru.orderdorms.features.auth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun BaseAuthScreen(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.padding(Dimensions.smallPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = title,
            style = OrderTheme.typography.displayMedium,
            modifier = Modifier.padding(bottom = Dimensions.regularPadding),
            color = OrderTheme.colors.primaryTextColor,
        )
        content()

    }

}