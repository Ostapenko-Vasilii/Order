package ru.orderdorms.features.auth.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import ru.orderdorms.features.auth.resources.Res
import ru.orderdorms.features.auth.resources.done_button
import ru.orderdorms.features.auth.resources.email_label
import ru.orderdorms.features.auth.resources.email_placeholder
import ru.orderdorms.features.auth.resources.email_title
import ru.orderdorms.features.auth.resources.forgot_password
import ru.orderdorms.features.auth.resources.invitation_title
import ru.orderdorms.features.auth.resources.login_action
import ru.orderdorms.features.auth.resources.login_button
import ru.orderdorms.features.auth.resources.login_title
import ru.orderdorms.features.auth.resources.next_button
import ru.orderdorms.features.auth.resources.no_account_register
import ru.orderdorms.features.auth.resources.no_code
import ru.orderdorms.features.auth.resources.password_label
import ru.orderdorms.features.auth.resources.password_placeholder
import ru.orderdorms.features.auth.resources.password_repeat_label
import ru.orderdorms.features.auth.resources.password_repeat_placeholder
import ru.orderdorms.features.auth.resources.register
import ru.orderdorms.features.auth.resources.resend_code
import ru.orderdorms.features.auth.resources.verify_title
import ru.orderdorms.features.auth.resources.welcome_subtitle
import org.jetbrains.compose.resources.stringResource
import ru.orderdorms.features.auth.presentation.components.BaseAuthScreen
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.components.OrderButton
import ru.orderdorms.ui.components.OrderTextField
import ru.orderdorms.ui.components.OtpCodeInput
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun WelcomeScreen(onLogin: () -> Unit, onRegister: () -> Unit) {
    BaseAuthScreen(title = "Order") {
        Text(
            text = stringResource(Res.string.welcome_subtitle),
            style = OrderTheme.typography.bodyLarge,
            color = OrderTheme.colors.primaryTextColor.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = Dimensions.largePadding)
        )
        OrderButton(text = stringResource(Res.string.login_button), onClick = onLogin)
        Spacer(modifier = Modifier.height(Dimensions.smallPadding))
        OrderButton(
            text = stringResource(Res.string.register),
            onClick = onRegister,
            containerColor = OrderTheme.colors.bgPlaceholderColor,
            contentColor = OrderTheme.colors.primaryTextColor
        )
    }
}

@Composable
fun InvitationStep(
    code: String,
    onCodeChanged: (String) -> Unit,
    onBack: () -> Unit,
    codeError: String?,
    isLoading: Boolean,
    onNext: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    
    BaseAuthScreen(title = stringResource(Res.string.invitation_title), onBack = onBack) {
        OtpCodeInput(
            code = code,
            onCodeChanged = onCodeChanged,
            isValid = if (codeError == null && code.length == 6) true else if (codeError != null) false else null,
            errorText = codeError,
            modifier = Modifier.focusRequester(focusRequester),
        )
        Spacer(modifier = Modifier.height(Dimensions.regularPadding))
        AuthClickableText(stringResource(Res.string.no_code)) { /* Действие */ }
        Spacer(modifier = Modifier.height(Dimensions.regularPadding))
        OrderButton(
            text = stringResource(Res.string.register),
            isActive = code.length == 6,
            isLoading = isLoading,
            onClick = onNext
        )
    }
}

@Composable
fun EmailStep(
    email: String,
    onEmailChanged: (String) -> Unit,
    buttonText: String,
    onBack: () -> Unit,
    emailError: String?,
    isLoading: Boolean,
    onNext: () -> Unit,
) {
    BaseAuthScreen(title = stringResource(Res.string.email_title), onBack = onBack) {
        OrderTextField(
            value = email,
            onValueChange = onEmailChanged,
            label = stringResource(Res.string.email_label),
            placeholder = stringResource(Res.string.email_placeholder),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = emailError != null,
            errorText = emailError,
        )
        Spacer(modifier = Modifier.height(Dimensions.largePadding))
        OrderButton(
            text = buttonText,
            isActive = emailError == null && email.isNotBlank(),
            isLoading = isLoading,
            onClick = onNext
        )
    }
}

@Composable
fun VerifyStep(
    code: String,
    onCodeChanged: (String) -> Unit,
    onBack: () -> Unit,
    email: String,
    codeError: String?,
    isLoading: Boolean,
    isCodeSubmitted: Boolean,
    retryAfterSeconds: Int,
    onResend: () -> Unit,
    onNext: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BaseAuthScreen(title = stringResource(Res.string.verify_title), onBack = onBack) {
        Text(
            text = "${stringResource(Res.string.email_label)}: $email",
            style = OrderTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(Dimensions.regularPadding))
        OtpCodeInput(
            code = code,
            onCodeChanged = onCodeChanged,
            maxLength = 6,
            isOnlyNumbers = true,
            isEnabled = !isLoading,
            showError = isCodeSubmitted,
            isValid = if (codeError == null && code.length == 6) true else if (codeError != null) false else null,
            modifier = Modifier.focusRequester(focusRequester),
            errorText = codeError,
        )
        Spacer(modifier = Modifier.height(Dimensions.regularPadding))
        AuthClickableText(stringResource(Res.string.resend_code, retryAfterSeconds)) { onResend() }
        Spacer(modifier = Modifier.height(Dimensions.regularPadding))
        OrderButton(
            text = stringResource(Res.string.next_button),
            isActive = codeError == null && code.length == 6 && !isLoading,
            isLoading = isLoading,
            onClick = onNext
        )
    }
}

@Composable
fun PasswordStep(
    pass: String,
    onPassChanged: (String) -> Unit,
    passRepeat: String,
    onPassRepeatChanged: (String) -> Unit,
    title: String,
    onBack: () -> Unit,
    passError: String?,
    passRepeatError: String?,
    isLoading: Boolean,
    onNext: () -> Unit,
) {
    val passwordRepeatFocusRequester = remember { FocusRequester() }

    BaseAuthScreen(title = title, onBack = onBack) {
        OrderTextField(
            value = pass,
            onValueChange = onPassChanged,
            label = stringResource(Res.string.password_label),
            placeholder = stringResource(Res.string.password_placeholder),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { passwordRepeatFocusRequester.requestFocus() }
            ),
            isError = passError != null,
            errorText = passError,
        )
        Spacer(modifier = Modifier.height(Dimensions.smallPadding))
        OrderTextField(
            value = passRepeat,
            onValueChange = onPassRepeatChanged,
            label = stringResource(Res.string.password_repeat_label),
            placeholder = stringResource(Res.string.password_repeat_placeholder),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { if (passError == null && passRepeatError == null) onNext() }
            ),
            modifier = Modifier.focusRequester(passwordRepeatFocusRequester),
            isError = passRepeatError != null,
            errorText = passRepeatError,
        )
        Spacer(modifier = Modifier.height(Dimensions.largePadding))
        OrderButton(
            text = stringResource(Res.string.done_button),
            isActive = passError == null && passRepeatError == null && pass.isNotBlank() && passRepeat.isNotBlank(),
            isLoading = isLoading,
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
    onBack: () -> Unit,
    emailError: String?,
    passError: String?,
    isLoading: Boolean,
    onLoginClick: () -> Unit
) {
    BaseAuthScreen(title = stringResource(Res.string.login_title), onBack = onBack) {
        OrderTextField(
            value = email,
            onValueChange = onEmailChanged,
            label = stringResource(Res.string.email_label),
            placeholder = stringResource(Res.string.email_placeholder),
            isError = emailError != null,
            errorText = emailError,
        )
        Spacer(modifier = Modifier.height(Dimensions.smallPadding))
        OrderTextField(
            value = pass,
            onValueChange = onPassChanged,
            label = stringResource(Res.string.password_label),
            placeholder = stringResource(Res.string.password_placeholder),
            visualTransformation = PasswordVisualTransformation(),
            isError = passError != null,
            errorText = passError,
        )

        AuthClickableText(stringResource(Res.string.forgot_password)) { onForgotPassword() }

        Spacer(modifier = Modifier.height(Dimensions.regularPadding))

        OrderButton(
            text = stringResource(Res.string.login_action),
            isActive = emailError == null && email.isNotBlank() && pass.isNotBlank(),
            isLoading = isLoading,
            onClick = onLoginClick
        )

        Spacer(modifier = Modifier.height(Dimensions.smallPadding))

        AuthClickableText(stringResource(Res.string.no_account_register)) { onRegClick() }
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