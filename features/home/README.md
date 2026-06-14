# `features/home`

Домашний экран приложения после успешной авторизации.

## Что есть сейчас
- `HomeFlow` — вход в feature-экран
- `HomeViewModel` — загрузка состояния dashboard
- `HomeRepositoryImpl` — временные данные для первого запуска
- `HomeModule` — DI-модуль для Koin

## Подключение
Модуль уже добавлен в:
- `settings.gradle.kts`
- `composeApp/build.gradle.kts`
- `composeApp/src/commonMain/kotlin/ru/orderdorms/di/Koin.kt`
- `composeApp/src/commonMain/kotlin/ru/orderdorms/navigation/RootComponent.kt`

## Запуск
Для проверки достаточно собрать shared metadata:

```bash
./gradlew :composeApp:compileKotlinMetadata :features:home:compileKotlinMetadata
```

