package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.model.MusicComposition;
import com.musicsystem.model.MusicStyle;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.InputValidator;

public class EditCompositionCommand implements Command {
    private MusicCollection collection;
    private InputValidator validator;

    public EditCompositionCommand(MusicCollection collection, InputValidator validator) {
        this.collection = collection;
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== РЕДАГУВАННЯ КОМПОЗИЦІЇ ===\n");

        if (collection.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }

        System.out.println("Композиції в колекції:\n");
        int index = 1;
        for (MusicComposition composition : collection.getAll()) {
            System.out.println(index++ + ". " + composition.getInfo());
        }

        int choice = validator.readInt("\nОберіть номер композиції для редагування (0 для скасування): ",
                0, collection.getAll().size());

        if (choice == 0) {
            System.out.println("Редагування скасовано.");
            return;
        }

        MusicComposition composition = collection.getAll().get(choice - 1);

        System.out.println("\nПоточна інформація:");
        System.out.println(composition.getInfo());
        System.out.println("\nЩо редагувати?");
        System.out.println("1. Назву");
        System.out.println("2. Виконавця");
        System.out.println("3. Стиль");
        System.out.println("4. Тривалість");
        System.out.println("5. Рік");
        System.out.println("0. Назад");

        int editChoice = validator.readInt("Ваш вибір: ", 0, 5);

        try {
            switch (editChoice) {
                case 1:
                    String newTitle = validator.readNonEmptyString("Нова назва: ");
                    composition.setTitle(newTitle);
                    break;

                case 2:
                    String newArtist = validator.readNonEmptyString("Новий виконавець: ");
                    composition.setArtist(newArtist);
                    break;

                case 3:
                    System.out.println("\nОберіть новий стиль:");
                    MusicStyle[] styles = MusicStyle.values();
                    for (int i = 0; i < styles.length; i++) {
                        System.out.println((i + 1) + ". " + styles[i]);
                    }
                    int styleIndex = validator.readInt("Ваш вибір: ", 1, styles.length) - 1;
                    composition.setStyle(styles[styleIndex]);
                    break;

                case 4:
                    int newDuration = validator.readDuration("Нова тривалість (MM:SS або HH:MM:SS): ");
                    composition.setDurationSeconds(newDuration);
                    break;

                case 5:
                    int newYear = validator.readInt("Новий рік: ", 1900, 2100);
                    composition.setYear(newYear);
                    break;

                case 0:
                    System.out.println("Редагування скасовано.");
                    return;
            }

            collection.update(composition);
            System.out.println("\n✓ Композицію успішно оновлено!");
            System.out.println(composition.getInfo());

        } catch (Exception e) {
            System.out.println("\n✗ Помилка редагування: " + e.getMessage());
        }
    }
}