package ru.orderdorms.core.domain.error

data class DomainError(
    val code: String,
    val message: String,
    val details: Map<String, String> = emptyMap(),
    val statusCode: Int? = null,
)
