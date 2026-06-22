package ru.orderdorms.features.services.data.repository

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.services.data.network.CleaningApiService
import ru.orderdorms.features.services.domain.repository.CleaningRepository

class CleaningRepositoryImpl(
    private val apiService: CleaningApiService
) : CleaningRepository {
    override suspend fun getCleaningInfo(): Either<DomainError, String> {
        return try {
            val response = apiService.fetchCleaningInfo()
            Either.Right(response.info)
        } catch (e: Exception) {
            Either.Left(DomainError(code = "CLEANING_FETCH_ERROR", message = e.message ?: "Failed to fetch cleaning info"))
        }
    }
}
