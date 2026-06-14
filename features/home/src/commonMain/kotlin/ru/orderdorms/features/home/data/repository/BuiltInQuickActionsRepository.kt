package ru.orderdorms.features.home.data.repository

import com.russhwolf.settings.Settings
import ru.orderdorms.core.domain.model.Service
import ru.orderdorms.features.home.domain.repository.QuickActionsRepository
import ru.orderdorms.ui.icons.cleaningIco
import ru.orderdorms.ui.icons.commentIco
import ru.orderdorms.ui.icons.handshakeIco
import ru.orderdorms.ui.icons.paymentsIco
import ru.orderdorms.ui.icons.repairIco

class BuiltInQuickActionsRepository(
    private val settings: Settings
) : QuickActionsRepository {

    private val allServices = listOf(
        Service(id = "1", title = "Оплата", description = "Шаблон", icon = paymentsIco, url = "https://pay.urfu.ru/direct"),
        Service(id = "2", title = "Уборка", description = "Шаблон", icon = cleaningIco),
        Service(id = "3", title = "Бронирование", description = "Шаблон", icon = handshakeIco),
        Service(id = "4", title = "Заявка", description = "Шаблон", icon = commentIco),
        Service(id = "5", title = "Жалоба", description = "Шаблон", icon = repairIco),
        Service(id = "6", title = "Услуги от проживающих", description = "Шаблон", icon = handshakeIco),
        Service(id = "7", title = "База Знаний", description = "Шаблон", icon = commentIco),
        Service(id = "8", title = "Барахолка", description = "Шаблон", icon = paymentsIco),
    )

    override fun getQuickActions(): List<Service> {
        val saved = settings.getString("services.quick_actions", "")
        if (saved.isEmpty()) return allServices.take(4)

        val ids = saved.split(",")
        return allServices.filter { ids.contains(it.id) }
    }
}