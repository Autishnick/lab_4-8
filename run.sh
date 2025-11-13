#!/bin/bash
# Скрипт для запуску Music System

echo "🎵 Компіляція Music System..."
javac -cp "libs/javax.mail-1.6.2.jar:libs/activation-1.1.1.jar" -d bin -encoding UTF-8 -sourcepath src src/com/musicsystem/Main.java

if [ $? -eq 0 ]; then
    echo "✅ Компіляція успішна!"
    echo "🚀 Запуск програми (з підтримкою email)..."
    echo ""
    java -cp "bin:libs/javax.mail-1.6.2.jar:libs/activation-1.1.1.jar" com.musicsystem.Main
else
    echo "❌ Помилка компіляції!"
    exit 1
fi
