package ru.orderdorms.features.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.auth.domain.usecase.CheckInviteCodeUseCase
import ru.orderdorms.features.auth.domain.usecase.LoginUseCase
import ru.orderdorms.features.auth.domain.usecase.ResendRegistrationCodeUseCase
import ru.orderdorms.features.auth.domain.usecase.SendForgotPasswordEmailUseCase
import ru.orderdorms.features.auth.domain.usecase.SendRegistrationEmailUseCase
import ru.orderdorms.features.auth.domain.usecase.SetForgotPasswordUseCase
import ru.orderdorms.features.auth.domain.usecase.SetRegistrationPasswordUseCase
import ru.orderdorms.features.auth.domain.usecase.VerifyForgotPasswordCodeUseCase
import ru.orderdorms.features.auth.domain.usecase.VerifyRegistrationCodeUseCase

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val checkInviteCodeUseCase: CheckInviteCodeUseCase,
    private val sendRegistrationEmailUseCase: SendRegistrationEmailUseCase,
    private val resendRegistrationCodeUseCase: ResendRegistrationCodeUseCase,
    private val verifyRegistrationCodeUseCase: VerifyRegistrationCodeUseCase,
    private val setRegistrationPasswordUseCase: SetRegistrationPasswordUseCase,
    private val sendForgotPasswordEmailUseCase: SendForgotPasswordEmailUseCase,
    private val verifyForgotPasswordCodeUseCase: VerifyForgotPasswordCodeUseCase,
    private val setForgotPasswordUseCase: SetForgotPasswordUseCase,
) {
    private val scope: CoroutineScope = MainScope()

    var state by mutableStateOf(AuthUiState())
        private set

    private fun update(transform: (AuthUiState) -> AuthUiState) {
        state = transform(state)
    }

    private fun setLoading(value: Boolean) {
        update { it.copy(isLoading = value) }
    }

    private fun isValidEmail(value: String): Boolean =
        Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(value.trim())

    private fun isValidInvitationCode(value: String): Boolean = value.trim().length >= 3

    private fun isValidPassword(value: String): Boolean = value.length >= 6

    private fun isValidVerifyCode(value: String): Boolean = value.trim().length == 6

    fun loginEmailError(): String? = when {
        state.loginEmail.isBlank() -> null
        !isValidEmail(state.loginEmail) -> "Неправильная почта"
        else -> null
    }

    fun loginPasswordError(): String? = when {
        state.loginPassword.isBlank() -> null
        !isValidPassword(state.loginPassword) -> "Пароль меньше 6 символов"
        state.loginErrorFromBackend != null -> state.loginErrorFromBackend
        else -> null
    }

    fun invitationCodeError(): String? = when {
        state.invitationCode.isBlank() -> null
        !isValidInvitationCode(state.invitationCode) -> "Код слишком короткий"
        state.inviteErrorFromBackend != null -> state.inviteErrorFromBackend
        else -> null
    }

    fun emailError(): String? = when {
        state.email.isBlank() -> null
        !isValidEmail(state.email) -> "Неправильная почта"
        state.emailErrorFromBackend != null -> state.emailErrorFromBackend
        else -> null
    }

    fun verifyCodeError(): String? = when {
        state.verifyCode.isBlank() -> null
        !isValidVerifyCode(state.verifyCode) -> "Неправильный код"
        state.verifyErrorFromBackend != null -> state.verifyErrorFromBackend
        else -> null
    }

    fun passwordError(): String? = when {
        state.password.isBlank() -> null
        !isValidPassword(state.password) -> "Пароль меньше 6 символов"
        state.passwordErrorFromBackend != null -> state.passwordErrorFromBackend
        else -> null
    }

    fun passwordRepeatError(): String? = when {
        state.passwordRepeat.isBlank() -> null
        state.passwordRepeat != state.password -> "Пароли не совпадают"
        else -> null
    }

    fun onLoginEmailChanged(value: String) {
        update { it.copy(loginEmail = value, loginErrorFromBackend = null) }
    }

    fun onLoginPasswordChanged(value: String) {
        update { it.copy(loginPassword = value, loginErrorFromBackend = null) }
    }

    fun onInvitationCodeChanged(value: String) {
        update { it.copy(invitationCode = value, inviteErrorFromBackend = null) }
    }

    fun onEmailChanged(value: String) {
        update { it.copy(email = value, emailErrorFromBackend = null) }
    }

    fun onVerifyCodeChanged(value: String) {
        update { it.copy(verifyCode = value, verifyErrorFromBackend = null) }
    }

    fun onPasswordChanged(value: String) {
        update { it.copy(password = value, passwordErrorFromBackend = null) }
    }

    fun onPasswordRepeatChanged(value: String) {
        update { it.copy(passwordRepeat = value) }
    }

    fun openLogin() {
        update {
            it.copy(
                currentScreen = AuthScreen.Login,
                flowMode = AuthFlowMode.Registration,
                loginErrorFromBackend = null,
                emailErrorFromBackend = null,
                verifyErrorFromBackend = null,
                passwordErrorFromBackend = null,
            )
        }
    }

    fun openRegistration() {
        update {
            it.copy(
                currentScreen = AuthScreen.Invitation,
                flowMode = AuthFlowMode.Registration,
                invitationCode = "",
                email = "",
                verifyCode = "",
                password = "",
                passwordRepeat = "",
                inviteErrorFromBackend = null,
                emailErrorFromBackend = null,
                verifyErrorFromBackend = null,
                passwordErrorFromBackend = null,
            )
        }
    }

    fun openResetPassword() {
        update {
            it.copy(
                currentScreen = AuthScreen.Email,
                flowMode = AuthFlowMode.ResetPassword,
                email = it.loginEmail,
                verifyCode = "",
                password = "",
                passwordRepeat = "",
                emailErrorFromBackend = null,
                verifyErrorFromBackend = null,
                passwordErrorFromBackend = null,
            )
        }
    }

    fun back() {
        update {
            when (it.currentScreen) {
                AuthScreen.Welcome -> it
                AuthScreen.Login -> it.copy(currentScreen = AuthScreen.Welcome)
                AuthScreen.Invitation -> it.copy(currentScreen = AuthScreen.Welcome)
                AuthScreen.Email -> {
                    val previous = if (it.flowMode == AuthFlowMode.Registration) {
                        AuthScreen.Invitation
                    } else {
                        AuthScreen.Login
                    }
                    it.copy(currentScreen = previous)
                }
                AuthScreen.Verify -> it.copy(currentScreen = AuthScreen.Email)
                AuthScreen.Password -> it.copy(currentScreen = AuthScreen.Email)
            }
        }
    }

    fun submitLogin(onAuthorized: () -> Unit) {
        val snapshot = state
        if (!isValidEmail(snapshot.loginEmail) || !isValidPassword(snapshot.loginPassword)) return

        execute {
            when (val result = loginUseCase(snapshot.loginEmail.trim(), snapshot.loginPassword)) {
                is Either.Left -> update { it.copy(loginErrorFromBackend = result.value.message) }
                is Either.Right -> onAuthorized()
            }
        }
    }

    fun submitInvitation() {
        val snapshot = state
        if (!isValidInvitationCode(snapshot.invitationCode)) return

        execute {
            when (val result = checkInviteCodeUseCase(snapshot.invitationCode.trim())) {
                is Either.Left -> update { it.copy(inviteErrorFromBackend = result.value.message) }
                is Either.Right -> {
                    update {
                        it.copy(
                            currentScreen = AuthScreen.Email,
                            email = "",
                            verifyCode = "",
                            password = "",
                            passwordRepeat = "",
                            flowMode = AuthFlowMode.Registration,
                            inviteErrorFromBackend = null,
                            emailErrorFromBackend = null,
                        )
                    }
                }
            }
        }
    }

    fun submitEmail() {
        val snapshot = state
        if (!isValidEmail(snapshot.email)) return

        execute {
            when (snapshot.flowMode) {
                AuthFlowMode.Registration -> {
                    when (val result = sendRegistrationEmailUseCase(snapshot.email.trim())) {
                        is Either.Left -> update { it.copy(emailErrorFromBackend = result.value.message) }
                        is Either.Right -> moveToVerify(result.value.nextRetryAfter)
                    }
                }

                AuthFlowMode.ResetPassword -> {
                    when (val result = sendForgotPasswordEmailUseCase(snapshot.email.trim())) {
                        is Either.Left -> update { it.copy(emailErrorFromBackend = result.value.message) }
                        is Either.Right -> moveToVerify(result.value.nextRetryAfter)
                    }
                }
            }
        }
    }

    fun resendCode() {
        val snapshot = state
        if (snapshot.email.isBlank()) return

        execute {
            when (snapshot.flowMode) {
                AuthFlowMode.Registration -> {
                    when (val result = resendRegistrationCodeUseCase()) {
                        is Either.Left -> update { it.copy(verifyErrorFromBackend = prettifyError(result.value)) }
                        is Either.Right -> update {
                            it.copy(
                                nextRetryAfterSeconds = result.value.nextRetryAfter,
                                verifyErrorFromBackend = result.value.message,
                            )
                        }
                    }
                }

                AuthFlowMode.ResetPassword -> {
                    when (val result = sendForgotPasswordEmailUseCase(snapshot.email.trim())) {
                        is Either.Left -> update { it.copy(verifyErrorFromBackend = prettifyError(result.value)) }
                        is Either.Right -> update {
                            it.copy(
                                nextRetryAfterSeconds = result.value.nextRetryAfter,
                                verifyErrorFromBackend = "Код повторно отправлен на почту",
                            )
                        }
                    }
                }
            }
        }
    }

    fun submitVerifyPressed() {
        update { it.copy(verifyCodeSubmitted = true, verifyErrorFromBackend = null) }
        val snapshot = state
        if (!isValidVerifyCode(snapshot.verifyCode)) return

        execute {
            when (snapshot.flowMode) {
                AuthFlowMode.Registration -> {
                    when (val result = verifyRegistrationCodeUseCase(snapshot.verifyCode.trim())) {
                        is Either.Left -> update { it.copy(verifyErrorFromBackend = result.value.message) }
                        is Either.Right -> moveToPassword()
                    }
                }

                AuthFlowMode.ResetPassword -> {
                    when (val result = verifyForgotPasswordCodeUseCase(snapshot.verifyCode.trim())) {
                        is Either.Left -> update { it.copy(verifyErrorFromBackend = result.value.message) }
                        is Either.Right -> moveToPassword()
                    }
                }
            }
        }
    }

    fun submitPassword(onAuthorized: () -> Unit) {
        val snapshot = state
        val passwordIsValid = isValidPassword(snapshot.password)
        val passwordsMatch = snapshot.password == snapshot.passwordRepeat
        if (!passwordIsValid || !passwordsMatch) return

        execute {
            when (snapshot.flowMode) {
                AuthFlowMode.Registration -> {
                    when (val result = setRegistrationPasswordUseCase(snapshot.password)) {
                        is Either.Left -> update { it.copy(passwordErrorFromBackend = result.value.message) }
                        is Either.Right -> onAuthorized()
                    }
                }

                AuthFlowMode.ResetPassword -> {
                    when (val result = setForgotPasswordUseCase(snapshot.password)) {
                        is Either.Left -> update { it.copy(passwordErrorFromBackend = result.value.message) }
                        is Either.Right -> {
                            update {
                                it.copy(
                                    currentScreen = AuthScreen.Login,
                                    flowMode = AuthFlowMode.Registration,
                                    loginEmail = it.email,
                                    loginPassword = "",
                                    password = "",
                                    passwordRepeat = "",
                                    verifyCode = "",
                                    passwordErrorFromBackend = null,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun dispose() {
        scope.cancel()
    }

    private fun moveToVerify(nextRetryAfter: Int) {
        update {
            it.copy(
                currentScreen = AuthScreen.Verify,
                verifyCode = "",
                password = "",
                passwordRepeat = "",
                verifyCodeSubmitted = false,
                emailErrorFromBackend = null,
                verifyErrorFromBackend = null,
                nextRetryAfterSeconds = nextRetryAfter,
            )
        }
    }

    private fun moveToPassword() {
        update {
            it.copy(
                currentScreen = AuthScreen.Password,
                password = "",
                passwordRepeat = "",
                verifyCodeSubmitted = false,
                verifyErrorFromBackend = null,
            )
        }
    }

    private fun execute(block: suspend () -> Unit) {
        scope.launch {
            setLoading(true)
            try {
                block()
            } finally {
                setLoading(false)
            }
        }
    }

    private fun prettifyError(error: DomainError): String {
        val retryAfter = error.details["retry_after"]
        return if (error.code == "TOO_MANY_REQUESTS" && retryAfter != null) {
            "Слишком часто. Повторите через $retryAfter сек"
        } else {
            error.message
        }
    }
}

@Composable
fun rememberAuthViewModel(): AuthViewModel {
    val koin = KoinPlatform.getKoin()
    val viewModel = remember {
        AuthViewModel(
            loginUseCase = koin.get(),
            checkInviteCodeUseCase = koin.get(),
            sendRegistrationEmailUseCase = koin.get(),
            resendRegistrationCodeUseCase = koin.get(),
            verifyRegistrationCodeUseCase = koin.get(),
            setRegistrationPasswordUseCase = koin.get(),
            sendForgotPasswordEmailUseCase = koin.get(),
            verifyForgotPasswordCodeUseCase = koin.get(),
            setForgotPasswordUseCase = koin.get(),
        )
    }
    DisposableEffect(viewModel) {
        onDispose { viewModel.dispose() }
    }
    return viewModel
}
