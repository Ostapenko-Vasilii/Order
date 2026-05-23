package ru.orderdorms.features.home.data.repository

import com.russhwolf.settings.Settings
import ru.orderdorms.features.home.domain.model.Service
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
        Service(id = "1", title = "Оплата", icon = paymentsIco, url = "https://pay.urfu.ru/direct"),
        Service(id = "2", title = "Уборка", icon = cleaningIco),
        Service(id = "3", title = "Бронирование", icon = handshakeIco),
        Service(id = "4", title = "Заявка", icon = commentIco),
        Service(id = "5", title = "Жалоба", icon = repairIco),
    )

    override fun getQuickActions(): List<Service> {
        val saved = settings.getString("services.quick_actions", "")
        if (saved.isEmpty()) return allServices.take(4)
        
        val ids = saved.split(",")
        return allServices.filter { ids.contains(it.id) }
    }
}
