// noinspection SpellCheckingInspection
package com.musicsystem;

// noinspection SpellCheckingInspection
import com.musicsystem.util.Logger;
// noinspection SpellCheckingInspection
import com.musicsystem.util.LogLevel;

/**
 * Тестовий клас для перевірки логування та email розсилки
 */
public class TestEmailAndLogging {
    private static final String CLASS_NAME = "TestEmailAndLogging";
    private static final Logger logger = Logger.getInstance();

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     ТЕСТ СИСТЕМИ ЛОГУВАННЯ ТА EMAIL СПОВІЩЕНЬ             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();

        // Перевірка всіх рівнів логування
        testAllLogLevels();

        // Перевірка логування з винятками
        testExceptionLogging();

        // Перевірка FATAL (відправить email якщо налаштовано)
        testFatalError();

        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    РЕЗУЛЬТАТИ ТЕСТУ                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("✅ Тест завершено!");
        System.out.println();
        System.out.println("📋 Перевірте результати:");
        System.out.println("   1. Файл логів: logs/application.log");
        System.out.println("   2. Email (якщо налаштовано): перевірте пошту");
        System.out.println();
        System.out.println("📊 Поточні налаштування:");
        System.out.println("   - Рівень логування: " + logger.getCurrentLevel());
        System.out.println("   - Шлях до логів: " + logger.getLogFilePath());
        System.out.println();
    }

    private static void testAllLogLevels() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🔍 Тест 1: Перевірка всіх рівнів логування");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        logger.debug(CLASS_NAME, "DEBUG: Це детальне повідомлення для налагодження");
        logger.info(CLASS_NAME, "INFO: Інформаційне повідомлення про роботу системи");
        logger.warn(CLASS_NAME, "WARN: Попередження про потенційну проблему");
        logger.error(CLASS_NAME, "ERROR: Помилка, яка не критична для системи");
        
        System.out.println("✅ Записано логи всіх рівнів (DEBUG, INFO, WARN, ERROR)");
        System.out.println();
    }

    private static void testExceptionLogging() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🐛 Тест 2: Логування з винятками");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        try {
            // Штучно створюємо виняток
            throw new RuntimeException("Тестовий виняток для перевірки логування");
        } catch (Exception e) {
            logger.error(CLASS_NAME, "Перехоплено тестовий виняток", e);
            System.out.println("✅ Виняток залогований");
        }
        System.out.println();
    }

    private static void testFatalError() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🚨 Тест 3: FATAL помилка (відправить email якщо налаштовано)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        System.out.println("⚠️  УВАГА: Якщо email налаштовано, буде відправлено тестове повідомлення!");
        System.out.println("   (Якщо JavaMail не встановлено, email не відправиться, але це нормально)");
        System.out.println();
        
        // Пауза для читабельності
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Це відправить email якщо налаштовано
        logger.fatal(CLASS_NAME, "🧪 ТЕСТОВА КРИТИЧНА ПОМИЛКА - це тест системи логування");
        
        System.out.println();
        System.out.println("✅ FATAL лог записано");
        System.out.println("📧 Перевірте пошту (якщо email налаштовано і JavaMail встановлено)");
        System.out.println();
    }
}

