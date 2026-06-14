#!/bin/bash

#  Order App — Quick Local Dev Launcher
# Запускает frontend (wasm) + помощь для запуска backend

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

echo " Order App Local Development Launcher"
echo "========================================"
echo ""

# Выборка target'а
echo "Выберите, что запустить:"
echo "1. Wasm (браузер, рекомендуется)"
echo "2. JS (браузер)"
echo "3. Android (требует эмулятор)"
echo "4. Show instructions (только конфиг)"
echo ""
read -p "Выбери (1-4): " choice

case $choice in
  1)
    echo " Запускаем Wasm в браузере..."
    echo "Убедись, что backend запущен на http://localhost:8000!"
    echo ""
    cd "$PROJECT_ROOT"
    ./gradlew :composeApp:wasmJsBrowserDevelopmentRun
    ;;
  2)
    echo " Запускаем JS в браузере..."
    echo "Убедись, что backend запущен на http://localhost:8000!"
    echo ""
    cd "$PROJECT_ROOT"
    ./gradlew :composeApp:jsBrowserDevelopmentRun
    ;;
  3)
    echo " Запускаем Android..."
    echo "Убедись, что эмулятор или устройство подключены!"
    echo ""
    cd "$PROJECT_ROOT"
    ./gradlew :androidApp:installDebug
    ;;
  4)
    echo " Show Dev Instructions"
    echo ""
    cat << 'EOF'

 Local Development Setup
==========================

## Backend (FastAPI)

1. Откры второй терминал и перейди в папку backend:
   cd /path/to/order-auth-backend

2. Создай virtual environment (если нет):
   python -m venv venv
   source venv/bin/activate  # или venv\Scripts\activate на Windows

3. Установи зависимости:
   pip install -r requirements.txt

4. Добавь CORS middleware из docs/cors_middleware_fastapi.py в main.py

5. Запусти backend:
   ENV=development python -m uvicorn main:app --host 0.0.0.0 --port 8000 --reload

   Backend будет доступен на http://localhost:8000

## Frontend (Wasm)

1. Запусти (в основном терминале):
   cd /home/user/StudioProjects/Order
   ./gradlew :composeApp:wasmJsBrowserDevelopmentRun

   Откроется браузер на http://localhost:XXXX

## Проверка CORS

1. Открыть DevTools (F12)
2. Network tab
3. Сделать запрос (например, ввести invite code)
4. Проверить ответ: есть ли Access-Control-Allow-Origin?

Если 405 Method Not Allowed на OPTIONS — чек пункт 4 (CORS middleware)

## Логирование

Frontend:
  - Wasm: DevTools Console (F12)
  - Android: Logcat в Android Studio

Backend:
  - FastAPI logs в терминале

## Документация

   docs/cors_middleware_fastapi.py  — CORS конфиг
   docs/BACKEND_CHECKLIST.md        — чек-лист backend
   docs/LOCAL_DEV_SETUP.md          — подробная инструкция
   docs/FRONTEND_STATUS.md          — статус фронтенда

EOF
    ;;
  *)
    echo "❌ Неверный выбор"
    exit 1
    ;;
esac
