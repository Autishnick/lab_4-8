package com.musicsystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.musicsystem.util.LogLevel;

public class TestEmailAndLogging {
    private static final Logger logger = LogManager.getLogger(TestEmailAndLogging.class);

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     ТЕСТ СИСТЕМИ ЛОГУВАННЯ ТА EMAIL СПОВІЩЕНЬ             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();

        testAllLogLevels();
        testExceptionLogging();
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
        String logLevel = System.getProperty("log.level", "INFO");
        String logFilePath = System.getProperty("log.file.path", "logs/application.log");
        System.out.println("   - Рівень логування: " + logLevel);
        System.out.println("   - Шлях до логів: " + logFilePath);
        System.out.println();
    }

    private static void testAllLogLevels() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🔍 Тест 1: Перевірка всіх рівнів логування");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        logger.debug("DEBUG: Це детальне повідомлення для налагодження");
        logger.info("INFO: Інформаційне повідомлення про роботу системи");
        logger.warn("WARN: Попередження про потенційну проблему");
        logger.error("ERROR: Помилка, яка не критична для системи");
        
        System.out.println("✅ Записано логи всіх рівнів (DEBUG, INFO, WARN, ERROR)");
        System.out.println();
    }

    private static void testExceptionLogging() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🐛 Тест 2: Логування з винятками");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        try {
            throw new RuntimeException("Тестовий виняток для перевірки логування");
        } catch (Exception e) {
            logger.error("Перехоплено тестовий виняток", e);
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
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        logger.fatal("🧪 ТЕСТОВА КРИТИЧНА ПОМИЛКА - це тест системи логування");
        
        System.out.println();
        System.out.println("✅ FATAL лог записано");
        System.out.println("📧 Перевірте пошту (якщо email налаштовано і JavaMail встановлено)");
        System.out.println();
    }
}

