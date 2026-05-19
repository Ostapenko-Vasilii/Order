package ru.orderdorms.core.data.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import ru.orderdorms.core.data.network.model.ApiErrorEnvelopeDto
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either

internal suspend inline fun <reified T> safeApiCall(
    json: Json,
    crossinline request: suspend () -> HttpResponse,
): Either<DomainError, T> {
    return try {
        val response = request()
        if (response.status.isSuccess()) {
            Either.Right(response.body())
        } else {
            Either.Left(parseError(json, response))
        }
    } catch (e: Throwable) {
        Either.Left(
            DomainError(
                code = "NETWORK_ERROR",
                message = e.message ?: "Ошибка сети",
            )
        )
    }
}

internal suspend fun parseError(json: Json, response: HttpResponse): DomainError {
    val fallback = DomainError(
        code = "HTTP_${response.status.value}",
        message = "Ошибка запроса (${response.status.value})",
        statusCode = response.status.value,
    )

    return try {
        val payload = response.bodyAsText()
        val parsed = json.decodeFromString<ApiErrorEnvelopeDto>(payload)
        val details = parsed.error.details?.mapValues { (_, value) ->
            (value as? JsonPrimitive)?.content ?: value.toString()
        }.orEmpty()
        DomainError(
            code = parsed.error.code,
            message = parsed.error.message,
            details = details,
            statusCode = response.status.value,
        )
    } catch (_: SerializationException) {
        fallback
    }
}

