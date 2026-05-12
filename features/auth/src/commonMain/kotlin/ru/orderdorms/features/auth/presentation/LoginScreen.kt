package ru.orderdorms.features.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.orderdorms.features.auth.presentation.components.BaseAuthScreen
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.components.OrderButton
import ru.orderdorms.ui.components.OtpCodeInput
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(OrderTheme.colors.bgPlaceholderColor),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .widthIn(min = 100.dp, max = 400.dp)
                .heightIn(min = 100.dp)
                .fillMaxWidth()
                .padding(Dimensions.smallPadding)
                .background(
                    color = OrderTheme.colors.onBackground, shape = RoundedCornerShape(
                        Dimensions.regularCornerRadius
                    )
                ),
            contentAlignment = Alignment.Center,
        ) {

        }
    }
}




