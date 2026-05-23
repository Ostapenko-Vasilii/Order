package ru.orderdorms.features.home.data.repository

import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.home.domain.model.Action
import ru.orderdorms.features.home.domain.model.Event
import ru.orderdorms.features.home.domain.model.HomeDashboard
import ru.orderdorms.features.home.domain.model.News
import ru.orderdorms.features.home.domain.repository.HomeRepository
import ru.orderdorms.ui.icons.cleaningIco
import ru.orderdorms.ui.icons.commentIco
import ru.orderdorms.ui.icons.handshakeIco
import ru.orderdorms.ui.icons.paymentsIco
import ru.orderdorms.ui.icons.priorityHighIco
import ru.orderdorms.ui.icons.priorityInfoIco

class HomeRepositoryImpl : HomeRepository {
    override suspend fun loadDashboard(): Either<DomainError, HomeDashboard> =
        Either.Right(
            HomeDashboard(
                userName = "Имя",
                events = listOf(
                    Event(
                        id = "1",
                        title = "Название события",
                        date = "Время, дата",
                        imageUrl = null
                    ),
                    Event(
                        id = "2",
                        title = "Название события",
                        date = "Время, дата",
                        imageUrl = null
                    )
                ),
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
                // quickActions moved to features/services (built-in). Home dashboard no longer receives them from network.
                quickActions = emptyList()
            )
        )
}
