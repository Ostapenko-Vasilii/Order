package ru.orderdorms.features.home.data.repository

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.home.domain.model.HomeDashboard
import ru.orderdorms.features.home.domain.model.News
import ru.orderdorms.features.home.domain.repository.HomeRepository
import ru.orderdorms.ui.icons.priorityHighIco
import ru.orderdorms.ui.icons.priorityInfoIco
import kotlin.collections.emptyList
import kotlin.collections.listOf

class HomeRepositoryImpl : HomeRepository {
    override suspend fun loadDashboard(): Either<DomainError, HomeDashboard> =
        Either.Right(
            HomeDashboard(
                userName = "Имя",
                events = emptyList(),
                importantNews = listOf(
                    News(
                        id = "1",
                        title = "Шаблон названия новости",
                        subtitle = "Шаблон текста новости",
                        icon = priorityHighIco
                    ),
                    News(
                        id = "2",
                        title = "Шаблон названия новости",
                        subtitle = "Шаблон текста новости",
                        icon = priorityInfoIco
                    )
                ),
                quickActions = emptyList()
            )
        )
}
