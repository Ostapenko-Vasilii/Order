package ru.orderdorms.features.auth.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.theme.OrderTheme

private const val DEMO_VERIFICATION_CODE = "123456"

private fun isValidEmail(value: String): Boolean =
    Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(value.trim())

private fun isValidInvitationCode(value: String): Boolean = value.trim().length == 6

private fun isValidPassword(value: String): Boolean = value.length >= 8

@Composable
fun AuthFlow(onAuthorized: () -> Unit) {
    val navigator = rememberAuthNavigator()

    Box(
        modifier = Modifier.fillMaxSize().background(OrderTheme.colors.bgPlaceholderColor),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(Dimensions.smallPadding)
                .background(OrderTheme.colors.onBackground, RoundedCornerShape(Dimensions.regularCornerRadius))
                .animateContentSize()
        ) {
            when (navigator.currentScreen) {
                AuthScreen.Welcome -> {
                    WelcomeScreen(
                        onLogin = { navigator.navigate(AuthScreen.Login) },
                        onRegister = { navigator.navigate(AuthScreen.Invitation) }
                    )
                }

                AuthScreen.Login -> {
                    val emailState = remember { mutableStateOf("") }
                    val passState = remember { mutableStateOf("") }
                    val email = emailState.value
                    val pass = passState.value
                    LoginScreen(
                        email = email,
                        onEmailChanged = { emailState.value = it },
                        pass = pass,
                        onPassChanged = { passState.value = it },
                        onForgotPassword = { },
                        onRegClick = { navigator.navigate(AuthScreen.Invitation) },
                        onBack = navigator::back,
                        emailError = when {
                            email.isBlank() -> null
                            !isValidEmail(email) -> "Неправильная почта"
                            else -> null
                        },
                        passError = when {
                            pass.isBlank() -> null
                            !isValidPassword(pass) -> "Пароль меньше 8 символов"
                            else -> null
                        },
                        onLoginClick = {
                            if (isValidEmail(email) && isValidPassword(pass)) {
                                onAuthorized()
                            }
                        },
                    )
                }

                AuthScreen.Invitation -> {
                    val codeState = remember { mutableStateOf("") }
                    val code = codeState.value
                    InvitationStep(
                        code = code,
                        onCodeChanged = { codeState.value = it },
                        onBack = navigator::back,
                        codeError = when {
                            code.isBlank() -> null
                            !isValidInvitationCode(code) -> "Неправильный код"
                            else -> null
                        },
                        onNext = { navigator.navigate(AuthScreen.Email) },
                    )
                }

                AuthScreen.Email -> {
                    val emailState = remember { mutableStateOf("") }
                    val email = emailState.value
                    EmailStep(
                        email = email,
                        onEmailChanged = { emailState.value = it },
                        buttonText = "Далее",
                        onBack = navigator::back,
                        emailError = when {
                            email.isBlank() -> null
                            !isValidEmail(email) -> "Неправильная почта"
                            else -> null
                        },
                        onNext = {
                            if (isValidEmail(email)) {
                                navigator.navigate(AuthScreen.Verify)
                            }
                        },
                    )
                }

                AuthScreen.Verify -> {
                    val codeState = remember { mutableStateOf("") }
                    val code = codeState.value
                    VerifyStep(
                        code = code,
                        onCodeChanged = { codeState.value = it },
                        onBack = navigator::back,
                        expectedCode = DEMO_VERIFICATION_CODE,
                        codeError = when {
                            code.isBlank() -> null
                            code != DEMO_VERIFICATION_CODE -> "Неправильный код"
                            else -> null
                        },
                        onNext = {
                            if (code == DEMO_VERIFICATION_CODE) {
                                navigator.navigate(AuthScreen.Authorized)
                            }
                        },
                    )
                }

                AuthScreen.Authorized -> {
                    AuthorizedStep(
                        onBack = navigator::back,
                        onNext = { navigator.navigate(AuthScreen.Password) },
                    )
                }

                AuthScreen.Password -> {
                    val passState = remember { mutableStateOf("") }
                    val passRepeatState = remember { mutableStateOf("") }
                    val pass = passState.value
                    val passRepeat = passRepeatState.value
                    PasswordStep(
                        pass = pass,
                        onPassChanged = { passState.value = it },
                        passRepeat = passRepeat,
                        onPassRepeatChanged = { passRepeatState.value = it },
                        title = "Придумайте пароль",
                        onBack = navigator::back,
                        passError = when {
                            pass.isBlank() -> null
                            !isValidPassword(pass) -> "Пароль меньше 8 символов"
                            else -> null
                        },
                        passRepeatError = when {
                            passRepeat.isBlank() -> null
                            passRepeat != pass -> "Пароли не совпадают"
                            else -> null
                        },
                        onNext = onAuthorized,
                    )
                }
            }
        }
    }
}

