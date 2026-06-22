package ru.orderdorms.features.services.presentation.cleaning

data class CleaningState(
    val isLoading: Boolean = false,
    val infoMarkdown: String? = null,
    val errorMessage: String? = null
)
