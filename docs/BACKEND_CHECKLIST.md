# ✅ Backend TODO: CORS + фиксы для Order Auth API

## Что нужно сделать на backend (FastAPI)

- [ ] **1. Добавить зависимость**
  ```bash
  pip install fastapi[all]
  # fastapi уже идёт с CORS middleware
  ```

- [ ] **2. Добавить CORS middleware в main.py**
  ```python
  from fastapi.middleware.cors import CORSMiddleware
  
  app = FastAPI()
  
  app.add_middleware(
      CORSMiddleware,
      allow_origins=["http://localhost:5173", "http://localhost:5174", "http://127.0.0.1:5173"],  # браузер
      allow_credentials=True,
      allow_methods=["GET", "POST", "OPTIONS", "DELETE", "PATCH"],
      allow_headers=["Content-Type", "Authorization"],
      max_age=3600,
  )
  ```
  **Важно:** middleware добавляется ПЕРЕД маршрутами!

- [ ] **3. Проверить, что все auth-ручки возвращают JSON в формате:**
  ```json
  {
    "error": {
      "code": "ERROR_CODE",
      "message": "Сообщение",
      "details": {}
    }
  }
  ```
  **или**
  ```json
  {
    "invite_token": "...",
    // или другой успешный ответ по ТЗ
  }
  ```

- [ ] **4. Убедиться, что все ручки отвечают на OPTIONS (автоматически с middleware)**
  - Если нет — добавить `@app.options(...)` для каждого пути

- [ ] **5. Тестовые данные (магические значения по ТЗ)**
  - [ ] `POST /api/v1/auth/register/invite-code`
    - `code == "qwerty"` → 200 OK, `{"invite_token": "mock_invite_token_123"}`
    - `code == "expired"` → 410 Gone, `{"error": {"code": "INVITE_EXPIRED", ...}}`
    - другое → 400 Bad Request, `{"error": {"code": "INVITE_NOT_FOUND", ...}}`

  - [ ] `POST /api/v1/auth/register/mail`
    - Header: `Authorization: Bearer mock_invite_token_123`
    - Email `exist@example.com` → 409 Conflict, `{"error": {"code": "EMAIL_ALREADY_REGISTERED", ...}}`
    - No `@` in email → 400 Bad Request, `{"error": {"code": "INVALID_EMAIL_FORMAT", ...}}`
    - Успех → 200 OK, `{"temp_token": "mock_temp_token_456", "next_retry_after": 60}`

  - [ ] `POST /api/v1/auth/send-email-code`
    - Header: `Authorization: Bearer mock_temp_token_456`
    - Успех → 200 OK, `{"next_retry_after": 60, "message": "..."}`
    - (Опционально) Если запрос < 60 sec → 429 Too Many Requests

  - [ ] `POST /api/v1/auth/register/email-code`
    - Header: `Authorization: Bearer mock_temp_token_456`
    - `code == "123456"` → 200 OK, `{"final_register_token": "mock_final_token_789"}`
    - `code == "000000"` → 410 Gone, `{"error": {"code": "VERIFICATION_CODE_EXPIRED", ...}}`
    - другое → 400 Bad Request

  - [ ] `POST /api/v1/auth/register/set-password`
    - Header: `Authorization: Bearer mock_final_token_789`
    - `password < 6` chars → 400, `{"error": {"code": "PASSWORD_TOO_WEAK", ...}}`
    - Успех → 200 OK, `{"access_token": "mock_access_jwt_abc", "refresh_token": "mock_refresh_jwt_xyz"}`

  - [ ] `POST /api/v1/auth/login`
    - `email == "user@example.com"` && `password == "password123"` → 200 OK
    - Успех → `{"access_token": "...", "refresh_token": "..."}`
    - Иначе → 401 Unauthorized, `{"error": {"code": "BAD_CREDENTIALS", ...}}`

  - [ ] `POST /api/v1/auth/forgot-pass/mail`
    - Email `notfound@example.com` → 404 Not Found
    - Успех (например, `user@example.com`) → 200 OK, `{"temp_token": "...", "next_retry_after": 60}`

  - [ ] `POST /api/v1/auth/forgot-pass/email-code`
    - `code == "123456"` → 200 OK, `{"reset_token": "..."}`
    - Иначе → 410/400

  - [ ] `POST /api/v1/auth/forgot-pass/set-password`
    - Header: `Authorization: Bearer <reset_token>`
    - Успех → 200 OK, `{}`

- [ ] **6. Проверить логирование**
  - Логировать все входящие запросы с методом, path, origin
  - Логировать ошибки с кодом и сообщением

- [ ] **7. Локальный тест**
  ```bash
  # Запустить backend
  ENV=development uvicorn main:app --host 0.0.0.0 --port 8000 --reload
  
  # В другом терминале, тест preflight
  curl -X OPTIONS http://localhost:8000/api/v1/auth/register/invite-code \
    -H "Origin: http://localhost:5173" \
    -H "Access-Control-Request-Method: POST" \
    -v
  
  # Должен вернуться 200 с заголовками:
  # Access-Control-Allow-Origin: http://localhost:5173
  # Access-Control-Allow-Methods: POST, GET, OPTIONS
  ```

---

## Дополнительно (если нужно)

- [ ] Добавить rate limiting (429 на слишком частые запросы)
- [ ] Добавить логирование всех запросов + ошибок
- [ ] Добавить валидацию всех входных данных
- [ ] Test/mock все ошибочные сценарии перед продом

---

## Что передать LLM для быстрого fix-a

Используй промпт из `docs/BACKEND_CORS_PROMPT.md` — скопируй весь текст и передай помощнику типа ChatGPT.
