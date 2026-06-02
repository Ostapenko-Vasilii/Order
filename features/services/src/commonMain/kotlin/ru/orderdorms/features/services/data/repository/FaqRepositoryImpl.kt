package ru.orderdorms.features.services.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.orderdorms.features.services.domain.model.faq.FaqCategory
import ru.orderdorms.features.services.domain.model.faq.FaqQuestion
import ru.orderdorms.features.services.domain.repository.FaqRepository
import ru.orderdorms.ui.icons.dormIco
import kotlin.time.Duration.Companion.minutes

class FaqRepositoryImpl(
    private val mockDataSource: FaqMockDataSource,
    private val apiDataSource: FaqApiDataSource,
    private val useMock: Boolean = true
) : FaqRepository {

    private var cachedData: List<FaqCategory>? = null
    private var lastFetchTime: Instant? = null
    private val cacheDuration = 30.minutes

    override fun getFaqCategories(): Flow<List<FaqCategory>> = flow {
        val now = Clock.System.now()
        val cache = cachedData
        val last = lastFetchTime
        
        if (cache != null && last != null && (now - last) < cacheDuration) {
            emit(cache)
        } else {
            val data = if (useMock) {
                mockDataSource.getCategories()
            } else {
                apiDataSource.getCategories()
            }
            cachedData = data
            lastFetchTime = now
            emit(data)
        }
    }
}

interface FaqApiDataSource {
    suspend fun getCategories(): List<FaqCategory>
}

class KtorFaqApiDataSource : FaqApiDataSource {
    override suspend fun getCategories(): List<FaqCategory> {
        return emptyList()
    }
}

class FaqMockDataSource {
    fun getCategories(): List<FaqCategory> = listOf(
        FaqCategory(
            id = "1",
            title = "Общие вопросы",
            icon = dormIco,
            questions = listOf(
                FaqQuestion(
                    id = "q1",
                    title = "Как оплатить проживание?",
                    answerMarkdown = """
                        # Оплата проживания
                        
                        Вы можете оплатить проживание несколькими способами:
                        
                        1. **Через мобильное приложение** (раздел Оплата).
                        2. **В терминалах самообслуживания** в холле.
                        3. **Банковским переводом** по реквизитам.
                        
                        > [!IMPORTANT]
                        > Оплата должна быть произведена до 10 числа каждого месяца.
                        
                        ![Иллюстрация](https://picsum.photos/400/200)
                    """.trimIndent()
                ),
                FaqQuestion(
                    id = "q2",
                    title = "Правила внутреннего распорядка",
                    answerMarkdown = """
                        ### Правила проживания
                        
                        - Соблюдать тишину с 23:00 до 07:00.
                        - Содержать комнату в чистоте.
                        - Бережно относиться к имуществу.
                        
                        *Подробнее читайте в договоре найма.*
                    """.trimIndent()
                )
            )
        ),
        FaqCategory(
            id = "2",
            title = "Бытовые услуги",
            icon = dormIco,
            questions = listOf(
                FaqQuestion(
                    id = "q3",
                    title = "График работы прачечной",
                    answerMarkdown = "Прачечная работает **ежедневно с 08:00 до 22:00**. Перерыв с 13:00 до 14:00."
                )
            )
        )
    )
}
