package ru.orderdorms.features.services.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.orderdorms.features.services.data.network.model.FaqCategoryDto
import ru.orderdorms.features.services.data.network.model.toDomain
import ru.orderdorms.features.services.domain.model.faq.FaqCategory

class KtorFaqApiDataSource(
    private val httpClient: HttpClient
) : FaqApiDataSource {
    override suspend fun getCategories(): List<FaqCategory> {
        val response: List<FaqCategoryDto> = httpClient.get("faq").body()
        return response.map { it.toDomain() }
    }
}
