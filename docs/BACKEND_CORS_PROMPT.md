#  ПРОМПТ: Добавление CORS в FastAPI Auth Backend

# ============ ДЛЯ LLM / AI-помощника ============
# Передай этот текст помощнику типа ChatGPT, Claude или Copilot

"""
Задача: Добавить CORS (Cross-Origin Resource Sharing) middleware в FastAPI приложение для авторизации (Order Auth API).

Требования:
1. Приложение работает на http://localhost:8000.
2. Frontend (браузер) может быть на http://localhost:5173, http://localhost:3000 или других портах в dev.
3. В production frontend может быть на другом домене (например, https://app.example.com).
4. Необходимо разрешить запросы с методами: POST, GET, OPTIONS.
5. Необходимо разрешить заголовки: Content-Type, Authorization, Accept.
6. Приложение должно корректно отвечать на preflight-запросы (OPTIONS).

Задачи:
1. Добавить fastapi.middleware.cors.CORSMiddleware к приложению FastAPI.
2. Для dev-режима разрешить все origin'ы (allow_origins=["*"]).
3. Для production-режима использовать whitelist origin'ов из env-переменной или конфига.
4. Убедиться, что allow_methods включает ["POST", "GET", "OPTIONS"].
5. Убедиться, что allow_headers включает ["Content-Type", "Authorization"].
6. Разрешить credentials (allow_credentials=True).
7. Кэшировать preflight на 3600 секунд (max_age=3600).

Примечание: 
- Клиент (Kotlin/KMP) отправляет header Authorization: Bearer <token> во все защищённые запросы.
- Это вызывает preflight OPTIONS, поэтому middleware должна корректно это обрабатывать.
- Если allow_origins=["*"], то allow_credentials должен быть False (ограничение браузера).
  В этом случае используй explicit whitelist вместо "*".

Результат:
- Браузерные клиенты (wasm/js) будут успешно отправлять POST-запросы на /api/v1/auth/*.
- Запросы с Authorization-заголовком будут проходить (preflight будет разрешён).
- Сервер будет отвечать с корректными CORS-заголовками.
"""

# ============ ДЛЯ MANUAL IMPLEMENTATION ============
# Пример кода (скопировать в main.py или создать cors.py и импортировать):

from fastapi.middleware.cors import CORSMiddleware

# Добавить в app сразу после создания FastAPI():
def setup_cors(app):
    """Настройка CORS для браузерных клиентов"""
    import os
    
    # Определить origin'ы на основе ENV
    env = os.getenv("ENV", "development")
    
    if env == "development":
        # Dev: разрешить все локальные источники + localhost
        allowed_origins = [
            "http://localhost:3000",
            "http://localhost:5173",
            "http://localhost:5174",
            "http://localhost:8080",
            "http://127.0.0.1:3000",
            "http://127.0.0.1:5173",
            "http://127.0.0.1:5174",
            "http://127.0.0.1:8080",
        ]
    else:
        # Production: белый список домен'ов из конфига
        allowed_origins = [
            "https://app.example.com",
            "https://order.example.com",
        ]
    
    app.add_middleware(
        CORSMiddleware,
        allow_origins=allowed_origins,
        allow_credentials=True,
        allow_methods=["GET", "POST", "OPTIONS", "PATCH", "DELETE"],
        allow_headers=["Content-Type", "Authorization", "Accept"],
        expose_headers=["X-Total-Count"],  # если нужны кастомные headers в ответе
        max_age=3600,
    )

# В main.py:
from fastapi import FastAPI
app = FastAPI()
setup_cors(app)  # <- вызвать перед добавлением маршрутов
# или inline:
# app.add_middleware(CORSMiddleware, allow_origins=[...], ...)

# ============ ПРОВЕРКА ============
# После добавления CORS, протестировать:
# 1. Запустить backend на http://localhost:8000
# 2. Запустить frontend (браузер) на другом порту
# 3. Открыть DevTools -> Network, отправить POST-запрос
# 4. Проверить заголовок ответа "Access-Control-Allow-Origin"
# 5. Если 200 OK и заголовок есть, CORS работает ✓

# Возможные ошибки:
# - 405 Method Not Allowed на OPTIONS -> проверить allow_methods
# - CORS error в консоли браузера -> проверить allow_origins и allow_headers
# - Preflight timeout -> может быть firewall или сетевая проблема
"""
