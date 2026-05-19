package ru.orderdorms.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.orderdorms.ui.theme.OrderTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OtpCodeInput(
    code: String,
    onCodeChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    isValid: Boolean? = null,
    isEnabled: Boolean = true,
    showError: Boolean = false,
    maxLength: Int = 6,
    isOnlyNumbers: Boolean = false,
    errorText: String? = null,
) {
    val focusRequester = remember { FocusRequester() }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BasicTextField(
                value = code,
                onValueChange = { input ->
                    if (input.length <= maxLength && isEnabled) {
                        val filtered = if (isOnlyNumbers) {
                            input.filter { it.isDigit() }
                        } else {
                            input.filter { it.isLetterOrDigit() }.uppercase()
                        }
                        onCodeChanged(filtered)
                    }
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .size(1.dp)
                    .alpha(0f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isOnlyNumbers) KeyboardType.Number else KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Characters
                ),
                enabled = isEnabled
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.pointerInput(isEnabled) {
                    if (isEnabled) {
                        detectTapGestures {
                            focusRequester.requestFocus()
                            softwareKeyboardController?.show()
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    }
                }
            ) {
                val step = when {
                    maxLength % 3 == 0 -> 3
                    maxLength % 2 == 0 -> 2
                    else -> 0
                }

                repeat(maxLength) { index ->
                    OtpCharCell(
                        char = code.getOrNull(index)?.toString() ?: "",
                        isFocused = code.length == index && isEnabled,
                        isValid = if (showError) isValid else null,
                        isEnabled = isEnabled
                    )

                    if (step > 0 && (index + 1) % step == 0 && index != maxLength - 1) {
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                }
            }

            if (showError && !errorText.isNullOrBlank()) {
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = errorText,
                    style = OrderTheme.typography.labelMedium,
                    color = Color(0xFFF44336)
                )
            }
        }
    }
}

@Composable
private fun OtpCharCell(
    char: String,
    isFocused: Boolean,
    isValid: Boolean?,
    isEnabled: Boolean
) {
    val borderColor by animateColorAsState(
        targetValue = when {
            isValid == true -> Color(0xFF4CAF50)
            isValid == false -> Color(0xFFF44336)
            !isEnabled && isValid == null -> Color.LightGray.copy(alpha = 0.3f)
            isFocused -> Color.DarkGray
            else -> Color.LightGray.copy(alpha = 0.5f)
        }
    )

    val bgColor by animateColorAsState(
        targetValue = when {
            isValid == true -> Color(0xFF4CAF50).copy(alpha = 0.1f)
            isValid == false -> Color(0xFFF44336).copy(alpha = 0.1f)
            !isEnabled && isValid == null -> Color.LightGray.copy(alpha = 0.2f)
            else -> Color.Transparent
        }
    )

    Box(
        modifier = Modifier
            .size(width = 40.dp, height = 48.dp)
            .background(bgColor, RoundedCornerShape(8.dp))
            .border(1.dp, borderColor, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = char,
            transitionSpec = {
                (fadeIn() + scaleIn(initialScale = 0.8f)).togetherWith(fadeOut() + scaleOut())
            }
        ) { text ->
            Text(
                text = text,
                style = OrderTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isEnabled) Color.Black else Color.Gray
                ),
                color = OrderTheme.colors.primaryTextColor
            )
        }
    }
}