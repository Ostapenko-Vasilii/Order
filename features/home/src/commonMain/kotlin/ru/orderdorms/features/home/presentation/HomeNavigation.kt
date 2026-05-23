package ru.orderdorms.features.home.presentation

import androidx.compose.runtime.Composable

@Composable
fun HomeFlow(onLogout: () -> Unit, onOpenServices: () -> Unit = {}) {
    val viewModel = rememberHomeViewModel()
    val state = viewModel.state

    HomeScreen(
        state = state,
        onRefresh = viewModel::refresh,
        onOpenServices = onOpenServices,
    )
}
