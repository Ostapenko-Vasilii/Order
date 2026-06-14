# ️ Order – Kotlin Multiplatform Auth App

> **Статус:** Frontend (Kotlin KMP/Compose) готов и интегрирован с API.  
> Backend (FastAPI) в разработке.

---

##  Быстрый обзор

**Проект:** Многоплатформенное приложение авторизации на Kotlin (Android, iOS, Web/Wasm).

**Стек Frontend:**
- **Lang:** Kotlin KMP
- **UI:** Jetpack Compose Multiplatform
- **Network:** Ktor (HTTP client)
- **DI:** Koin
- **Storage:** Multiplatform-Settings
- **Error Handling:** Either<DomainError, T> (homemade)
- **BuildConfig:** BuildKonfig для BASE_URL

**Стек Backend (в разработке):**
- **Lang:** Python
- **Framework:** FastAPI
- **Serialization:** Pydantic

**Платформы:**
- ✅ Android (Gradle: `:androidApp:installDebug`)
- ✅ iOS (Xcode: `:iosApp:...`)
- ✅ Web/Wasm (Gradle: `:composeApp:wasmJsBrowserDevelopmentRun`)
- ✅ Web/JS (Gradle: `:composeApp:jsBrowserDevelopmentRun`)

---

##  Быстрый старт

### 1. Clone & Setup Frontend

```bash
cd /home/user/StudioProjects/Order

# Sync Gradle
./gradlew sync

# Опционально: обновить Yarn lock для Wasm
./gradlew :kotlinWasmUpgradeYarnLock
```

### 2. Запустить Frontend (выбри один)

**Wasm (браузер, рекомендуется):**
```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

**JS (браузер):**
```bash
./gradlew :composeApp:jsBrowserDevelopmentRun
```

**Android:**
```bash
./gradlew :androidApp:installDebug
```

### 3. Запустить Backend (FastAPI)

```bash
# Клонировать/создать backend-проект (если нет)
cd /path/to/backend

# Setup
python -m venv venv
source venv/bin/activate  # или venv\Scripts\activate
pip install -r requirements.txt

# Добавить CORS (+смотри docs/cors_middleware_fastapi.py)
# (вставить в main.py)

# Запустить
ENV=development python -m uvicorn main:app --host 0.0.0.0 --port 8000 --reload
```

Backend будет на http://localhost:8000  
Frontend ожидает backend именно там (BuildKonfig: `BASE_URL=http://localhost:8000`)

---

##  Структура проекта

```
/Order
├── composeApp/              # Основное KMP приложение (Compose)
│   ├── src/
│   │   ├── commonMain/      # Общий код (Android, iOS, Wasm, JS)
│   │   ├── androidMain/     # Android-специфичный код
│   │   ├── iosMain/         # iOS-специфичный код
│   │   ├── jsMain/          # JS-специфичный код
│   │   └── wasmJsMain/      # Wasm-специфичный код
│   └── build.gradle.kts
│
├── androidApp/              # Android entrypoint (APK)
│   ├── src/main/
│   └── build.gradle.kts
│
├── iosApp/                  # iOS entrypoint (Xcode)
│   ├── iosApp/
│   │   ├── iOSApp.swift
│   │   └── ...
│   └── iosApp.xcodeproj/
│
├── core/                    # Shared core modules
│   ├── domain/              # Domain layer (entities, repositories contracts, use cases)
│   ├── data/                # Data layer (network, storage, repositories impl)
│   │   ├── network/         # HTTP client, API services
│   │   ├── auth/            # Token storage impl
│   │   └── di/              # DI modules
│   └── ui/                  # Common UI components
│
├── features/                # Feature modules
│   └── auth/                # Auth feature
│       ├── domain/          # Auth contracts (repositories, use cases)
│       ├── data/            # Auth impl (repositories, DTOs)
│       ├── presentation/    # Auth UI (ViewModel, Screens)
│       └── di/              # Auth DI
│
├── docs/                    #  Документация
│   ├── cors_middleware_fastapi.py       # CORS пример
│   ├── BACKEND_CHECKLIST.md             # Чек-лист backend
│   ├── BACKEND_CORS_PROMPT.md           # CORS промпт
│   ├── BACKEND_PROMPT_FOR_LLM.md        # Главный промпт для backend
│   ├── LOCAL_DEV_SETUP.md               # Локальный dev setup
│   ├── FRONTEND_STATUS.md               # Статус фронтенда
│   └── ...
│
├── scripts/
│   └── dev-launcher.sh      # Скрипт для быстрого запуска
│
├── gradle/
│   ├── libs.versions.toml   # Version catalog (зависимости)
│   └── wrapper/
│
├── build-logic/             # Gradle convention plugins
│   └── convention/
│
├── build.gradle.kts         # Root build script
├── settings.gradle.kts      # Root settings
└── gradlew / gradlew.bat    # Gradle wrapper
```

---

##  API Endpoints (Frontend ↔️ Backend)

Все endpoints доступны по `BASE_URL/api/v1/auth/...`

| Endpoint | Method | Auth | Описание |
|----------|--------|------|---------|
| `register/invite-code` | POST | — | Проверка инвайт-кода |
| `register/mail` | POST | invite_token | Ввод email (регистрация) |
| `send-email-code` | POST | temp_token | Повторная отправка кода |
| `register/email-code` | POST | temp_token | Проверка кода из почты |
| `register/set-password` | POST | final_token | Установка пароля (окончание регистрации) |
| `login` | POST | — | Вход (email + password) |
| `forgot-pass/mail` | POST | — | Восстановление: отправка кода на почту |
| `forgot-pass/email-code` | POST | temp_token | Проверка кода восстановления |
| `forgot-pass/set-password` | POST | reset_token | Установка нового пароля |

**Полный формат:** смотри [`docs/FRONTEND_STATUS.md`](docs/FRONTEND_STATUS.md) → API Endpoints

---

## ⚙️ Конфигурация

### BuildKonfig (Frontend)
Задаётся в `core/data/build.gradle.kts` и `composeApp/build.gradle.kts`:
- `BASE_URL`: backend URL (dev: `http://localhost:8000`, prod: твой домен)

Генерируется при сборке → доступен через `ru.orderdorms.BuildKonfig.BASE_URL` в коде.

### Gradle

```bash
# Просмотр всех доступных tasks
./gradlew :composeApp:tasks --all

# Очистка
./gradlew clean

# Полная сборка
./gradlew build

# Без сборки (только sync)
./gradlew sync
```

---

##  Тестирование

### Frontend Tests

```bash
# Multiplatform unit tests
./gradlew :composeApp:commonMainTests

# JVM tests
./gradlew :composeApp:jvmTest

# Wasm tests
./gradlew :composeApp:wasmJsTest
```

### Backend Tests (после реализации)

```bash
# FastAPI
pytest
pytest --cov=.
```

---

##  Документация

| Документ | Для кого | Описание |
|----------|----------|---------|
| [`FRONTEND_STATUS.md`](docs/FRONTEND_STATUS.md) | Frontend разработчик | Статус, архитектура, endpoints |
| [`BACKEND_PROMPT_FOR_LLM.md`](docs/BACKEND_PROMPT_FOR_LLM.md) | Backend разработчик / LLM | Готовый промпт для backend |
| [`BACKEND_CHECKLIST.md`](docs/BACKEND_CHECKLIST.md) | Backend разработчик | Чек-лист реализации |
| [`LOCAL_DEV_SETUP.md`](docs/LOCAL_DEV_SETUP.md) | Developer (любой) | Локальный dev setup |
| [`cors_middleware_fastapi.py`](docs/cors_middleware_fastapi.py) | Backend разработчик | CORS middleware код |
| [`BACKEND_CORS_PROMPT.md`](docs/BACKEND_CORS_PROMPT.md) | Backend разработчик / LLM | CORS промпт |
| [`core/data/README.md`](core/data/README.md) | Frontend разработчик | Детали сетевого слоя |

---

##  Основные компоненты

### Frontend: Auth Flow  
```
App (RootComponent)
  ├─ Koin DI инициализирован
  └─ AuthViewModel
      ├─ useCheckInvite()
      ├─ useSendEmail()
      ├─ useVerifyEmailCode()
      ├─ useSetPassword()
      ├─ useLogin()
      └─ ... (forgot-pass usecases)
          ↓
      AuthRepositoryImpl (использует AuthApiService)
          ↓
      AuthApiService (Ktor HttpClient)
          ↓
      Backend (FastAPI)
```

### Error Handling
```
Backend returns: { "error": { "code": "INVITE_NOT_FOUND", "message": "...", "details": {} } }
       ↓ (парсинг)
ApiError(code, message, details)
       ↓ (маппинг)
DomainError.ServerError
       ↓ (обработка в ViewModel)
UI.errorMessage = "Инвайт-код не найден"
```

---

##  Токены и Storage

- **invite_token, temp_token, final_token:** память приложения (временные)
- **access_token, refresh_token:** Multiplatform-Settings (постоянное хранилище)

На Android: SharedPreferences  
На iOS: UserDefaults  
На Wasm/JS: localStorage

---

##  Типичные проблемы

| Проблема | Решение |
|----------|---------|
| 405 Method Not Allowed на OPTIONS | Добавить CORS middleware на backend |
| CORS error в DevTools | Проверить allow_origins и allow_headers в CORS |
| компиляция не работает | `./gradlew clean && ./gradlew sync` |
| Wasm lock файл изменился | `./gradlew :kotlinWasmUpgradeYarnLock` |
| Backend не запускается | Проверить Python версию, зависимости, port 8000 свободен |

---

##  Как использовать Backend Prompt

Если нужно быстро создать backend:

```bash
# 1. Открой docs/BACKEND_PROMPT_FOR_LLM.md
# 2. Скопируй весь текст из раздела "ЗАДАЧА ДЛЯ LLM"
# 3. Вставь в ChatGPT / Claude / твой LLM
# 4. Добавь запрос: "Создай полный, готовый к запуску код"
# 5. Готово!
```

Или используй готовый пример:
```bash
# docs/cors_middleware_fastapi.py + свой основной код backend
```

---

##  Next Steps

- [ ] Реализовать backend (FastAPI) с auth endpoints
- [ ] Добавить CORS middleware в backend
- [ ] Проверить локальный dev-flow: frontend ↔️ backend
- [ ] Добавить refresh token logic
- [ ] Добавить secure token storage (Keychain для iOS, Encrypted SharedPreferences для Android)
- [ ] Добавить error localization (i18n)
- [ ] Добавить network interceptor для авторизованных запросов
- [ ] Добавить integration tests
- [ ] Deploy на production (build release APK, iOS ipa, web bundle)

---

##  Support

- **Frontend Issues:** смотри logs в Logcat (Android) или DevTools (Wasm)
- **Backend Issues:** смотри [`BACKEND_CHECKLIST.md`](docs/BACKEND_CHECKLIST.md)
- **CORS Issues:** смотри [`LOCAL_DEV_SETUP.md`](docs/LOCAL_DEV_SETUP.md) → Проверка CORS

---

##  Лицензия

[ADD YOUR LICENSE HERE]

---

Удачи в разработке! 
