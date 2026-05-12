package ru.orderdorms.features.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import ru.orderdorms.features.auth.presentation.components.BaseAuthScreen
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.components.OrderButton
import ru.orderdorms.ui.components.OrderTextField
import ru.orderdorms.ui.components.OtpCodeInput
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun WelcomeScreen(onLogin: () -> Unit, onRegister: () -> Unit) {
    BaseAuthScreen(title = "Текст-логотип") {
        Text(
            text = "Управляйте своей жизнью в общежитии!",
            style = OrderTheme.typography.bodyLarge,
            color = OrderTheme.colors.primaryTextColor.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = Dimensions.largePadding)
        )
        OrderButton(text = "Войти в аккаунт", onClick = onLogin)
        Spacer(modifier = Modifier.height(Dimensions.smallPadding))
        OrderButton(
            text = "Регистрация",
            onClick = onRegister,
            containerColor = OrderTheme.colors.bgPlaceholderColor,
            contentColor = OrderTheme.colors.primaryTextColor
        )
    }
}

@Composable
fun InvitationStep(code: String, onCodeChanged: (String) -> Unit, onNext: () -> Unit) {
    BaseAuthScreen(title = "Введите код приглашения") {
        OtpCodeInput(
            code = code,
            onCodeChanged = onCodeChanged,
            maxLength = 6
        )
        Spacer(modifier = Modifier.height(Dimensions.regularPadding))
        AuthClickableText("Нет кода?") { /* Действие */ }
        Spacer(modifier = Modifier.height(Dimensions.regularPadding))
        OrderButton(
            text = "Регистрация",
            isActive = code.length == 6,
            onClick = onNext
        )
    }
}

@Composable
fun EmailStep(email: String, onEmailChanged: (String) -> Unit, buttonText: String, onNext: () -> Unit) {
    BaseAuthScreen(title = "Введите почту") {
        OrderTextField(
            value = email,
            onValueChange = onEmailChanged,
            label = "Почта",
            placeholder = "example@mail.ru",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(Dimensions.largePadding))
        OrderButton(
            text = buttonText,
            isActive = email.contains("@"),
            onClick = onNext
        )
    }
}

@Composable
fun VerifyStep(code: String, onCodeChanged: (String) -> Unit, onNext: () -> Unit) {
    BaseAuthScreen(title = "Введите код с почты") {
        Text(
            text = "Код отправлен на почту",
            style = OrderTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(Dimensions.regularPadding))
        OtpCodeInput(
            code = code,
            onCodeChanged = onCodeChanged,
            maxLength = 6,
            isOnlyNumbers = true
        )
        Spacer(modifier = Modifier.height(Dimensions.regularPadding))
        AuthClickableText("Отправить еще раз через 60с") { }
        Spacer(modifier = Modifier.height(Dimensions.regularPadding))
        OrderButton(
            text = "Далее",
            isActive = code.length == 6,
            onClick = onNext
        )
    }
}

@Composable
fun PasswordStep(pass: String, onPassChanged: (String) -> Unit, passRepeat: String, onPassRepeatChanged: (String) -> Unit, title: String, onNext: () -> Unit) {
    BaseAuthScreen(title = title) {
        OrderTextField(
            value = pass,
            onValueChange = onPassChanged,
            label = "Пароль",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(Dimensions.smallPadding))
        OrderTextField(
            value = passRepeat,
            onValueChange = onPassRepeatChanged,
            label = "Подтверждение пароля",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(Dimensions.largePadding))
        OrderButton(
            text = "Готово",
            isActive = pass.isNotEmpty() && pass == passRepeat,
            onClick = onNext
        )
    }
}

@Composable
fun LoginScreen(
    email: String,
    onEmailChanged: (String) -> Unit,
    pass: String,
    onPassChanged: (String) -> Unit,
    onForgotPassword: () -> Unit,
    onRegClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    BaseAuthScreen(title = "Войти") {
        OrderTextField(value = email, onValueChange = onEmailChanged, label = "Почта")
        Spacer(modifier = Modifier.height(Dimensions.smallPadding))
        OrderTextField(
            value = pass,
            onValueChange = onPassChanged,
            label = "Пароль",
            visualTransformation = PasswordVisualTransformation()
        )

        AuthClickableText("Забыли пароль?") { onForgotPassword() }

        Spacer(modifier = Modifier.height(Dimensions.regularPadding))

        OrderButton(text = "Вход", onClick = onLoginClick)

        Spacer(modifier = Modifier.height(Dimensions.smallPadding))

        AuthClickableText("Нет аккаунта? Регистрация") { onRegClick() }
    }
}

@Composable
fun AuthClickableText(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = OrderTheme.typography.labelMedium,
        color = OrderTheme.colors.primaryTextColor.copy(alpha = 0.6f),
        modifier = Modifier
            .padding(vertical = Dimensions.smallPadding)
            .clickable { onClick() }
    )
}