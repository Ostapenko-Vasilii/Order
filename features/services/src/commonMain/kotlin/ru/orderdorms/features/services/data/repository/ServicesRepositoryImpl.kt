package ru.orderdorms.features.services.data.repository

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import ru.orderdorms.core.domain.model.Service
import ru.orderdorms.features.services.domain.repository.ServicesRepository
import ru.orderdorms.ui.icons.cleaningIco
import ru.orderdorms.ui.icons.commentIco
import ru.orderdorms.ui.icons.handshakeIco
import ru.orderdorms.ui.icons.paymentsIco
import ru.orderdorms.ui.icons.repairIco

class ServicesRepositoryImpl(
    private val settings: Settings
) : ServicesRepository {

    private val allServices = listOf(
        Service("1", "Оплата Общежития", "Шаблон", paymentsIco, url = "https://pay.urfu.ru/direct"),
        Service("2", "Забронировать место", "Шаблон", handshakeIco),
        Service("3", "График Уборки", "Шаблон", cleaningIco),
        Service("4", "Подать Заявку", "Шаблон", commentIco),
        Service("5", "Подать Жалобу", "Шаблон", repairIco),
        Service("6", "Услуги от проживающих", "Шаблон", handshakeIco),
        Service("7", "База Знаний", "Шаблон", commentIco),
        Service("8", "Барахолка", "Шаблон", paymentsIco),
    )

    private val _quickActionIds = MutableStateFlow(loadQuickActionIds())

    override fun getAllServices(): Flow<List<Service>> = _quickActionIds.map { ids ->
        allServices.map { it.copy(isQuickAction = ids.contains(it.id)) }
    }

    override fun getQuickActions(): Flow<List<Service>> = _quickActionIds.map { ids ->
        allServices.filter { ids.contains(it.id) }
    }

    override suspend fun toggleQuickAction(serviceId: String) {
        val current = _quickActionIds.value.toMutableList()
        if (current.contains(serviceId)) {
            current.remove(serviceId)
        } else {
            if (current.size < 4) {
                current.add(serviceId)
            }
        }
        saveQuickActionIds(current)
        _quickActionIds.value = current
    }

    override suspend fun setQuickActions(serviceIds: List<String>) {
        val limited = serviceIds.take(4)
        saveQuickActionIds(limited)
        _quickActionIds.value = limited
    }

    private fun loadQuickActionIds(): List<String> {
        val saved = settings.getString(KEY_QUICK_ACTIONS, "")
        return if (saved.isEmpty()) {
            listOf("1", "2", "3", "4") // Default
        } else {
            saved.split(",")
        }
    }

    private fun saveQuickActionIds(ids: List<String>) {
        settings.putString(KEY_QUICK_ACTIONS, ids.joinToString(","))
    }

    companion object {
        private const val KEY_QUICK_ACTIONS = "services.quick_actions"
    }
}
