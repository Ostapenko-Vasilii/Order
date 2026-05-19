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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun AuthFlow(onAuthorized: () -> Unit) {
    val viewModel = rememberAuthViewModel()
    val state = viewModel.state

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
            when (state.currentScreen) {
                AuthScreen.Welcome -> {
                    WelcomeScreen(
                        onLogin = viewModel::openLogin,
                        onRegister = viewModel::openRegistration,
                    )
                }

                AuthScreen.Login -> {
                    LoginScreen(
                        email = state.loginEmail,
                        onEmailChanged = viewModel::onLoginEmailChanged,
                        pass = state.loginPassword,
                        onPassChanged = viewModel::onLoginPasswordChanged,
                        onForgotPassword = viewModel::openResetPassword,
                        onRegClick = viewModel::openRegistration,
                        onBack = viewModel::back,
                        emailError = viewModel.loginEmailError(),
                        passError = viewModel.loginPasswordError(),
                        isLoading = state.isLoading,
                        onLoginClick = { viewModel.submitLogin(onAuthorized) },
                    )
                }

                AuthScreen.Invitation -> {
                    InvitationStep(
                        code = state.invitationCode,
                        onCodeChanged = viewModel::onInvitationCodeChanged,
                        onBack = viewModel::back,
                        codeError = viewModel.invitationCodeError(),
                        isLoading = state.isLoading,
                        onNext = viewModel::submitInvitation,
                    )
                }

                AuthScreen.Email -> {
                    EmailStep(
                        email = state.email,
                        onEmailChanged = viewModel::onEmailChanged,
                        buttonText = if (state.flowMode == AuthFlowMode.ResetPassword) "Отправить код" else "Далее",
                        onBack = viewModel::back,
                        emailError = viewModel.emailError(),
                        isLoading = state.isLoading,
                        onNext = viewModel::submitEmail,
                    )
                }

                AuthScreen.Verify -> {
                    VerifyStep(
                        code = state.verifyCode,
                        onCodeChanged = viewModel::onVerifyCodeChanged,
                        onBack = viewModel::back,
                        email = state.email,
                        codeError = viewModel.verifyCodeError(),
                        isLoading = state.isLoading,
                        isCodeSubmitted = state.verifyCodeSubmitted,
                        retryAfterSeconds = state.nextRetryAfterSeconds ?: 60,
                        onResend = viewModel::resendCode,
                        onNext = viewModel::submitVerifyPressed,
                    )
                }

                AuthScreen.Password -> {
                    PasswordStep(
                        pass = state.password,
                        onPassChanged = viewModel::onPasswordChanged,
                        passRepeat = state.passwordRepeat,
                        onPassRepeatChanged = viewModel::onPasswordRepeatChanged,
                        title = if (state.flowMode == AuthFlowMode.ResetPassword) {
                            "Придумайте новый пароль"
                        } else {
                            "Придумайте пароль"
                        },
                        onBack = viewModel::back,
                        passError = viewModel.passwordError(),
                        passRepeatError = viewModel.passwordRepeatError(),
                        isLoading = state.isLoading,
                        onNext = { viewModel.submitPassword(onAuthorized) },
                    )
                }
            }
        }
    }
}

