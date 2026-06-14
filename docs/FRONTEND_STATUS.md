#  Order KMP App — Auth Implementation Status

## Статус реализации

✅ **Frontend (Kotlin KMP):**
- ✅ Network layer (Ktor HttpClient без Authorization)
- ✅ Multiplatform-Settings для хранения токенов
- ✅ Either<DomainError, T> для обработки ошибок
- ✅ AuthApiService со всеми endpoint'ами по ТЗ
- ✅ AuthRepository + usecases с реальными сетевыми вызовами
- ✅ AuthViewModel с интеграцией usecases
- ✅ DI через Koin (NetworkModule, AuthModule)
- ✅ Поддержка платформ: Android, iOS, Wasm/JS
- ✅ BuildKonfig для конфигурации BASE_URL

⏳ **Backend (FastAPI):**
- ⏳ CORS middleware (см. `docs/cors_middleware_fastapi.py`)
- ⏳ Auth endpoints (см. `docs/BACKEND_CHECKLIST.md`)

---

## Архитектура Frontend

```
composeApp/
  ├─ src/commonMain/
  │  ├─ kotlin/ru/orderdorms/di/
  │  │  └─ Koin.kt (инициализация DI)
  │  └─ kotlin/ru/orderdorms/
  │     ├─ App.kt (точка входа)
  │     └─ navigation/RootComponent.kt (root state + auth check)
  │
  ├─ src/androidMain/, iosMain/ (platform-specific)

core/domain/
  ├─ src/commonMain/
  │  ├─ kotlin/ru/orderdorms/core/domain/
  │  │  ├─ functional/Either.kt (Either<L, R>)
  │  │  ├─ error/DomainError.kt (ошибки)
  │  │  └─ auth/AuthRepository.kt (контракт)

core/data/
  ├─ src/commonMain/
  │  ├─ kotlin/ru/orderdorms/core/data/
  │  │  ├─ network/
  │  │  │  ├─ HttpClientFactory.kt (создание Ktor HttpClient)
  │  │  │  ├─ AuthApiService.kt (запросы к auth endpoints)
  │  │  │  ├─ ApiCall.kt (хелпер для JSON парсинга)
  │  │  │  └─ model/ (DTO: AuthDto, ErrorDto и т.д.)
  │  │  ├─ auth/
  │  │  │  ├─ TokenStorage.kt (интерфейс)
  │  │  │  ├─ SettingsTokenStorage.kt (Multiplatform-Settings)
  │  │  │  └─ InMemoryAuthRepository.kt (реализация)
  │  │  └─ di/NetworkModule.kt (DI)
  │  ├─ androidMain/*/ (OkHttp client)
  │  ├─ iosMain/*/ (Darwin HttpEngine)
  │  └─ jsMain/*/ (Js HttpEngine)

features/auth/
  ├─ src/commonMain/
  │  ├─ kotlin/ru/orderdorms/features/auth/
  │  │  ├─ domain/
  │  │  │  ├─ repository/AuthFeatureRepository.kt
  │  │  │  └─ usecase/
  │  │  │     ├─ AuthUseCases.kt (интерфейсы)
  │  │  │     └─ AuthUseCasesImpl.kt (реализация)
  │  │  ├─ data/
  │  │  │  ├─ repository/AuthFeatureRepositoryImpl.kt
  │  │  │  ├─ dto/ (DTO модели)
  │  │  │  └─ mapper/ (DTO -> Domain)
  │  │  ├─ presentation/
  │  │  │  ├─ AuthState.kt (UI state)
  │  │  │  ├─ AuthViewModel.kt (ViewModel с usecase вызовами)
  │  │  │  ├─ AuthNavigation.kt (навигация между экранами)
  │  │  │  ├─ Screens.kt (экраны: EmailInput, CodeInput, PasswordInput и т.д.)
  │  │  │  └─ пакеты отдельных экранов (InviteCodeScreen/, EmailScreen/ и т.д.)
  │  │  └─ di/AuthModule.kt (DI)
```

---

## API Endpoints (по ТЗ)

### Registration Flow
| Endpoint | Method | Payload | Authorization | Ответ |
|----------|--------|---------|---|---------|
| `/api/v1/auth/register/invite-code` | POST | `{"code": "..."}` | — | `{"invite_token": "..."}` |
| `/api/v1/auth/register/mail` | POST | `{"email": "..."}` | Bearer invite_token | `{"temp_token": "...", "next_retry_after": 60}` |
| `/api/v1/auth/send-email-code` | POST | {} | Bearer temp_token | `{"next_retry_after": 60, "message": "..."}` |
| `/api/v1/auth/register/email-code` | POST | `{"code": "..."}` | Bearer temp_token | `{"final_register_token": "..."}` |
| `/api/v1/auth/register/set-password` | POST | `{"password": "..."}` | Bearer final_token | `{"access_token": "...", "refresh_token": "..."}` |

### Login
| Endpoint | Method | Payload | Authorization | Ответ |
|----------|--------|---------|---|---------|
| `/api/v1/auth/login` | POST | `{"email": "...", "password": "..."}` | — | `{"access_token": "...", "refresh_token": "..."}` |

### Forgot Password Flow
| Endpoint | Method | Payload | Authorization | Ответ |
|----------|--------|---------|---|---------|
| `/api/v1/auth/forgot-pass/mail` | POST | `{"email": "..."}` | — | `{"temp_token": "...", "next_retry_after": 60}` |
| `/api/v1/auth/forgot-pass/email-code` | POST | `{"code": "..."}` | Bearer temp_token | `{"reset_token": "..."}` |
| `/api/v1/auth/forgot-pass/set-password` | POST | `{"password": "..."}` | Bearer reset_token | `{}` |

---

## Быстрый старт: Локальная разработка

### 1. Backend (FastAPI)
```bash
# Смотри docs/cors_middleware_fastapi.py
# Добавить CORS middleware и запустить на http://localhost:8000
ENV=development python -m uvicorn main:app --host 0.0.0.0 --port 8000
```

### 2. Frontend (Wasm)
```bash
cd /home/user/StudioProjects/Order
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
# Откроется браузер на http://localhost:XXXX
```

### 3. Frontend (Android)
```bash
./gradlew :androidApp:installDebug
# Запустить на эмуляторе или реальном девайсе
```

---

## Обработка ошибок

### DomainError (маппинг server -> client)
```kotlin
sealed class DomainError {
    data class NetworkError(val apiError: ApiError) : DomainError()
    data class ValidationError(val fieldName: String, val message: String) : DomainError()
    data class ServerError(val code: String, val message: String, val details: Map<String, Any?>? = null) : DomainError()
}

// ApiError (с сервера)
data class ApiError(
    val code: String,           // "INVITE_NOT_FOUND"
    val message: String,        // "Инвайт-код не найден"
    val details: Map<String, Any?>? = null
)
```

### Пример обработки в ViewModel
```kotlin
when (val result = checkInviteUseCase(code)) {
    is Either.Left -> {
        // Ошибка
        state = state.copy(
            error = result.value,  // DomainError
            isLoading = false
        )
    }
    is Either.Right -> {
        // Успех
        state = state.copy(
            inviteToken = result.value.token,
            isLoading = false
        )
        // Переход на следующий экран
    }
}
```

---

## Конфигурация

### BuildKonfig
- `BASE_URL`: `http://localhost:8000` (dev) или production URL
- Генерируется автоматически при сборке
- Смотри `core/data/build.gradle.kts` в секции `buildConfigField`

### Хранение токенов
- **Временные** (invite_token, temp_token, final_token): память приложения
- **Боевые** (access_token, refresh_token): `Multiplatform-Settings` (SharedPreferences на Android, UserDefaults на iOS)

---

## Файлы документации

-  [`docs/cors_middleware_fastapi.py`](docs/cors_middleware_fastapi.py) — готовый CORS код для FastAPI
-  [`docs/BACKEND_CORS_PROMPT.md`](docs/BACKEND_CORS_PROMPT.md) — промпт для LLM
-  [`docs/BACKEND_CHECKLIST.md`](docs/BACKEND_CHECKLIST.md) — чек-лист backend
-  [`docs/LOCAL_DEV_SETUP.md`](docs/LOCAL_DEV_SETUP.md) — инструкция по запуску
-  [`docs/core/data/README.md`](core/data/README.md) — детали сетевого слоя

---

## Дальнейшие шаги (optional)

1. **Refresh Token Logic**: добавить автоматическое обновление `access_token` через `refresh_token`
2. **Error Localization**: маппировать `error.code` на локализованные сообщения (strings res)
3. **Network Interceptor для Authorized API**: добавить `HttpClient` с автоматическим `Authorization` для защищённых endpoint'ов
4. **Integration Tests**: тестировать full flow с фейковым `AuthFeatureRepository`
5. **Secure Token Storage**: для iOS использовать Keychain, для Android — Encrypted SharedPreferences

---

## Контакты и поддержка

- Если frontend не работает — проверь логи в Logcat (Android) или console (Wasm)
- Если backend не работает — смотри `docs/BACKEND_CHECKLIST.md`
- Если CORS error — смотри [`docs/LOCAL_DEV_SETUP.md`](docs/LOCAL_DEV_SETUP.md)

Удачи! 
