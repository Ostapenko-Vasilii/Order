package ru.orderdorms.features.news.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.orderdorms.core.domain.error.DomainError
import ru.orderdorms.core.domain.functional.Either
import ru.orderdorms.features.news.data.network.model.NewsDto
import ru.orderdorms.features.news.data.network.model.toDomain
import ru.orderdorms.features.news.domain.model.NewsItem
import ru.orderdorms.features.news.domain.model.NewsType
import ru.orderdorms.features.news.domain.repository.NewsRepository
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class NewsRepositoryImpl(
    private val mockDataSource: NewsMockDataSource,
    private val apiDataSource: NewsApiDataSource,
    private val clock: Clock,
    private val useMock: Boolean = true
) : NewsRepository {

    private var cachedNews: List<NewsItem>? = null
    private var lastFetchTime: Instant? = null
    private val cacheDuration = 10.minutes

    override fun getAllNews(): Flow<Either<DomainError, List<NewsItem>>> = flow {
        val now = clock.now()
        val cache = cachedNews
        val last = lastFetchTime

        if (cache != null && last != null && (now - last) < cacheDuration) {
            emit(Either.Right(cache))
        } else {
            try {
                val data = if (useMock) {
                    mockDataSource.getNews()
                } else {
                    apiDataSource.getNews()
                }
                cachedNews = data
                lastFetchTime = now
                emit(Either.Right(data))
            } catch (e: Exception) {
                emit(Either.Left(DomainError(code = "NEWS_FETCH_ERROR", message = e.message ?: "Failed to fetch news")))
            }
        }
    }
}

interface NewsApiDataSource {
    suspend fun getNews(): List<NewsItem>
}

class KtorNewsApiDataSource(
    private val httpClient: HttpClient
) : NewsApiDataSource {
    override suspend fun getNews(): List<NewsItem> {
        val response: List<NewsDto> = httpClient.get("news").body()
        return response.map { it.toDomain() }
    }
}

class NewsMockDataSource(private val clock: Clock) {
    fun getNews(): List<NewsItem> {
        val now = clock.now()
        return listOf(
            NewsItem(
                id = "1",
                title = "Тестирование пожарной тревоги",
                content = "Сегодня в 20:00",
                type = NewsType.URGENT,
                timestamp = now.minus(2.minutes),
                details = "Сегодня в 20:00"
            ),
            NewsItem(
                id = "2",
                title = "Собрание жильцов",
                content = "Сбор на первом этаже, 8 сентября в 18:30",
                type = NewsType.ANNOUNCEMENT,
                timestamp = now.minus(7.minutes),
                details = "Сбор на первом этаже, 8 сентября в 18:30"
            ),
            NewsItem(
                id = "3",
                title = "Спортивный турнир",
                content = "Запись до пятницы, спортзал 28 июня",
                type = NewsType.EVENT,
                timestamp = now.minus(8.minutes),
                details = "Запись до пятницы, спортзал 28 июня"
            ),
            NewsItem(
                id = "4",
                title = "Обновление правил общежития",
                content = "Ознакомьтесь с новым регламентом посещения гостей в базе знаний",
                type = NewsType.ANNOUNCEMENT,
                timestamp = now.minus(24.minutes),
                details = "Ознакомьтесь с новым регламентом посещения гостей в базе знаний"
            ),
            NewsItem(
                id = "5",
                title = "Ваша заявка выполнена",
                content = "Заявка на ремонт крана (№123) была успешно выполнена мастером.",
                type = NewsType.PERSONAL,
                timestamp = now.minus(1.hours),
                details = "Заявка №123"
            )
        )
    }
}
