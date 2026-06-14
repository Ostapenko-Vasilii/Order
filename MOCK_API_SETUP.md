#  Mock API Datasource - Готово!

## ✅ Что было сделано

Создан встроенный **MockAuthApiService** для тестирования приложения **без запущенного backend'а**.

###  Созданные файлы

1. **`MockAuthApiService.kt`** - Mock реализация всех методов AuthApiService
   - Имитирует сетевые задержки (200-500ms)
   - Возвращает реалистичные mock-токены
   - Поддерживает различные сценарии (успех/ошибки)

2. **`NetworkModule.kt`** - Обновлен для выбора режима
   - Добавлен флаг `USE_MOCK_API` для переключения
   - Легко переключать между реальным и mock API

3. **Документация:**
   - `docs/MOCK_API_GUIDE.md` - Полный гайд с примерами
   - `docs/MOCK_API_CHEATSHEET.md` - Шпаргалка с командами
   - `docs/DEV_MODE.properties` - Конфиг для разработки
   - `core/data/README.md` - Обновлено описание компонентов

---

##  Быстрый старт

### Включить мок-режим

Отредактируй `core/data/src/commonMain/kotlin/ru/orderdorms/core/data/di/NetworkModule.kt`:

```kotlin
const val USE_MOCK_API = true  // Включи мок-режим
```

### Тестовые данные для логина

```
Email:    test@example.com
Password: password123
```

---

##  Функциональность

✅ **Все методы AuthApiService поддерживаются:**
- ✅ `checkInviteCode()` - проверка кода приглашения
- ✅ `sendRegistrationEmail()` - отправка email при регистрации
- ✅ `resendRegistrationCode()` - переотправка кода
- ✅ `verifyRegistrationCode()` - проверка кода верификации
- ✅ `setRegistrationPassword()` - установка пароля
- ✅ `login()` - вход в аккаунт
- ✅ `sendForgotPasswordEmail()` - отправка email для восстановления
- ✅ `verifyForgotPasswordCode()` - проверка кода восстановления
- ✅ `setForgotPassword()` - установка нового пароля

✅ **Сценарии тестирования:**
- Успешные операции
- Валидация данных (email, пароль, коды)
- Обработка ошибок
- Имитация сетевых задержек

---

##  Поддерживаемые платформы

✅ Android  
✅ iOS  
✅ Wasm/JS Browser  

---

##  Использование

### Для разработки UI без backend'а

```kotlin
// core/data/src/commonMain/kotlin/ru/orderdorms/core/data/di/NetworkModule.kt
const val USE_MOCK_API = true
```

### Для работы с реальным backend'ом

```kotlin
const val USE_MOCK_API = false
```

---

##  Документация

- ** `MOCK_API_GUIDE.md`** - Полная документация с примерами
- **⚡ `MOCK_API_CHEATSHEET.md`** - Быстрые команды
- ** `DEV_MODE.properties`** - Конфиг для разработки

---

##  Следующие шаги

1. Отредактируй `NetworkModule.kt` - измени `USE_MOCK_API = true`
2. Запусти приложение любым способом:
   - Android: `./gradlew :androidApp:installDebug`
   - iOS: открой в Xcode
   - Wasm: `./gradlew :composeApp:wasmJsBrowserDevelopmentRun`
3. Стартуй процесс авторизации - всё будет работать без backend'а!

---

## ✨ Преимущества

 **Разработка без backend'а** - полная независимость от сервера  
⚡ **Быстрые итерации** - нет задержек на развертывание backend'а  
 **Тестирование UI** - все сценарии ошибок можно легко воспроизвести  
 **Готовые примеры** - все методы уже реализованы  
 **Легко переключать** - одна строка кода меняет режим  

---

## ❓ Вопросы?

Смотри документацию:
- **`docs/MOCK_API_GUIDE.md`** - Ответы на вопросы
- **`MockAuthApiService.kt`** - Исходный код с комментариями

---

**Готово к разработке! **
