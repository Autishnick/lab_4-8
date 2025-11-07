#!/bin/bash
# Скрипт компіляції з JavaMail

echo "📦 Компіляція Music System (з JavaMail)..."
javac -cp "libs/javax.mail-1.6.2.jar" \
      -d bin \
      -encoding UTF-8 \
      -sourcepath src \
      src/com/musicsystem/**/*.java

if [ $? -eq 0 ]; then
    echo "✅ Компіляція успішна!"
    echo ""
    echo "📋 Скомпільовані класи в папці bin/"
    echo "📧 JavaMail підключено (email буде працювати)"
else
    echo "❌ Помилка компіляції!"
    exit 1
fi

