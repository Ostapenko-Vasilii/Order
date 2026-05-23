package ru.orderdorms.features.home.domain.usecase

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.home.domain.model.HomeDashboard
import ru.orderdorms.features.home.domain.repository.HomeRepository
import ru.orderdorms.features.home.domain.repository.QuickActionsRepository

class HomeUseCasesImpl(
    private val repository: HomeRepository,
    private val quickActionsRepository: QuickActionsRepository
) : HomeUseCases {
    override suspend fun loadDashboard(): Either<DomainError, HomeDashboard> {
        return repository.loadDashboard().fold(
            left = { Either.Left(it) },
            right = { dashboard ->
                Either.Right(dashboard.copy(quickActions = quickActionsRepository.getQuickActions()))
            }
        )
    }
}
