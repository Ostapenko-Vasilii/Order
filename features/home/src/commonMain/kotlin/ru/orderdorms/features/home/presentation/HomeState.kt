package ru.orderdorms.features.home.presentation

import ru.orderdorms.features.home.domain.model.HomeDashboard

data class HomeUiState(
    val isLoading: Boolean = true,
    val dashboard: HomeDashboard? = null,
    val errorMessage: String? = null,
)

