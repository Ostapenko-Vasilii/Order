package ru.orderdorms.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val json: Json,
) {
    fun createAnonymous(): HttpClient = baseClient()

    fun createFinalAuthorized(tokenProvider: () -> String?): HttpClient = baseClient {
        val token = tokenProvider()
        if (!token.isNullOrBlank()) {
            header(HttpHeaders.Authorization, "Bearer $token")
        }
    }

    private fun baseClient(
        extraHeaders: io.ktor.client.plugins.DefaultRequest.DefaultRequestBuilder.() -> Unit = {},
    ): HttpClient {
        return HttpClient {
            expectSuccess = false

            install(ContentNegotiation) {
                json(json)
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) = Unit
                }
                level = LogLevel.INFO
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                extraHeaders()
            }
        }
    }
}


