#!/bin/bash
# Скрипт для перевірки логування

echo "📝 Перевірка системи логування..."
echo ""

# Очистити старі логи
rm -f logs/application.log
echo "🗑️  Очищено старі логи"

# Компіляція
echo "📦 Компіляція..."
javac -d bin -encoding UTF-8 -sourcepath src src/com/musicsystem/Main.java

# Створити тестовий файл для автоматичного вводу
cat > /tmp/music_test_input.txt << 'EOF'

4
0
n
EOF

# Запустити програму з автоматичним вводом
echo ""
echo "🚀 Запуск програми (автоматичний режим)..."
echo "================================"
java -cp bin com.musicsystem.Main < /tmp/music_test_input.txt 2>&1 | head -30

# Показати створені логи
echo ""
echo "================================"
echo "📄 Перевірка лог-файлу:"
echo "================================"

if [ -f logs/application.log ]; then
    echo "✅ Файл logs/application.log створено!"
    echo ""
    echo "📋 Останні 20 рядків логу:"
    echo "--------------------------------"
    tail -20 logs/application.log
    echo ""
    echo "📊 Статистика логів:"
    echo "--------------------------------"
    echo -n "DEBUG: "
    grep -c "\[DEBUG\]" logs/application.log || echo "0"
    echo -n "INFO:  "
    grep -c "\[INFO\]" logs/application.log || echo "0"
    echo -n "WARN:  "
    grep -c "\[WARN\]" logs/application.log || echo "0"
    echo -n "ERROR: "
    grep -c "\[ERROR\]" logs/application.log || echo "0"
    echo -n "FATAL: "
    grep -c "\[FATAL\]" logs/application.log || echo "0"
    echo ""
    echo "✅ Логування працює правильно!"
else
    echo "❌ Лог-файл НЕ створено!"
fi

# Видалити тимчасовий файл
rm -f /tmp/music_test_input.txt

