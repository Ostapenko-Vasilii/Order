#!/bin/bash
# =============================================================================
# Скрипт для быстрого переключения между mock и реальным API
# =============================================================================

NETWORK_MODULE="core/data/src/commonMain/kotlin/ru/orderdorms/core/data/di/NetworkModule.kt"

echo " Переключение режима API"
echo "═══════════════════════════════════════════"

if grep -q "const val USE_MOCK_API = true" "$NETWORK_MODULE"; then
    echo "❌ Текущий режим: MOCK API (выключаю...)"
    sed -i 's/const val USE_MOCK_API = true/const val USE_MOCK_API = false/' "$NETWORK_MODULE"
    echo "✅ Переключился на РЕАЛЬНЫЙ API"
    echo " Убедись что backend запущен на http://localhost:8000"
else
    echo "✅ Текущий режим: РЕАЛЬНЫЙ API (переключаю на MOCK...)"
    sed -i 's/const val USE_MOCK_API = false/const val USE_MOCK_API = true/' "$NETWORK_MODULE"
    echo "✅ Переключился на MOCK API"
    echo " Теперь приложение работает без backend'а"
    echo " Логин: test@example.com / password123"
fi

echo "═══════════════════════════════════════════"
echo " Готово! Пересобери приложение."
