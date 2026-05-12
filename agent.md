# Agent guide — инструкции для проекта Order

Документ служит справочником для автоматизированного агента / разработчика, который будет
работать с репозиторием `Order`. Здесь собраны команды, объяснения архитектуры модулей,
инструкции по запуску веб- и мобильных билдов (dev и production), а также типичные шаги
по отладке и рекомендации по поддержанию чистоты проекта.

Всё ниже написано на русском; команды — для Windows PowerShell (файлы `.\gradlew.bat`).

---

## Быстрая навигация
- Открывать проект нужно из корня репозитория `Order`.
- Основные модули:
  - `composeApp` — основная multiplatform UI-библиотека/приложение (Android / iOS / Web / Wasm)
  - `androidApp` — Android-апк (entrypoint для Android)
  - `iosApp` — xcode проект для iOS
  - `core/*` — (data / domain / ui) — общая архитектурная оболочка (в проекте часть модулей может быть пустой)
  - `build-logic` — кастомные convention plugins и утилиты Gradle

---

## Частые команды (PowerShell)

# показать все задачи для модуля composeApp (полезно чтобы увидеть точные имена тасков)
```powershell
.\gradlew.bat :composeApp:tasks --all
```

# запустить dev-сервер для Wasm (быстрее, современные браузеры)
```powershell
.\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
```

# запустить dev-сервер для JS target (если используется)
```powershell
.\gradlew.bat :composeApp:jsBrowserDevelopmentRun
```

# собрать production/release версию web (Wasm)
# В большинстве Compose Multiplatform проектов аналогичная задача называется
# `wasmJsBrowserProductionRun` или `wasmJsBrowserProductionWebpack` — проверьте список тасков
```powershell
.\gradlew.bat :composeApp:wasmJsBrowserProductionRun
# если таска нет — выполните сначала
.\gradlew.bat :composeApp:tasks --all | Out-String | Select-String -Pattern "Production"
```

# собрать production/release версию web (JS)
```powershell
.\gradlew.bat :composeApp:jsBrowserProductionRun
# или
.\gradlew.bat :composeApp:jsBrowserProductionWebpack
```

# собрать Android debug APK
```powershell
.\gradlew.bat :androidApp:assembleDebug
```

# собрать Android release (подписанный / unsigned, в зависимости от конфигурации)
```powershell
.\gradlew.bat :androidApp:assembleRelease
```

# запустить unit / multiplatform тесты — сначала просмотрите доступные тест-таски
```powershell
.\gradlew.bat :composeApp:tasks --all | Out-String | Select-String -Pattern "test"
# примеры
.\gradlew.bat :composeApp:jsTest
.\gradlew.bat :composeApp:jvmTest
```

# очистка
```powershell
.\gradlew.bat clean
```

---

## Как создать конфигурацию запуска web (Gradle) в Android Studio
1. Откройте **Run → Edit Configurations...**
2. Нажмите `+` → выберите **Gradle**
3. Заполните поля:
   - Name: `ComposeApp Wasm Dev` (или любое удобное)
   - Gradle project: выберите корень (`Order`) или `:composeApp`
   - Tasks: `:composeApp:wasmJsBrowserDevelopmentRun`
4. Примените и запустите.

Если задача не видна в Gradle tool window — выполните `File → Sync Project with Gradle Files` и/или `Invalidate Caches / Restart`.

---

## Гарантии и проверки
- Всегда перед запуском production/bundle сборки проверьте командой `:composeApp:tasks --all` — имена тасков могут отличаться
- Убедитесь, что проект открыт из корня и Gradle synced (иначе task lookup в Android Studio не покажет подпроекты)

---

## Структура и рекомендации по модулям
- `composeApp` — место для общих UI-компонентов и entrypoints для Web / Wasm / Desktop / iOS
  - Ресурсы UI для общих модулей держите в `commonMain/resources` или в target-specific ресурсах (`androidMain/res`, `jsMain/resources`) — не кладите сгенерированные файлы в `src` вручную
- `core/ui` — общий UI-компонентный модуль; используйте его как `implementation(project(":core:ui"))` в `composeApp`.
- `build-logic` — держит conventions; избегайте дублирования конфигурации между плагинами, выносите общую логику в утилиты.

Рекомендации:
- Не храните сгенерированные артефакты в `src/*` — они должны быть в `build/` или генерироваться в процессе Gradle tasks
- Для строковых ресурсов используйте мультимодульную генерацию (compose resources) и проверьте, что все модули подключены корректно

---

## Исправления, сделанные агентом (коротко)
- Удалён хрупкий таск ручного копирования compose-ресурсов из `composeApp.build.gradle.kts` (если вы ищете — больше не используется)
- Добавлены правила в `.gitignore` для типичных артефактов (hs_err_pid, extracted/, kotlin-js-store и т.д.)
- В `App.kt` добавлен пример использования `OrderTextField` из `core/ui` и соответствующая зависимость проекта

Если нужно — откатить эти изменения можно через git.

---

## Траблшутинг (советы)
- Gradle sync не видит модуль / таск:
  - Откройте проект из корня; выполните `File → Sync Project with Gradle Files`; затем `Invalidate Caches / Restart`.
- Падает сборка на missing class из Compose tooling:
  - Убедитесь, что в `build-logic` / convention-плагинах добавлена зависимость на `androidx.compose.ui:ui-tooling` и `org.jetbrains.compose:compose-gradle-plugin` нужной версии.
- Проблемы с ресурсами в превьюх:
  - В превью иногда не работает `stringResource` для multiplatform-ресурсов — подмените локально на строковые литералы или включите генерацию ресурсов до превью.
- Непонятные таски production/dev:
  - Запустите `:composeApp:tasks --all` и ищите `Production` / `Development` / `Run` в именах. Именования могут менять версии Kotlin/Compose.

---

## Как помочь агенту в будущем (если будете автоматизировать)
- В корне добавить `agent-config.yaml` с минимальными настройками окружения (JDK, Gradle version, запускаемые таски по умолчанию)
- В `build-logic` привести к единому стандарту все plugin-helpers, убрать ручные строки зависимости в пользу typed `libs`/version catalog
- Добавить CI (GitHub Actions / Azure Pipelines) с job’ами: `assembleDebug`, `composeApp:wasmJsBrowserProductionRun`, `composeApp:jsBrowserProductionRun`, `check` — это упростит проверку вне локальной среды

---

Если нужно — могу сделать автоматическую `agent-config.yaml` и добавить YAML-конфигурации для CI, либо подготовить готовые Run-конфигурации IDE (в виде `.run` файлов) для Android Studio.

Автор данного файла: автоматический агент — руководство по работе с репозиторием `Order`.

