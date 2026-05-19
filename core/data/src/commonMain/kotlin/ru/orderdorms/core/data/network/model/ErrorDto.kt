package ru.orderdorms.core.data.network.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ApiErrorEnvelopeDto(
    val error: ApiErrorDto,
)

@Serializable
data class ApiErrorDto(
    val code: String,
    val message: String,
    val details: JsonObject? = null,
)
