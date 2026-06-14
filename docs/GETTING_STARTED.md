#  Инструкция по использованию документации и конфигов

_Этот файл создан специально для быстрого ознакомления с подготовленными материалами._

---

## ✅ Что было создано

### ‍ Для Frontend-разработчика

| Файл | Назначение |
|------|-----------|
|  [`docs/FRONTEND_STATUS.md`](docs/FRONTEND_STATUS.md) | Полный обзор архитектуры, API endpoints, статус реализации |
|  [`docs/LOCAL_DEV_SETUP.md`](docs/LOCAL_DEV_SETUP.md) | Инструкция по запуску фронта + бэка локально |
|  [`docs/QUICK_REFERENCE.md`](docs/QUICK_REFERENCE.md) | Быстрая справка всех команд (Gradle, curl, etc.) |
|  [`scripts/dev-launcher.sh`](scripts/dev-launcher.sh) | Скрипт для быстрого запуска фронта (Wasm/JS/Android) |

**Используй прежде всего:**
1. Открыть `README_AUTH_IMPL.md` (главный README)
2. Потом `docs/LOCAL_DEV_SETUP.md` (для локального dev)
3. Потом `docs/QUICK_REFERENCE.md` (если нужна команда)

---

###  Для Backend-разработчика (FastAPI)

| Файл | Назначение |
|------|-----------|
|  [`docs/BACKEND_PROMPT_FOR_LLM.md`](docs/BACKEND_PROMPT_FOR_LLM.md) | **ГЛАВНЫЙ ФАЙЛ:** готовый промпт для LLM (ChatGPT, Claude) |
|  [`docs/BACKEND_CHECKLIST.md`](docs/BACKEND_CHECKLIST.md) | Чек-лист всего, что нужно реализовать |
|  [`docs/BACKEND_CORS_PROMPT.md`](docs/BACKEND_CORS_PROMPT.md) | Фокусированный промпт на CORS |
|  [`docs/cors_middleware_fastapi.py`](docs/cors_middleware_fastapi.py) | Готовый код CORS middleware (скопировать в main.py) |
|  [`docs/LOCAL_DEV_SETUP.md`](docs/LOCAL_DEV_SETUP.md) | Backend часть (как запустить и проверить) |
|  [`docs/QUICK_REFERENCE.md`](docs/QUICK_REFERENCE.md) | Backend commands (pytest, uvicorn, format code) |

**Быстрый путь:**
1. Открыть [`docs/BACKEND_PROMPT_FOR_LLM.md`](docs/BACKEND_PROMPT_FOR_LLM.md)
2. Скопировать весь текст из раздела "ЗАДАЧА ДЛЯ LLM"
3. Вставить в ChatGPT/Claude и добавить "Создай полный код"
4. Готовый backend генерируется автоматически

**Или manual way:**
1. Откритьзаем [`docs/cors_middleware_fastapi.py`](docs/cors_middleware_fastapi.py) для CORS
2. Используем [`docs/BACKEND_CHECKLIST.md`](docs/BACKEND_CHECKLIST.md) как чек-лист
3. Запускаем тесты curl из [`docs/QUICK_REFERENCE.md`](docs/QUICK_REFERENCE.md)

---

###  Главные README'ы

| Файл | Кем писан | Описание |
|------|-----------|---------|
|  [`README_AUTH_IMPL.md`](README_AUTH_IMPL.md) | Frontend+Backend обзор | Главный файл, статус, архитектура, быстрый старт |
|  [`docs/FRONTEND_STATUS.md`](docs/FRONTEND_STATUS.md) | Frontend | Детали фронта, контракты API, ошибки |
|  [`docs/LOCAL_DEV_SETUP.md`](docs/LOCAL_DEV_SETUP.md) | Обе стороны | Инструкция по запуску обеих сторон локально |

---

##  Как использовать: пошаговый гайд

### Сценарий 1: Первый запуск фронта

```bash
1. Прочитать: docs/LOCAL_DEV_SETUP.md (Frontend часть)

2. Запустить:
   ./gradlew :composeApp:wasmJsBrowserDevelopmentRun

3. Открыть браузер на http://localhost:XXXX (адрес выведется в терминал)

4. Введи invite code (попробуй "qwerty", если есть backend)
   Если нет backend — будет ошибка сети (это ожидаемо)
```

### Сценарий 2: Запуск бэка с LLM-помощью

```bash
1. Открыть: docs/BACKEND_PROMPT_FOR_LLM.md

2. Скопировать раздел "ЗАДАЧА ДЛЯ LLM" полностью

3. Открыть ChatGPT/Claude

4. Вставить промпт, добавить:
   "Создай полный, готовый к запуску Python код (main.py + requirements.txt).
    Я буду запускать его одной командой: python main.py"

5. Следовать инструкциям LLM, копировать файлы

6. Запустить backend:
   cd /path/to/backend
   python main.py

7. Доступен на http://localhost:8000

8. Теперь backend + frontend работают вместе!
```

### Сценарий 3: Manual создание backend (без LLM)

```bash
1. Использовать docs/BACKEND_CHECKLIST.md как главный чек-лист

2. Для CORS:
   - Скопировать код из docs/cors_middleware_fastapi.py
   - Вставить в main.py

3. Для endpoints:
   - Каждый endpoint из чек-листа имеет известную логику
   - Реализовать по формату JSON

4. Для проверки:
   - Используй commands из docs/QUICK_REFERENCE.md
   - curl для тестирования
```

### Сценарий 4: Локальный dev (фронт + бэк вместе)

```bash
1. Прочитать: docs/LOCAL_DEV_SETUP.md (вся)

2. Терминал 1 — Backend:
   cd /path/to/backend
   ENV=development python -m uvicorn main:app --host 0.0.0.0 --port 8000 --reload

3. Терминал 2 — Frontend:
   cd /home/user/StudioProjects/Order
   ./gradlew :composeApp:wasmJsBrowserDevelopmentRun

4. Браузер автоматически откроется

5. Тестировать: вводить данные, смотреть в DevTools (F12)

6. Если CORS error — смотри docs/LOCAL_DEV_SETUP.md → Типовые ошибки
```

---

##  Структура документации

```
docs/
├─ README_AUTH_IMPL.md              ← ГЛАВНЫЙ FILE (стартовый)
├─ FRONTEND_STATUS.md               ← Frontend архитектура & статус
├─ BACKEND_PROMPT_FOR_LLM.md        ← ГЛАВНЫЙ FILE для backend (копировать в LLM)
├─ BACKEND_CHECKLIST.md             ← Backend чек-лист
├─ BACKEND_CORS_PROMPT.md           ← CORS промпт (опционален)
├─ LOCAL_DEV_SETUP.md               ← Dev инструкция для обоих
├─ QUICK_REFERENCE.md               ← Быстрая справка команд
└─ cors_middleware_fastapi.py       ← CORS готовый код

scripts/
└─ dev-launcher.sh                  ← Скрипт быстрого запуска (chmod +x)
```

---

##  Быстрый навигатор

**Я хочу запустить фронт**
→ [`docs/LOCAL_DEV_SETUP.md`](docs/LOCAL_DEV_SETUP.md) + [`scripts/dev-launcher.sh`](scripts/dev-launcher.sh)

**Я хочу создать backend быстро**
→ [`docs/BACKEND_PROMPT_FOR_LLM.md`](docs/BACKEND_PROMPT_FOR_LLM.md) (copy-paste в LLM)

**Я хочу создать backend вручную**
→ [`docs/BACKEND_CHECKLIST.md`](docs/BACKEND_CHECKLIST.md) + [`docs/cors_middleware_fastapi.py`](docs/cors_middleware_fastapi.py)

**Я хочу понять архитектуру**
→ [`README_AUTH_IMPL.md`](README_AUTH_IMPL.md) + [`docs/FRONTEND_STATUS.md`](docs/FRONTEND_STATUS.md)

**Я забыл команду для Gradle / curl / pytest**
→ [`docs/QUICK_REFERENCE.md`](docs/QUICK_REFERENCE.md)

**Я получил CORS error**
→ [`docs/LOCAL_DEV_SETUP.md`](docs/LOCAL_DEV_SETUP.md) → Типовые ошибки

---

##  Pro Tips

1. **Всегда начинай с `README_AUTH_IMPL.md`** — это главный README проекта

2. **Для backend с LLM: просто скопируй из `BACKEND_PROMPT_FOR_LLM.md`** — это работает в 90% случаев

3. **Используй `scripts/dev-launcher.sh`** — облегчает запуск фронта одной командой

4. **Смотри `docs/QUICK_REFERENCE.md` для любой команды** — основные команды там

5. **CORS error? Первым делом смотри `docs/LOCAL_DEV_SETUP.md`** → Проверка CORS

6. **Нет backend? Просто прочитай `docs/BACKEND_CHECKLIST.md`** — там весь требуемый функционал

---

##  Если что-то не ясно

| Вопрос | Ответ находится в |
|--------|-------------------|
| "Как запустить фронт?" | `docs/LOCAL_DEV_SETUP.md` (Frontend часть) |
| "Как создать backend?" | `docs/BACKEND_PROMPT_FOR_LLM.md` (скопировать в LLM) |
| "Какие endpoints нужны?" | `docs/FRONTEND_STATUS.md` (секция API Endpoints) |
| "CORS error — что делать?" | `docs/LOCAL_DEV_SETUP.md` (Проверка CORS) |
| "Забыл команду Gradle" | `docs/QUICK_REFERENCE.md` (Frontend часть) |
| "Забыл команду curl" | `docs/QUICK_REFERENCE.md` (Проверка CORS & API) |

---

Все файлы готовы к использованию! Начни с `README_AUTH_IMPL.md` или `docs/LOCAL_DEV_SETUP.md` 
