# 📑 MOCK API - Индекс документации

## 🎯 С чего начать?

1. **Ты здесь первый раз?**  
   → Прочитай [`MOCK_API_SETUP.md`](./MOCK_API_SETUP.md) (5 минут)

2. **Нужны быстрые команды?**  
   → Смотри [`docs/MOCK_API_CHEATSHEET.md`](./docs/MOCK_API_CHEATSHEET.md)

3. **Хочешь всё знать?**  
   → Читай [`docs/MOCK_API_GUIDE.md`](./docs/MOCK_API_GUIDE.md)

---

## 📂 Файлы проекта

### Исходный код
```
core/data/src/commonMain/kotlin/ru/orderdorms/core/data/
├── network/
│   ├── MockAuthApiService.kt        ← ✨ НОВЫЙ МОК DATASOURCE
│   ├── AuthApiService.kt            (интерфейс)
│   ├── KtorAuthApiService.kt        (реальные HTTP вызовы)
│   └── ...
└── di/
    └── NetworkModule.kt             ← ✏️ ОБНОВЛЕН (добавлен флаг USE_MOCK_API)
```

### Документация
```
/
├── MOCK_API_SETUP.md                ← 🌟 НАЧНИ ОТСЮДА
├── CHANGES_SUMMARY.md               (что изменилось)
├── togglemock-api.sh                (скрипт переключения)
│
docs/
├── MOCK_API_GUIDE.md                (полный гайд)
├── MOCK_API_CHEATSHEET.md           (шпаргалка)
├── DEV_MODE.properties              (конфиг)
└── ...

core/data/
└── README.md                        ← ✏️ ОБНОВЛЕН (описание мока)
```

---

## 🚀 Быстрый старт (30 сек)

```bash
# 1. Отредактируй файл
nano core/data/src/commonMain/kotlin/ru/orderdorms/core/data/di/NetworkModule.kt

# 2. Измени false на true:
#    const val USE_MOCK_API = true

# 3. Или используй скрипт
./toggle-mock-api.sh

# 4. Запусти приложение
./gradlew :androidApp:installDebug
```

**Логин:** test@example.com / password123

---

## 📖 Документация

| Файл | Для кого | Время |
|------|----------|-------|
| [`MOCK_API_SETUP.md`](./MOCK_API_SETUP.md) | Новички | 5 мин |
| [`docs/MOCK_API_CHEATSHEET.md`](./docs/MOCK_API_CHEATSHEET.md) | Разработчики | 3 мин |
| [`docs/MOCK_API_GUIDE.md`](./docs/MOCK_API_GUIDE.md) | Все подробности | 15 мин |
| [`CHANGES_SUMMARY.md`](./CHANGES_SUMMARY.md) | Что изменилось | 10 мин |

---

## 💻 Команды

### Переключение режима

**Вариант 1 (скрипт):**
```bash
./toggle-mock-api.sh
```

**Вариант 2 (вручную):**
```bash
# Отредактируй NetworkModule.kt
nano core/data/src/commonMain/kotlin/ru/orderdorms/core/data/di/NetworkModule.kt
```

### Запуск приложения

**Android:**
```bash
./gradlew :androidApp:installDebug
```

**iOS:**
```bash
# Открой в Xcode
open iosApp/iosApp.xcodeproj
```

**Wasm/Browser:**
```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

---

## 🧪 Тестовые данные

### Успешный логин
```
Email:    test@example.com
Password: password123
```

### Ошибки валидации
```
Email "invalid@test.com"              → INVALID_EMAIL
Email "error@test.com"                → EMAIL_ALREADY_EXISTS
Пароль короче 8 символов             → WEAK_PASSWORD
Код верификации "000000"              → INVALID_CODE
```

---

## ✨ Возможности

✅ Работает без backend'а  
✅ Все платформы (Android/iOS/Wasm)  
✅ Имитирует сетевые задержки  
✅ Готовые сценарии ошибок  
✅ Легко переключать между режимами  
✅ Полная документация  

---

## 🗂️ Структура решения

```
Mock API Datasource
│
├── 📦 Исходный код
│   ├── MockAuthApiService.kt (новый)
│   └── NetworkModule.kt (обновлен)
│
├── 📖 Документация
│   ├── MOCK_API_SETUP.md (главный)
│   ├── MOCK_API_GUIDE.md (полный)
│   ├── MOCK_API_CHEATSHEET.md (шпаргалка)
│   └── DEV_MODE.properties (конфиг)
│
├── 🔧 Утилиты
│   └── toggle-mock-api.sh (переключение)
│
└── 📋 Сводки
    ├── CHANGES_SUMMARY.md (что изменилось)
    └── MOCK_API_INDEX.md (этот файл)
```

---

## ❓ F.A.Q

**Q: Как включить мок?**  
A: Редактируй `NetworkModule.kt`, измени `USE_MOCK_API = false` на `true`

**Q: Какие данные для логина?**  
A: test@example.com / password123

**Q: Работает ли на iOS и Wasm?**  
A: ✅ Да, на всех платформах

**Q: Как вернуться на реальный API?**  
A: Измени `USE_MOCK_API = true` на `false` в NetworkModule.kt

**Q: Что если нужны другие mock-данные?**  
A: Отредактируй `MockAuthApiService.kt`

---

## 🔗 Ссылки

- [MockAuthApiService.kt](./core/data/src/commonMain/kotlin/ru/orderdorms/core/data/network/MockAuthApiService.kt)
- [NetworkModule.kt](./core/data/src/commonMain/kotlin/ru/orderdorms/core/data/di/NetworkModule.kt)
- [core/data/README.md](./core/data/README.md)

---

## 📞 Помощь

1. Читай [`docs/MOCK_API_GUIDE.md`](./docs/MOCK_API_GUIDE.md) - там ответы на вопросы
2. Смотри примеры в [`MockAuthApiService.kt`](./core/data/src/commonMain/kotlin/ru/orderdorms/core/data/network/MockAuthApiService.kt)
3. Используй [`toggle-mock-api.sh`](./toggle-mock-api.sh) для быстрого переключения

---

**Версия:** 1.0  
**Дата:** 2026-05-21  
**Статус:** ✅ Готово к использованию

🎉 **Приложение теперь работает без backend'а!**

