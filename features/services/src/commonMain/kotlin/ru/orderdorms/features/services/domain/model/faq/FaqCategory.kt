package ru.orderdorms.features.services.domain.model.faq

import androidx.compose.ui.graphics.vector.ImageVector

data class FaqCategory(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val questions: List<FaqQuestion> = emptyList()
)

data class FaqQuestion(
    val id: String,
    val title: String,
    val answerMarkdown: String
)
