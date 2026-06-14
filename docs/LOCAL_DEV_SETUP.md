#  Local Development Setup: Order Frontend + Auth Backend

## Быстрый старт

### 1. Запуск Backend (FastAPI + Auth API)

```bash
# Перейти в папку backend-проекта
cd /path/to/order-auth-backend

# (Опция A) На Python с env
python -m venv venv
source venv/bin/activate  # или venv\Scripts\activate на Windows
pip install -r requirements.txt

# Добавить CORS middleware (если не добавлено)
# Копируешь код из docs/cors_middleware_fastapi.py в твой main.py

# Запустить backend
# Убедись, что CORS middleware добавлен и ENV=development
ENV=development python main.py
# или с uvicorn напрямую
uvicorn main:app --host 0.0.0.0 --port 8000 --reload

# Backend будет доступен на http://localhost:8000
```

### 2. Запуск Frontend (Order KMP App)

Выбери нужный target:

#### Wasm (браузер, рекомендуется для dev)
```bash
cd /home/user/StudioProjects/Order
./gradlew :composeApp:wasmJsBrowserDevelopmentRun --no-daemon
```
- Откроется браузер на `http://localhost:XXXX` (порт выберет Gradle)

#### JS (альтернатива wasm)
```bash
./gradlew :composeApp:jsBrowserDevelopmentRun --no-daemon
```

#### Android (требует эмулятор/device)
```bash
./gradlew :androidApp:installDebug
```
- Затем запустить приложение на эмуляторе

---

## Ожидаемое поведение

### Frontend (браузер) → Backend (localhost:8000)
1. Frontend на `http://localhost:5173` (или другой порт, выбранный webpack)
2. Backend на `http://localhost:8000`
3. Frontend отправляет POST /api/v1/auth/register/invite-code с телом `{"code":"qwerty"}`
4. Браузер автоматически отправляет preflight OPTIONS
5. Backend отвечает с CORS-заголовками
6. Frontend получает успешный ответ 200

## Проверка CORS

### В браузере (DevTools)
```
1. Открыть DevTools (F12)
2. Вкладка Network
3. Отправить запрос на логин/регистрацию
4. Найти запрос в Network tab
5. Посмотреть Response Headers:
   - Access-Control-Allow-Origin: http://localhost:5173
   - Access-Control-Allow-Methods: POST, GET, OPTIONS
   - Access-Control-Allow-Headers: Content-Type, Authorization
```

### Через curl
```bash
# Preflight запрос
curl -X OPTIONS http://localhost:8000/api/v1/auth/register/invite-code \
  -H "Origin: http://localhost:5173" \
  -H "Access-Control-Request-Method: POST" \
  -H "Access-Control-Request-Headers: content-type" \
  -v

# Должен вернуться 200 с CORS-заголовками

# Реальный запрос
curl -X POST http://localhost:8000/api/v1/auth/register/invite-code \
  -H "Content-Type: application/json" \
  -d '{"code":"qwerty"}' \
  -v
```

---

## Конфигурация

### BuildKonfig (сторона фронтенда - Order app)
`composeApp/build.gradle.kts` уже настроена на `BASE_URL = "http://localhost:8000"` для локальной разработки.

### Backend CORS (сторона backend)
Смотри `docs/cors_middleware_fastapi.py` или `docs/BACKEND_CORS_PROMPT.md` для полных инструкций.

---

## Типовые ошибки и решения

| Ошибка | Причина | Решение |
|--------|---------|---------|
| 405 Method Not Allowed на OPTIONS | Backend не разрешает OPTIONS | Добавить CORS middleware с allow_methods=["OPTIONS", "POST", ...] |
| CORS error в консоли браузера | Origin не в whitelist | Проверить allow_origins на backend включает хост браузера |
| ERR_NETWORK_CHANGED / timeout | Сеть недоступна или firewall | Проверить: backend запущен, firewall не блокирует 8000 |
| 401 Unauthorized | Неверные kredentialy / токен | Проверить логику авторизации на backend и маппинг токенов в app |

---

## Структура auth flow (для справки)

```
Frontend (KMP/Compose)
  ↓ (Button "Register")
AuthViewModel.checkInvite(code)
  ↓
AuthUseCaseImpl.checkInvite()
  ↓
AuthRepositoryImpl.checkInvite()
  ↓
AuthApiService.checkInviteCode()
  ↓
Ktor HttpClient.post("/api/v1/auth/register/invite-code")
  ↓ (preflight OPTIONS запрос)
  ↓ (реальный POST запрос)
Backend (FastAPI)
  ↓
CORS middleware (разрешает запрос, проверяет origin)
  ↓
Route handler: POST /api/v1/auth/register/invite-code
  ↓
Логика: проверить код, вернуть invite_token
  ↓
Frontend получает Either.Right(InviteToken)
  ↓
UI обновляется: переход на экран ввода email
```

---

## Дополнительно

- Если нужна HTTPS локально: используй self-signed cert + добавь в браузер как исключение
- Если нужен тот же сервер на разных машинах: замени localhost на IP машины (например, 192.168.x.x)
- Для production deployment: смотри конфиг BASE_URL в BuildKonfig и whitelist origin'ов на backend

---

Готово! Если что-то не работает, дай полный вывод ошибки из браузера или backend логов.
