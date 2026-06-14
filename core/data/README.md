# Core Data Auth Network

Этот модуль реализует сетевой слой авторизации для KMP (android / ios / wasm):

- Ktor `HttpClient` + JSON сериализация
- единая обработка backend ошибок (`{ error: { code, message, details } }`)
- простой `Either` в `core/domain`
- хранение токенов через Multiplatform Settings
- конфигурация `BASE_URL` через BuildKonfig

## Настройка BASE_URL

По умолчанию используется:

- `http://localhost:8000`

Чтобы задать другой backend, передай gradle property `backend.base.url`:

```sh
./gradlew :composeApp:compileKotlinMetadata -Pbackend.base.url=https://api.example.com
```

## Browser / wasm

Browser-target'ы (`js` / `wasmJs`) используют тот же `backend.base.url`, что и остальные платформы.
Для локальной разработки это обычно `http://localhost:8000`.

Если backend опубликован по другому адресу, просто переопредели `backend.base.url` — клиент будет ходить напрямую.

## Основные компоненты

- `core/data/src/commonMain/kotlin/ru/orderdorms/core/data/network/HttpClientFactory.kt`
- `core/data/src/commonMain/kotlin/ru/orderdorms/core/data/network/AuthApiService.kt`
- `core/data/src/commonMain/kotlin/ru/orderdorms/core/data/network/KtorAuthApiService.kt` (реальные HTTP вызовы)
- `core/data/src/commonMain/kotlin/ru/orderdorms/core/data/network/MockAuthApiService.kt` (мок для тестирования без бэка)
- `core/data/src/commonMain/kotlin/ru/orderdorms/core/data/network/ApiCall.kt`
- `core/data/src/commonMain/kotlin/ru/orderdorms/core/data/auth/SettingsTokenStorage.kt`
- `core/data/src/commonMain/kotlin/ru/orderdorms/core/data/di/NetworkModule.kt`

## Mock-Datasource для тестирования без бэка

Модуль включает `MockAuthApiService` для тестирования UI/функционала без запущенного backend'а.

### Включение мок-режима

Отредактируй `core/data/src/commonMain/kotlin/ru/orderdorms/core/data/di/NetworkModule.kt`:

```kotlin
const val USE_MOCK_API = true  // Включить мок-режим
```

Мок-реализация:
- ✅ Имитирует сетевые задержки (200-500ms)
- ✅ Возвращает реалистичные mock-токены
- ✅ Поддерживает различные сценарии (успех, ошибки)
- ✅ Валидирует input данные

### Mock-аккаунт для логина

| Поле | Значение |
|------|----------|
| Email | `test@example.com` |
| Password | `password123` |

### Сценарии тестирования

**Регистрация:**
- Любой код приглашения кроме `INVALID` будет принят
- Email `invalid@test.com` возвращает ошибку `INVALID_EMAIL`
- Email `error@test.com` возвращает ошибку `EMAIL_ALREADY_EXISTS`
- Код верификации `000000` - неверный
- Пароль короче 8 символов - ошибка `WEAK_PASSWORD`

**Восстановление пароля:**
- Код `000000` - неверный
- Пароль короче 8 символов - ошибка `WEAK_PASSWORD`

## Быстрая проверка сборки

```sh
./gradlew :core:data:compileAndroidMain
./gradlew :core:data:compileKotlinIosSimulatorArm64
./gradlew :core:data:compileKotlinWasmJs
```


