package ru.orderdorms.features.services.data.network.model

import kotlinx.serialization.Serializable
import ru.orderdorms.features.services.domain.model.faq.FaqCategory
import ru.orderdorms.features.services.domain.model.faq.FaqQuestion
import ru.orderdorms.ui.icons.dormIco

@Serializable
data class FaqCategoryDto(
    val id: String,
    val title: String,
    val iconName: String,
    val questions: List<FaqQuestionDto>
)

@Serializable
data class FaqQuestionDto(
    val id: String,
    val title: String,
    val answerMarkdown: String
)

fun FaqCategoryDto.toDomain(): FaqCategory = FaqCategory(
    id = id,
    title = title,
    // Map iconName to ImageVector. For now, defaulting to dormIco.
    icon = dormIco, 
    questions = questions.map { it.toDomain() }
)

fun FaqQuestionDto.toDomain(): FaqQuestion = FaqQuestion(
    id = id,
    title = title,
    answerMarkdown = answerMarkdown
)
