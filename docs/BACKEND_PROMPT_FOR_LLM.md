# 🤖 Промпт для быстрой реализации Backend (передай это LLM или используй как инструкцию)

## Контекст: Order Auth API Backend

Нужна FastAPI application с auth endpoints + CORS для браузерного клиента (Kotlin KMP + Compose).

**Стек:** Python + FastAPI + Pydantic

**Цель:** Создать мок-бэкенд, который имитирует авторизацию и работает с браузерным клиентом (Wasm/JS) на локальной машине.

---

## ЗАДАЧА ДЛЯ LLM

```
Ты — backend-разработчик на Python/FastAPI.

Задача: Создать Auth API backend согласно техническому заданию.

Требования:
1. FastAPI приложение на Python.
2. Все endpoints возвращают JSON.
3. Все ошибки в формате: { "error": { "code": "STRING", "message": "TEXT", "details": {} } }
4. Все успешные ответы — специфичные JSON по ТЗ.
5. CORS middleware для браузера (разрешить localhost:5173, localhost:8080, 127.0.0.1:*).
6. Сеть имитируется задержкой 500–1000 мс на каждый endpoint.

Endpoints:

### 1️⃣ POST /api/v1/auth/register/invite-code
Body: {"code": "string"}
Response 200: {"invite_token": "mock_invite_token_123"}
Response 400: {"error": {"code": "INVITE_NOT_FOUND", "message": "Инвайт-код не найден"}}
Response 410: {"error": {"code": "INVITE_EXPIRED", "message": "Срок действия инвайт-кода истёк"}}
Logic:
  - code == "qwerty" ➜ 200
  - code == "expired" ➜ 410
  - любой другой ➜ 400

### 2️⃣ POST /api/v1/auth/register/mail
Headers: Authorization: Bearer mock_invite_token_123
Body: {"email": "user@example.com"}
Response 200: {"temp_token": "mock_temp_token_456", "next_retry_after": 60}
Response 400: {"error": {"code": "INVALID_EMAIL_FORMAT", "message": "Неверный формат email"}}
Response 401: {"error": {"code": "UNAUTHORIZED", "message": "Неверный токен"}}
Response 409: {"error": {"code": "EMAIL_ALREADY_REGISTERED", "message": "Email уже зарегистрирован"}}
Logic:
  - header Authorization != Bearer mock_invite_token_123 ➜ 401
  - email not contains "@" ➜ 400
  - email == "exist@example.com" ➜ 409
  - иначе ➜ 200, сохранить temp_token в памяти

### 3️⃣ POST /api/v1/auth/send-email-code
Headers: Authorization: Bearer mock_temp_token_456
Body: {}
Response 200: {"next_retry_after": 60, "message": "Второе письмо"}
Response 429: {"error": {"code": "TOO_MANY_REQUESTS", "message": "Слишком много запросов", "details": {"retry_after": 45}}}
Response 401: {"error": {"code": "UNAUTHORIZED", "message": "Неверный токен"}}

### 4️⃣ POST /api/v1/auth/register/email-code
Headers: Authorization: Bearer mock_temp_token_456
Body: {"code": "string"}
Response 200: {"final_register_token": "mock_final_token_789"}
Response 400: {"error": {"code": "INVALID_VERIFICATION_CODE", "message": "Неверный код"}}
Response 410: {"error": {"code": "VERIFICATION_CODE_EXPIRED", "message": "Код истёк"}}
Logic:
  - code == "123456" ➜ 200
  - code == "000000" ➜ 410
  - любой другой ➜ 400

### 5️⃣ POST /api/v1/auth/register/set-password
Headers: Authorization: Bearer mock_final_token_789
Body: {"password": "string"}
Response 200: {"access_token": "mock_access_jwt_abc", "refresh_token": "mock_refresh_jwt_xyz"}
Response 400: {"error": {"code": "PASSWORD_TOO_WEAK", "message": "Пароль слишком короткий"}}
Logic:
  - len(password) < 6 ➜ 400
  - иначе ➜ 200

### 6️⃣ POST /api/v1/auth/login
Body: {"email": "string", "password": "string"}
Response 200: {"access_token": "mock_access_jwt_abc", "refresh_token": "mock_refresh_jwt_xyz"}
Response 401: {"error": {"code": "BAD_CREDENTIALS", "message": "Неверный email или пароль"}}
Logic:
  - email == "user@example.com" AND password == "password123" ➜ 200
  - иначе ➜ 401

### 7️⃣ POST /api/v1/auth/forgot-pass/mail
Body: {"email": "string"}
Response 200: {"temp_token": "mock_forgot_temp_token", "next_retry_after": 60}
Response 404: {"error": {"code": "EMAIL_NOT_FOUND", "message": "Email не найден"}}
Logic:
  - email == "notfound@example.com" ➜ 404
  - иначе ➜ 200, сохранить temp_token

### 8️⃣ POST /api/v1/auth/forgot-pass/email-code
Headers: Authorization: Bearer mock_forgot_temp_token
Body: {"code": "string"}
Response 200: {"reset_token": "mock_reset_token"}
Response 400, 410: как в пункте 4️⃣

### 9️⃣ POST /api/v1/auth/forgot-pass/set-password
Headers: Authorization: Bearer mock_reset_token
Body: {"password": "string"}
Response 200: {}
Response 400: как в пункте 5️⃣

---

## Реквизиты:
- Используй asyncio + uvicorn
- Пиши типизированный код с Pydantic models
- Добавь CORS middleware (CORSMiddleware из fastapi.middleware.cors)
- Для сеси хранения токенов используй обычные dict/в памяти
- Ко всем responses добавить имитацию delay 500–1000 мс
- В логах печатай все request/response трансакции

## Результат:
1. Файл main.py (или структура проекта) с готовым FastAPI app
2. Все endpoints работают по ТЗ
3. CORS включён (проверить через curl -X OPTIONS)
4. Запускается: python main.py или uvicorn main:app --reload
5. Доступен на http://localhost:8000
```

---

## Как использовать этот промпт

1. **Копируй весь текст выше** (от "Ты — backend-разработчик" до конца)

2. **Открой ChatGPT / Claude / своего LLM-помощника**

3. **Вставь текст и добавь:**
   ```
   Создай полный, работающий код FileStructure:
   - main.py (entry point)
   - requirements.txt (зависимости)
   - Готово к запуску одной командой
   
   Ответь на код — я буду копировать и запускать.
   ```

4. **Готово!** LLM создаст backend, который работает с твоим фронтенд-приложением.

---

## Альтернатива: быстрый вариант (если LLM долго обрабатывает)

Используй готовый пример:
```bash
# Перейти в папку backend
cd /path/to/backend

# Скопировать пример из docs/cors_middleware_fastapi.py
# в твой main.py

# Запустить
python main.py
```

---

## Проверка (после создания backend)

```bash
# 1. Backend работает?
curl http://localhost:8000/api/v1/auth/register/invite-code \
  -X POST -H "Content-Type: application/json" \
  -d '{"code":"qwerty"}' \
  -v

# Ожидается 200 с ответом: {"invite_token": "..."}

# 2. CORS работает?
curl -X OPTIONS http://localhost:8000/api/v1/auth/register/invite-code \
  -H "Origin: http://localhost:5173" \
  -H "Access-Control-Request-Method: POST" \
  -v

# Ожидается 200 с Access-Control-Allow-* headers

# 3. Все endpoints доступны?
curl http://localhost:8000/docs
# Должна открыться Swagger UI с документацией
```

---

## Если что-то не работает

1. **Читай ошибку в консоли** backend — там будет точное описание
2. **Смотри DevTools браузера** (Network tab) для frontend-части
3. **Проверь:** backend на 8000, frontend на 5173+ (разные origin = нужен CORS)

Готово! Дай этот промпт своему backend-разработчику или LLM-помощнику.

