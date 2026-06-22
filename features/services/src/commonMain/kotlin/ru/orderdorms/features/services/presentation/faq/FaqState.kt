package ru.orderdorms.features.services.presentation.faq

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.features.services.domain.model.faq.FaqCategory
import ru.orderdorms.features.services.domain.model.faq.FaqQuestion

data class FaqState(
    val categories: List<FaqCategory> = emptyList(),
    val selectedQuestion: FaqQuestion? = null,
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val error: DomainError? = null
) {
    val filteredCategories: List<FaqCategory>
        get() = categories.map { category ->
            category.copy(
                questions = category.questions.filter {
                    it.title.contains(searchQuery, ignoreCase = true) ||
                    category.title.contains(searchQuery, ignoreCase = true)
                }
            )
        }.filter { it.questions.isNotEmpty() || it.title.contains(searchQuery, ignoreCase = true) }
}
