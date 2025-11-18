package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.FileManager;
import com.musicsystem.util.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveToFileCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SaveToFileCommand.class);
    
    private MusicCollection collection;
    private FileManager fileManager;
    private InputValidator validator;

    public SaveToFileCommand(MusicCollection collection,
                             FileManager fileManager,
                             InputValidator validator) {
        this.collection = collection;
        this.fileManager = fileManager;
        this.validator = validator;
    }

    @Override
    public void execute() {
        logger.info("Виконання команди збереження у файл");
        System.out.println("\n=== ЗБЕРЕЖЕННЯ У ФАЙЛ ===\n");

        if (collection.isEmpty()) {
            logger.warn("Спроба зберегти порожню колекцію");
            System.out.println("Колекція порожня. Нічого зберігати.");
            return;
        }

        System.out.println("Композицій у колекції: " + collection.getTotalCount());
        System.out.println("\n1. Зберегти у файл за замовчуванням (data/compositions.txt)");
        System.out.println("2. Вказати інший файл");
        System.out.println("0. Скасувати");

        int choice = validator.readInt("Ваш вибір: ", 0, 2);

        if (choice == 0) {
            logger.info("Користувач скасував збереження");
            System.out.println("Збереження скасовано.");
            return;
        }

        try {
            if (choice == 1) {
                logger.info("Збереження у файл за замовчуванням");
                fileManager.saveToDefaultFile(collection);
                System.out.println("\n✓ Дані успішно збережено у файл за замовчуванням!");

            } else {
                String filename = validator.readNonEmptyString("Введіть шлях до файлу: ");
                logger.info("Збереження у файл: " + filename);
                fileManager.saveToFile(collection, filename);
                System.out.println("\n✓ Дані успішно збережено у файл: " + filename);
            }

            System.out.println("Збережено композицій: " + collection.getTotalCount());

        } catch (Exception e) {
            logger.error("Помилка збереження файлу: " + e.getMessage(), e);
            System.out.println("\n✗ Помилка збереження: " + e.getMessage());
        }

        validator.waitForEnter();
    }
}