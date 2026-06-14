# Quick Start: Mock API Cheatsheet

##  Включить мок-режим

### Способ 1️⃣ - Отредактировать файл (рекомендуется)

```bash
# Открой файл:
core/data/src/commonMain/kotlin/ru/orderdorms/core/data/di/NetworkModule.kt

# Найди строку:
const val USE_MOCK_API = false

# Измени на:
const val USE_MOCK_API = true
```

### Способ 2️⃣ - Через gradle property

```bash
# Android
./gradlew :androidApp:installDebug

# Wasm/Browser
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

---

##  Быстрые команды

### Android с мок API

```bash
./gradlew :androidApp:installDebug
# или
./gradlew :androidApp:runDebug
```

### iOS с мок API

```bash
# Убедись USE_MOCK_API = true в NetworkModule.kt
./gradlew :iosApp:build
```

### Wasm/Browser с мок API

```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

---

##  Тестовые данные

| Сценарий | Email | Password |
|----------|-------|----------|
| ✅ Успех | `test@example.com` | `password123` |
| ❌ Ошибка | любой другой | любой пароль |

---

##  Верннуть на реальный API

```bash
# Отредактируй NetworkModule.kt:
const val USE_MOCK_API = false

# Убедись что backend запущен на http://localhost:8000
```

---

##  Полная документация

 Смотри **`docs/MOCK_API_GUIDE.md`** для подробных сценариев тестирования

---

##  Советы

-  Мок-режим отлично работает для UI разработки
- ⚡ Имитирует реальные сетевые задержки (200-500ms)
-  Легко переключаться между режимами
-  Все mock сценарии в `MockAuthApiService.kt`

---

## ❓ F.A.Q

**Как отключить имитацию задержек?**  
Закомментируй `simulateNetworkDelay()` в `MockAuthApiService.kt`

**Что если нужны другие mock-данные?**  
Отредактируй методы в `MockAuthApiService.kt`

**Работает ли мок со всеми платформами?**  
✅ Да! Android, iOS, Wasm/JS - все поддерживают мок

**Можно ли использовать мок в production?**  
❌ Нет, только для разработки. Перед релизом: `USE_MOCK_API = false`
