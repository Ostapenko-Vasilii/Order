package ru.orderdorms.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun OrderTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    errorText: String? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = OrderTheme.typography.labelMedium,
                color = OrderTheme.colors.primaryTextColor,
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                style = OrderTheme.typography.headlineMedium,
            )
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = if (isError) Color(0xFFF44336) else Color(0xFFC4C4C4),
            focusedBorderColor = if (isError) Color(0xFFF44336) else Color(0xFFC4C4C4),
            errorBorderColor = Color(0xFFF44336),
            unfocusedLabelColor = Color(0xFF1D1B20),
            focusedLabelColor = Color(0xFF1D1B20),
            unfocusedPlaceholderColor = Color(0xFF9E9E9E),
            focusedPlaceholderColor = Color(0xFF9E9E9E),
            errorLabelColor = Color(0xFFF44336),
            errorPlaceholderColor = Color(0xFFF44336).copy(alpha = 0.7f),
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        singleLine = true,
        isError = isError,
        supportingText = {
            if (!errorText.isNullOrBlank()) {
                Text(
                    text = errorText,
                    style = OrderTheme.typography.labelMedium,
                    color = Color(0xFFF44336),
                )
            }
        },
    )
}

@Preview
@Composable
fun OrderTextFieldPreview() {
    MaterialTheme {
        Surface {
            OrderTextField(
                value = "",
                onValueChange = {},
                label = "Имя",
                placeholder = "Введите имя",
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}
