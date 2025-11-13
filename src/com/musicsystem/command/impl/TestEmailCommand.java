package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.util.InputValidator;
import com.musicsystem.util.Logger;

/**
 * Команда для тестування email-розсилки
 */
public class TestEmailCommand implements Command {
    private static final String CLASS_NAME = "TestEmailCommand";
    private static final Logger logger = Logger.getInstance();
    private InputValidator validator;

    public TestEmailCommand(InputValidator validator) {
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n═══ ТЕСТ EMAIL-РОЗСИЛКИ ═══\n");

        logger.info(CLASS_NAME, "Запуск тесту email-розсилки");

        boolean confirm = validator.readBoolean("Відправити тестовий email?");

        if (!confirm) {
            System.out.println("❌ Тест скасовано.\n");
            logger.info(CLASS_NAME, "Тест email скасовано користувачем");
            return;
        }

        System.out.println("\n⏳ Відправка тестової FATAL помилки...");

        // Логуємо різні рівні
        logger.info(CLASS_NAME, "Тест email - INFO");
        logger.warn(CLASS_NAME, "Тест email - WARN");
        logger.error(CLASS_NAME, "Тест email - ERROR");

        // Відправляємо FATAL (це відправить email)
        logger.fatal(CLASS_NAME, "🧪 ТЕСТОВА КРИТИЧНА ПОМИЛКА - перевірка email-розсилки");

        System.out.println("\n✅ Тест завершено!");
        System.out.println("📧 Перевірте пошту (включно зі СПАМ)");
        System.out.println("📝 Логи: logs/application.log\n");

        logger.info(CLASS_NAME, "Тест email-розсилки завершено");
        validator.waitForEnter();
    }
}

