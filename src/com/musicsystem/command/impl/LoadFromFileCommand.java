package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.FileManager;
import com.musicsystem.util.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadFromFileCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoadFromFileCommand.class);
    
    private MusicCollection collection;
    private FileManager fileManager;
    private InputValidator validator;

    public LoadFromFileCommand(MusicCollection collection,
                               FileManager fileManager,
                               InputValidator validator) {
        this.collection = collection;
        this.fileManager = fileManager;
        this.validator = validator;
    }

    @Override
    public void execute() {
        logger.info("Виконання команди завантаження з файлу");
        System.out.println("\n=== ЗАВАНТАЖЕННЯ З ФАЙЛУ ===\n");

        System.out.println("1. Завантажити з файлу за замовчуванням (data/compositions.txt)");
        System.out.println("2. Вказати інший файл");
        System.out.println("0. Скасувати");

        int choice = validator.readInt("Ваш вибір: ", 0, 2);

        if (choice == 0) {
            logger.info("Користувач скасував завантаження");
            System.out.println("Завантаження скасовано.");
            return;
        }

        try {
            if (choice == 1) {
                if (!fileManager.defaultFileExists()) {
                    logger.warn("Файл за замовчуванням не існує");
                    System.out.println("Файл за замовчуванням не існує.");
                    boolean create = validator.readBoolean("Створити файл з прикладами?");
                    if (create) {
                        fileManager.createDefaultFile();
                    } else {
                        logger.info("Користувач відмовився від створення файлу за замовчуванням");
                        return;
                    }
                }

                logger.info("Завантаження з файлу за замовчуванням");
                fileManager.loadFromDefaultFile(collection);
                System.out.println("\n✓ Дані успішно завантажено з файлу за замовчуванням!");

            } else {
                String filename = validator.readNonEmptyString("Введіть шлях до файлу: ");
                logger.info("Завантаження з файлу: " + filename);
                fileManager.loadFromFile(collection, filename);
                System.out.println("\n✓ Дані успішно завантажено з файлу: " + filename);
            }

            System.out.println("Завантажено композицій: " + collection.getTotalCount());

        } catch (Exception e) {
            logger.error("Помилка завантаження файлу: " + e.getMessage(), e);
            System.out.println("\n✗ Помилка завантаження: " + e.getMessage());
        }

        validator.waitForEnter();
    }
}