package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.util.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TestEmailCommand implements Command {
    private static final Logger logger = LogManager.getLogger(TestEmailCommand.class);
    private InputValidator validator;

    public TestEmailCommand(InputValidator validator) {
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n═══ ТЕСТ EMAIL-РОЗСИЛКИ ═══\n");

        logger.debug("Запуск тесту email-розсилки");

        boolean confirm = validator.readBoolean("Відправити тестовий email?");

        if (!confirm) {
            System.out.println("❌ Тест скасовано.\n");
            logger.debug("Тест email скасовано користувачем");
            return;
        }

        System.out.println("\n⏳ Відправка тестової FATAL помилки...");

        logger.info("Тест email - INFO");
        logger.warn("Тест email - WARN");
        logger.error("Тест email - ERROR");

        logger.fatal("🧪 ТЕСТОВА КРИТИЧНА ПОМИЛКА - перевірка email-розсилки");

        System.out.println("\n✅ Тест завершено!");
        System.out.println("📧 Перевірте пошту (включно зі СПАМ)");
        System.out.println("📝 Логи: logs/application.log\n");

        logger.debug("Тест email-розсилки завершено");
        validator.waitForEnter();
    }
}

