package ru.orderdorms.features.auth.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun AuthFlow() {
    val viewModel: AuthViewModel = viewModel { AuthViewModel() }
    val state by viewModel.state.collectAsState()
    val navController = rememberNavController()

    // Sync route with persistence
    androidx.compose.runtime.LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { entry ->
            entry.destination.route?.let { viewModel.setRoute(it) }
        }
    }

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
            NavHost(
                navController = navController,
                startDestination = viewModel.getInitialRoute(),
                enterTransition = {
                    slideInHorizontally(animationSpec = tween(300), initialOffsetX = { it }) + fadeIn(animationSpec = tween(300))
                },
                exitTransition = {
                    slideOutHorizontally(animationSpec = tween(300), targetOffsetX = { -it }) + fadeOut(animationSpec = tween(300))
                },
                popEnterTransition = {
                    slideInHorizontally(animationSpec = tween(300), initialOffsetX = { -it }) + fadeIn(animationSpec = tween(300))
                },
                popExitTransition = {
                    slideOutHorizontally(animationSpec = tween(300), targetOffsetX = { it }) + fadeOut(animationSpec = tween(300))
                }
            ) {
                composable("welcome") {
                    WelcomeScreen(
                        onLogin = { navController.navigate("login") },
                        onRegister = {
                            viewModel.setRegistration(true)
                            navController.navigate("invitation")
                        }
                    )
                }
                composable("login") {
                    var email by remember { mutableStateOf("") }
                    var pass by remember { mutableStateOf("") }
                    LoginScreen(
                        email = email,
                        onEmailChanged = { email = it },
                        pass = pass,
                        onPassChanged = { pass = it },
                        onForgotPassword = { },
                        onRegClick = {
                            viewModel.setRegistration(true)
                            navController.navigate("invitation")
                        },
                        onLoginClick = { }
                    )
                }
                composable("invitation") {
                    InvitationStep(
                        code = state.invitationCode,
                        onCodeChanged = { viewModel.updateInvitationCode(it) },
                        onNext = { navController.navigate("email") }
                    )
                }
                composable("email") {
                    EmailStep(
                        email = state.email,
                        onEmailChanged = { viewModel.updateEmail(it) },
                        buttonText = "Далее",
                        onNext = { navController.navigate("verify") }
                    )
                }
                composable("verify") {
                    var code by remember { mutableStateOf("") }
                    VerifyStep(
                        code = code,
                        onCodeChanged = { code = it },
                        onNext = { navController.navigate("password") }
                    )
                }
                composable("password") {
                    var pass by remember { mutableStateOf("") }
                    var passRepeat by remember { mutableStateOf("") }
                    PasswordStep(
                        pass = pass,
                        onPassChanged = { pass = it },
                        passRepeat = passRepeat,
                        onPassRepeatChanged = { passRepeat = it },
                        title = "Придумайте пароль",
                        onNext = { /* Finish */ }
                    )
                }
            }
        }
    }
}
