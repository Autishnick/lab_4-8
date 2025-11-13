package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.util.InputValidator;
import com.musicsystem.util.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Команда для запуску юніт-тестів
 */
public class RunUnitTestsCommand implements Command {
    private static final String CLASS_NAME = "RunUnitTestsCommand";
    private static final Logger logger = Logger.getInstance();
    private InputValidator validator;

    public RunUnitTestsCommand(InputValidator validator) {
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n═══ ЮНІТ-ТЕСТИ ═══\n");

        logger.info(CLASS_NAME, "Запуск юніт-тестів");

        System.out.println("1. Всі тести");
        System.out.println("2. Тести моделей");
        System.out.println("3. Тести сервісів");
        System.out.println("4. Тести утиліт");
        System.out.println("0. Скасувати\n");

        int choice = validator.readInt("Оберіть: ", 0, 4);

        if (choice == 0) {
            System.out.println("❌ Скасовано.\n");
            return;
        }

        String testPath = getTestPath(choice);
        String testName = getTestName(choice);

        System.out.println("\n⏳ Запуск: " + testName + "...\n");

        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "./test_all.sh", testPath);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );

            // Показуємо тільки важливі рядки
            String line;
            int testCount = 0;
            int passedCount = 0;
            boolean showLine = false;

            while ((line = reader.readLine()) != null) {
                // Показуємо тільки результати тестів та підсумки
                if (line.contains("Tests run:") || line.contains("OK (") || 
                    line.contains("FAILURES!!!") || line.contains("Test") ||
                    line.contains("✓") || line.contains("✗") ||
                    line.contains("Компіляція") || line.contains("Запуск")) {
                    System.out.println(line);
                    showLine = true;
                } else if (line.trim().isEmpty() && showLine) {
                    System.out.println();
                    showLine = false;
                }
            }

            int exitCode = process.waitFor();

            System.out.println();
            if (exitCode == 0) {
                System.out.println("✅ УСПІШНО - всі тести пройдено!");
                logger.info(CLASS_NAME, "Юніт-тести пройдено: " + testName);
            } else {
                System.out.println("❌ ПРОВАЛЕНО - є помилки в тестах");
                logger.warn(CLASS_NAME, "Юніт-тести провалились: " + testName);
            }

        } catch (Exception e) {
            System.out.println("\n❌ Помилка: " + e.getMessage());
            System.out.println("💡 Спробуйте: ./test_all.sh");
            logger.error(CLASS_NAME, "Помилка запуску юніт-тестів: " + e.getMessage(), e);
        }

        System.out.println();
        validator.waitForEnter();
    }

    private String getTestPath(int choice) {
        switch (choice) {
            case 1: return "";  // Всі тести
            case 2: return "model";
            case 3: return "service";
            case 4: return "util";
            default: return "";
        }
    }

    private String getTestName(int choice) {
        switch (choice) {
            case 1: return "Всі тести";
            case 2: return "Тести моделей";
            case 3: return "Тести сервісів";
            case 4: return "Тести утиліт";
            default: return "Всі тести";
        }
    }
}

