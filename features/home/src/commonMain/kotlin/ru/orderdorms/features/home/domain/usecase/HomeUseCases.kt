package ru.orderdorms.features.home.domain.usecase

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.home.domain.model.HomeDashboard

interface HomeUseCases {
    suspend fun loadDashboard(): Either<DomainError, HomeDashboard>
}
