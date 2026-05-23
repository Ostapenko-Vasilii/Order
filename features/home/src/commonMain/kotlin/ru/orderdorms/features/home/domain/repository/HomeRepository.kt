package ru.orderdorms.features.home.domain.repository

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.home.domain.model.HomeDashboard

interface HomeRepository {
    suspend fun loadDashboard(): Either<DomainError, HomeDashboard>
}
