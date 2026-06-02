package ru.orderdorms.features.services.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.orderdorms.features.services.domain.model.faq.FaqCategory

interface FaqRepository {
    fun getFaqCategories(): Flow<List<FaqCategory>>
}
