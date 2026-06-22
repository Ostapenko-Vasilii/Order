package ru.orderdorms.features.services.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface CleaningApiService {
    suspend fun fetchCleaningInfo(): CleaningResponse
}

class RemoteCleaningApiService(
    private val httpClient: HttpClient
) : CleaningApiService {
    override suspend fun fetchCleaningInfo(): CleaningResponse {
        // Assuming the endpoint is /cleaning based on the pattern
        return httpClient.get("cleaning").body()
    }
}

class MockCleaningApiService : CleaningApiService {
    override suspend fun fetchCleaningInfo(): CleaningResponse {
        return CleaningResponse(
            info = """
                # График Уборки
                
                Пожалуйста, соблюдайте чистоту в своих комнатах и местах общего пользования.
                
                | День | Этаж | Время |
                | :--- | :--- | :--- |
                | Понедельник | 1-2 | 10:00 - 12:00 |
                | Среда | 3-4 | 14:00 - 16:00 |
                | Пятница | 5 | 09:00 - 11:00 |
                
                **Важно:** В случае отсутствия жильцов уборка не проводится.
            """.trimIndent()
        )
    }
}
