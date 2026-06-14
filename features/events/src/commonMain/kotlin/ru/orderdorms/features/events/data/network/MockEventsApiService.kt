package ru.orderdorms.features.events.data.network

import kotlinx.coroutines.delay
import ru.orderdorms.features.events.domain.model.Event

class MockEventsApiService : EventsApiService {
    override suspend fun fetchEvents(): List<Event> {
        delay(500)
        return listOf(
            Event(
                id = "1",
                title = "Кинопоказ в холле",
                date = "24 мая",
                time = "19:00",
                place = "Холл 1 этажа",
                imageUrl = "https://images.unsplash.com/photo-1485846234645-a62644f84728?q=80&w=2059&auto=format&fit=crop",
                descriptionMarkdown = """
                    # Кинопоказ в холле
                    
                    Приглашаем всех на просмотр классики мирового кинематографа!
                    
                    **Что будет:**
                    - Уютная атмосфера
                    - Бесплатный попкорн
                    - Обсуждение после фильма
                    
                    ![Кино](https://images.unsplash.com/photo-1485846234645-a62644f84728?q=80&w=2059&auto=format&fit=crop)
                    
                    Не забудьте взять с собой хорошее настроение и пледы!
                """.trimIndent()
            ),
            Event(
                id = "2",
                title = "Турнир по настольному теннису",
                date = "26 мая",
                time = "16:00",
                place = "Спортивный зал",
                imageUrl = "https://images.unsplash.com/photo-1534158914592-062992fbe900?q=80&w=2099&auto=format&fit=crop",
                descriptionMarkdown = """
                    # Турнир по настольному теннису
                    
                    Проверь свои навыки и поборись за звание чемпиона общежития!
                    
                    **Категории:**
                    - Мужской одиночный разряд
                    - Женский одиночный разряд
                    
                    *Призы победителям гарантированы!*
                """.trimIndent()
            ),
            Event(
                id = "3",
                title = "Мастер-класс по кулинарии",
                date = "28 мая",
                time = "18:00",
                place = "Общая кухня 3 этажа",
                imageUrl = "https://images.unsplash.com/photo-1556910103-1c02745aae4d?q=80&w=2070&auto=format&fit=crop",
                descriptionMarkdown = """
                    # Мастер-класс по кулинарии
                    
                    Учимся готовить быстрые и полезные завтраки студента.
                    
                    **В программе:**
                    1. Идеальная овсянка
                    2. Смузи-боул
                    3. Панкейки без сахара
                    
                    Количество мест ограничено!
                """.trimIndent()
            ),
            Event(
                id = "4",
                title = "Вечер настольных игр",
                date = "30 мая",
                time = "20:00",
                place = "Коворкинг",
                imageUrl = "https://images.unsplash.com/photo-1610890716171-6b1bb98ffd09?q=80&w=2070&auto=format&fit=crop",
                descriptionMarkdown = """
                    # Вечер настольных игр
                    
                    Мафия, Монополия, Кодовые имена и многое другое.
                    
                    Приходи один или с друзьями!
                """.trimIndent()
            ),
            Event(
                id = "5",
                title = "Субботник",
                date = "1 июня",
                time = "10:00",
                place = "Территория общежития",
                imageUrl = "https://images.unsplash.com/photo-1610890716171-6b1bb98ffd09?q=80&w=2070&auto=format&fit=crop",
                descriptionMarkdown = """
                    # Общегородской субботник
                    
                    Сделаем наш дом чище вместе! 
                    Весь инвентарь будет выдан на месте.
                """.trimIndent()
            )
        )
    }
}
