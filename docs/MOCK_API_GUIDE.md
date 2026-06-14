# Mock API Datasource Guide

## Быстрый старт

Для тестирования приложения **без запущенного backend'а** используйте встроенный `MockAuthApiService`.

### Включить мок-режим

Отредактируй `core/data/src/commonMain/kotlin/ru/orderdorms/core/data/di/NetworkModule.kt`:

```kotlin
// Измени false на true
const val USE_MOCK_API = true
```

**или** используй gradle property при сборке:

```bash
./gradlew :composeApp:installDebug -Pmock.api=true
```

---

## Что делает MockAuthApiService?

✅ **Имитирует реальный backend:**
- Возвращает валидные mock-токены
- Имитирует сетевые задержки (200-500ms)
- Поддерживает различные сценарии (успех/ошибки)

✅ **Готовые тестовые данные:**
- Mock tokens для всех этапов авторизации
- Предусмотрены ошибки валидации

✅ **Не требует изменение кода:**
- Подменяет `AuthApiService` выбором в DI
- Все остальные слои работают без изменений

---

## Тестовые учетные данные

### Логин

| Поле | Значение |
|------|----------|
| Email | `test@example.com` |
| Password | `password123` |

Результат: ✅ Успешная авторизация

---

## Сценарии тестирования регистрации

### 1️⃣ Проверка кода приглашения

```
✅ Любой код кроме "INVALID" → успех
❌ Код "INVALID" → ошибка INVALID_CODE
```

### 2️⃣ Отправка email

```
✅ Обычный email → успех
❌ Email "invalid@test.com" → ошибка INVALID_EMAIL  
❌ Email "error@test.com" → ошибка EMAIL_ALREADY_EXISTS
```

### 3️⃣ Верификация кода

```
✅ Любой код кроме "000000" → успех
❌ Код "000000" → ошибка INVALID_CODE
```

### 4️⃣ Установка пароля

```
✅ Пароль >= 8 символов → успех
❌ Пароль < 8 символов → ошибка WEAK_PASSWORD
```

---

## Сценарии тестирования восстановления пароля

### 1️⃣ Отправка email

```
✅ Любой email → успех
```

### 2️⃣ Верификация кода

```
✅ Любой код кроме "000000" → успех
❌ Код "000000" → ошибка INVALID_CODE
```

### 3️⃣ Установка нового пароля

```
✅ Пароль >= 8 символов → успех
❌ Пароль < 8 символов → ошибка WEAK_PASSWORD
```

---

## Примеры использования в UI

### Успешный логин

```kotlin
val email = "test@example.com"
val password = "password123"

// UI автоматически вызывает:
// authViewModel.login(email, password)

// Результат (через 200-500ms): ✅ Успешная авторизация
```

### Ошибка валидации

```kotlin
// Пароль короче 8 символов
val password = "pass"

// Результат: ❌ Ошибка "Пароль должен содержать минимум 8 символов"
```

---

## Переключение между режимами

### Для разработки с backend'ом

```kotlin
const val USE_MOCK_API = false  // Используй реальные API вызовы
```

Убедись, что backend запущен на `http://localhost:8000` (или измени `BACKEND_URL`).

### Для разработки без backend'а

```kotlin
const val USE_MOCK_API = true  // Используй mock-данные
```

Приложение будет работать полностью автономно.

---

## Как добавить новые mock-сценарии

1. Отредактируй `MockAuthApiService.kt`
2. Добавь новую логику в нужный метод
3. Вернутся либо `Either.Right(...)` (успех) либо `Either.Left(...)` (ошибка)

Пример:

```kotlin
override suspend fun login(email: String, password: String): Either<DomainError, TokensResponseDto> {
    simulateNetworkDelay()
    
    // Добавь свой сценарий
    if (email == "blocked@test.com") {
        return Either.Left(
            DomainError(
                code = "ACCOUNT_BLOCKED",
                message = "Ваш аккаунт заблокирован"
            )
        )
    }
    
    // ... остальная логика
}
```

---

## F.A.Q

**Q: Могу ли я использовать mock в production?**  
A: Нет, это только для разработки/тестирования. Перед релизом убедись что `USE_MOCK_API = false`.

**Q: Можно ли использовать mock в тестах?**  
A: Да! Это именно для чего он предназначен.

**Q: Что если мне нужны другие mock-данные?**  
A: Отредактируй `MockAuthApiService.kt` и добавь нужные сценарии.

**Q: Как мне отключить имитацию задержек?**  
A: Закомментируй `simulateNetworkDelay()` или установи задержку на 0.

---

## Ссылки

- [NetworkModule.kt](../src/commonMain/kotlin/ru/orderdorms/core/data/di/NetworkModule.kt)
- [MockAuthApiService.kt](../src/commonMain/kotlin/ru/orderdorms/core/data/network/MockAuthApiService.kt)
- [AuthApiService.kt](../src/commonMain/kotlin/ru/orderdorms/core/data/network/AuthApiService.kt)
