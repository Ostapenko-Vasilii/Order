# FastAPI CORS Middleware для Order Auth API
# Вставить в main.py приложения FastAPI

from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()

# ============ CORS Configuration ============
# Это разрешает запросы из браузера с разных origin'ов (например, локальная разработка, staging, production)

allowed_origins = [
    # Локальная разработка (браузер)
    "http://localhost:3000",
    "http://localhost:8080",
    "http://localhost:5173",
    "http://localhost:5174",
    "http://127.0.0.1:3000",
    "http://127.0.0.1:8080",
    "http://127.0.0.1:5173",
    "http://127.0.0.1:5174",
    
    # Production (замени на реальные домены)
    "https://app.example.com",
    "https://order.example.com",
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=allowed_origins,  # или use allow_origins=["*"] для dev (небезопасно для prod)
    allow_credentials=True,          # разрешить cookies/credentials
    allow_methods=["GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"],  # все нужные methods
    allow_headers=[
        "Content-Type",
        "Authorization",
        "X-Requested-With",
        "Accept",
    ],
    expose_headers=["Content-Range", "X-Content-Range"],  # если нужно возвращать кастомные headers
    max_age=3600,  # кэш preflight на 1 час
)

# ============ Альтернатива: динамический CORS (для dev-режима) ============
# Если хочешь разрешить все origin'ы в dev и только whitelist на prod:

import os

if os.getenv("ENV", "development") == "development":
    # Dev: разрешить все
    allowed_origins_dev = ["*"]
else:
    # Prod: только белый список
    allowed_origins_dev = [
        "https://app.example.com",
        "https://order.example.com",
    ]

app.add_middleware(
    CORSMiddleware,
    allow_origins=allowed_origins_dev,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
    max_age=3600,
)

# ============ Проверка (в конце main.py) ============
if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)  # доступен на http://localhost:8000
