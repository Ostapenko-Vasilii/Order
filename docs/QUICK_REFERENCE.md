# ⚡ Quick Reference: Команды для разработки

## Frontend (Order KMP App)

### Сборка & Запуск

```bash
#  Wasm (браузер, рекомендуется)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

#  JS (браузер, альтернатива)
./gradlew :composeApp:jsBrowserDevelopmentRun

#  Android (Debug APK)
./gradlew :androidApp:installDebug

#  Android (Release APK)
./gradlew :androidApp:assembleRelease

#  iOS (Xcode project)
./gradlew :iosApp:preparePodspec
# Затем откри iosApp/iosApp.xcodeproj в Xcode
```

### Maintenance

```bash
# Sync Gradle
./gradlew sync

# Очистка
./gradlew clean

# Полная сборка (проверка)
./gradlew build --no-daemon

# Обновить Yarn lock для Wasm
./gradlew :kotlinWasmUpgradeYarnLock

# Просмотр всех tasks
./gradlew :composeApp:tasks --all
```

### Tests

```bash
# Multiplatform unit tests
./gradlew :composeApp:commonMainTests

# JVM tests
./gradlew :composeApp:jvmTest

# Wasm tests
./gradlew :composeApp:wasmJsTest
```

---

## Backend (FastAPI)

### Setup

```bash
# Создать virtual environment
python -m venv venv

# Активировать
source venv/bin/activate          # macOS/Linux
venv\Scripts\activate             # Windows

# Установить зависимости
pip install -r requirements.txt
```

### Запуск

```bash
# Dev режим (с reload)
ENV=development python -m uvicorn main:app --host 0.0.0.0 --port 8000 --reload

# Production режим
ENV=production python -m uvicorn main:app --host 0.0.0.0 --port 8000
```

### Testing

```bash
# Все тесты
pytest

# С coverage
pytest --cov=. --cov-report=html

# Конкретный файл
pytest tests/test_auth.py

# Конкретный тест
pytest tests/test_auth.py::test_invite_code
```

### Utils

```bash
# Format code (black)
black .

# Lint (flake8)
flake8 .

# Type check (mypy)
mypy .

# Все вместе
black . && flake8 . && mypy .
```

---

## Проверка CORS & API

### Браузер (DevTools)

```
1. F12 → Network tab
2. Отправить POST запрос (например, invite-code)
3. Найти по имени "invite-code"
4. Посмотреть Response Headers:
   - Access-Control-Allow-Origin
   - Access-Control-Allow-Methods
   - Access-Control-Allow-Headers
```

### Curl

```bash
# preflight OPTIONS запрос
curl -X OPTIONS http://localhost:8000/api/v1/auth/register/invite-code \
  -H "Origin: http://localhost:5173" \
  -H "Access-Control-Request-Method: POST" \
  -H "Access-Control-Request-Headers: content-type" \
  -v

# Реальный POST запрос
curl -X POST http://localhost:8000/api/v1/auth/register/invite-code \
  -H "Content-Type: application/json" \
  -d '{"code":"qwerty"}' \
  -v

# С Authorization header (для protected endpoints)
curl -X POST http://localhost:8000/api/v1/auth/register/mail \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer mock_invite_token_123" \
  -d '{"email":"test@example.com"}' \
  -v
```

### Browser DevTools Network Tab

```
POST https://localhost:8000/api/v1/auth/register/invite-code
Status: 200 OK (может быть 201, 400, 401, 409, 429, 500)
Response Headers:
  ✓ Access-Control-Allow-Origin           ← CORS разрешён
  ✓ Access-Control-Allow-Methods          ← POST, GET, OPTIONS
  ✓ Access-Control-Allow-Headers          ← Content-Type, Authorization
Response Body: {"invite_token": "..."}
```

---

## GIT

```bash
# Базовые команды
git add .
git commit -m "feat: добавить auth endpoints"
git push origin main

# Branching
git checkout -b feature/auth-endpoints
git merge main
git branch -d feature/auth-endpoints

# Status
git status
git log --oneline
```

---

## Docker (optional, для backend)

```bash
# Build image
docker build -t order-auth-backend .

# Run container
docker run -e ENV=development -p 8000:8000 order-auth-backend

# Stop container
docker ps
docker stop <CONTAINER_ID>
```

---

## Ports

| Сервис | Port | URL |
|--------|------|-----|
| Frontend Wasm | 5173 | http://localhost:5173 |
| Frontend JS | 8080 | http://localhost:8080 |
| Backend (FastAPI) | 8000 | http://localhost:8000 |
| Backend Docs (Swagger) | 8000/docs | http://localhost:8000/docs |

---

## Env Variables

```bash
# Frontend
# BuildKonfig (код-сборка)
BASE_URL=http://localhost:8000  # или production URL

# Backend
ENV=development                 # или production
DEBUG=true                      # или false
```

---

## IDE Shortcuts (Android Studio / IntelliJ IDEA)

```
Ctrl+K, Ctrl+C    — Comment selection
Ctrl+K, Ctrl+U    — Uncomment selection
Ctrl+D            — Duplicate line
Ctrl+/            — Toggle line comment
Shift+Ctrl+L      — Select all occurrences
Alt+Enter         — Quick fix
Ctrl+H            — Find & Replace
Shift+Shift       — Search everywhere
Ctrl+F            — Find in file
```

---

## Common Issues & Fixes

| Ошибка | Fix |
|--------|-----|
| `Gradle sync failed` | `./gradlew clean && ./gradlew sync` |
| `Lock file was changed` | `./gradlew :kotlinWasmUpgradeYarnLock` |
| `CORS error in browser` | Добавить CORS middleware в backend |
| `405 Method Not Allowed` | Проверить OPTIONS на backend |
| `python: command not found` | Установить Python или использовать python3 |
| `Port 8000 already in use` | `lsof -i :8000` (macOS/Linux) или `netstat -ano` (Windows) |
| `BuildKonfig not found` | Запустить сборку (`:composeApp:build` или `:composeApp:wasmJsBrowserDevelopmentRun`) |

---

## Resources

- **Frontend Documentation:** [`README_AUTH_IMPL.md`](README_AUTH_IMPL.md)
- **Backend Prompt:** [`docs/BACKEND_PROMPT_FOR_LLM.md`](docs/BACKEND_PROMPT_FOR_LLM.md)
- **Local Setup:** [`docs/LOCAL_DEV_SETUP.md`](docs/LOCAL_DEV_SETUP.md)
- **Backend Checklist:** [`docs/BACKEND_CHECKLIST.md`](docs/BACKEND_CHECKLIST.md)

---

Будут вопросы — открой документацию! 
