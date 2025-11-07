package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.model.Compilation;
import com.musicsystem.model.MusicComposition;
import com.musicsystem.service.CompilationManager;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.InputValidator;

import java.util.List;

public class EditCompilationCommand implements Command {
    private MusicCollection collection;
    private CompilationManager compilationManager;
    private InputValidator validator;

    public EditCompilationCommand(MusicCollection collection,
                                  CompilationManager compilationManager,
                                  InputValidator validator) {
        this.collection = collection;
        this.compilationManager = compilationManager;
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== РЕДАГУВАННЯ ЗБІРКИ ===\n");

        if (compilationManager.isEmpty()) {
            System.out.println("Немає створених збірок.");
            return;
        }

        List<Compilation> compilations = compilationManager.getAll();
        System.out.println("Збірки:\n");
        for (int i = 0; i < compilations.size(); i++) {
            System.out.println((i + 1) + ". " + compilations.get(i));
        }

        int choice = validator.readInt("\nОберіть збірку для редагування (0 для скасування): ",
                0, compilations.size());

        if (choice == 0) {
            System.out.println("Редагування скасовано.");
            return;
        }

        Compilation compilation = compilations.get(choice - 1);

        boolean editing = true;
        while (editing) {
            System.out.println("\n" + compilation.getInfo());
            System.out.println("\n--- Меню редагування ---");
            System.out.println("1. Перейменувати збірку");
            System.out.println("2. Змінити опис");
            System.out.println("3. Додати композицію");
            System.out.println("4. Видалити композицію");
            System.out.println("5. Переглянути треки");
            System.out.println("0. Завершити редагування");

            int action = validator.readInt("Ваш вибір: ", 0, 5);

            try {
                switch (action) {
                    case 1:
                        String newName = validator.readNonEmptyString("Нова назва: ");
                        compilation.setName(newName);
                        System.out.println("✓ Назву оновлено");
                        break;

                    case 2:
                        String newDescription = validator.readString("Новий опис: ");
                        compilation.setDescription(newDescription);
                        System.out.println("✓ Опис оновлено");
                        break;

                    case 3:
                        addTrack(compilation);
                        break;

                    case 4:
                        removeTrack(compilation);
                        break;

                    case 5:
                        viewTracks(compilation);
                        break;

                    case 0:
                        editing = false;
                        compilationManager.updateCompilation(compilation);
                        System.out.println("\n✓ Зміни збережено!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("\n✗ Помилка: " + e.getMessage());
            }
        }
    }

    private void addTrack(Compilation compilation) {
        if (collection.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }

        System.out.println("\nДоступні композиції:");
        List<MusicComposition> available = collection.getAll();
        for (int i = 0; i < available.size(); i++) {
            System.out.println((i + 1) + ". " + available.get(i).getInfo());
        }

        int choice = validator.readInt("\nОберіть композицію (0 для скасування): ",
                0, available.size());

        if (choice > 0) {
            MusicComposition track = available.get(choice - 1);
            compilation.addTrack(track);
            System.out.println("✓ Композицію додано");
            System.out.println("Тривалість збірки: " + compilation.getFormattedDuration());
        }
    }

    private void removeTrack(Compilation compilation) {
        List<MusicComposition> tracks = compilation.getTracks();

        if (tracks.isEmpty()) {
            System.out.println("Збірка порожня.");
            return;
        }

        System.out.println("\nТреки у збірці:");
        for (int i = 0; i < tracks.size(); i++) {
            System.out.println((i + 1) + ". " + tracks.get(i).getInfo());
        }

        int choice = validator.readInt("\nОберіть трек для видалення (0 для скасування): ",
                0, tracks.size());

        if (choice > 0) {
            MusicComposition track = tracks.get(choice - 1);
            compilation.removeTrack(track);
            System.out.println("✓ Трек видалено");
            System.out.println("Тривалість збірки: " + compilation.getFormattedDuration());
        }
    }

    private void viewTracks(Compilation compilation) {
        List<MusicComposition> tracks = compilation.getTracks();

        if (tracks.isEmpty()) {
            System.out.println("\nЗбірка порожня.");
            return;
        }

        System.out.println("\n--- Треки у збірці ---");
        for (int i = 0; i < tracks.size(); i++) {
            System.out.println((i + 1) + ". " + tracks.get(i).getInfo());
        }
        System.out.println("\nЗагалом: " + tracks.size() + " треків, " + compilation.getFormattedDuration());
        validator.waitForEnter();
    }
}