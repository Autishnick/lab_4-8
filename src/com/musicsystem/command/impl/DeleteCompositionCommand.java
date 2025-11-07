package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.model.MusicComposition;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.InputValidator;

public class DeleteCompositionCommand implements Command {
    private MusicCollection collection;
    private InputValidator validator;

    public DeleteCompositionCommand(MusicCollection collection, InputValidator validator) {
        this.collection = collection;
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== ВИДАЛЕННЯ КОМПОЗИЦІЇ ===\n");

        if (collection.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }

        System.out.println("Композиції в колекції:\n");
        int index = 1;
        for (MusicComposition composition : collection.getAll()) {
            System.out.println(index++ + ". " + composition.getInfo());
        }

        int choice = validator.readInt("\nОберіть номер композиції для видалення (0 для скасування): ",
                0, collection.getAll().size());

        if (choice == 0) {
            System.out.println("Видалення скасовано.");
            return;
        }

        MusicComposition toDelete = collection.getAll().get(choice - 1);
        boolean confirm = validator.readBoolean("\nВидалити композицію \"" + toDelete.getTitle() + "\"?");

        if (confirm) {
            collection.remove(toDelete);
            System.out.println("\n✓ Композицію успішно видалено!");
        } else {
            System.out.println("\nВидалення скасовано.");
        }
    }
}