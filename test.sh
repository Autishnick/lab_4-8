#!/bin/bash
# Скрипт для запуску тестів

echo "🧪 Компіляція та запуск тестів..."
echo ""

# Компіляція основного коду
echo "📦 Компіляція основного коду..."
javac -d bin -encoding UTF-8 -sourcepath src src/com/musicsystem/**/*.java

# Компіляція тестів
echo "📦 Компіляція тестів..."
javac -d bin -encoding UTF-8 -cp "bin:libs/junit-platform-console-standalone-1.11.0.jar" -sourcepath tests/src tests/src/com/musicsystem/**/*Test.java

# Запуск тестів
echo ""
echo "🚀 Запуск тестів..."
echo "================================"
java -jar libs/junit-platform-console-standalone-1.11.0.jar \
  --class-path bin \
  --scan-class-path \
  --disable-banner

echo ""
echo "✅ Тести завершені!"

