package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.model.Compilation;
import com.musicsystem.model.MusicComposition;
import com.musicsystem.service.CompilationManager;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class CreateCompilationCommand implements Command {
    private MusicCollection collection;
    private CompilationManager compilationManager;
    private InputValidator validator;

    public CreateCompilationCommand(MusicCollection collection,
                                    CompilationManager compilationManager,
                                    InputValidator validator) {
        this.collection = collection;
        this.compilationManager = compilationManager;
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== СТВОРЕННЯ ЗБІРКИ ===\n");

        if (collection.isEmpty()) {
            System.out.println("Колекція порожня. Спочатку додайте композиції.");
            return;
        }

        String name = validator.readNonEmptyString("Назва збірки: ");
        String description = validator.readString("Опис збірки (опціонально): ");

        try {
            Compilation compilation = compilationManager.create(name, description);

            System.out.println("\nДодати композиції до збірки?");
            boolean addTracks = validator.readBoolean("Додати зараз?");

            if (addTracks) {
                addTracksToCompilation(compilation);
            }

            System.out.println("\n✓ Збірку успішно створено!");
            System.out.println(compilation.getInfo());

        } catch (Exception e) {
            System.out.println("\n✗ Помилка створення збірки: " + e.getMessage());
        }
    }

    private void addTracksToCompilation(Compilation compilation) {
        List<MusicComposition> availableCompositions = collection.getAll();

        while (true) {
            System.out.println("\n--- Доступні композиції ---");
            for (int i = 0; i < availableCompositions.size(); i++) {
                MusicComposition comp = availableCompositions.get(i);
                System.out.println((i + 1) + ". " + comp.getInfo());
            }

            int choice = validator.readInt("\nОберіть композицію для додавання (0 для завершення): ",
                    0, availableCompositions.size());

            if (choice == 0) {
                break;
            }

            MusicComposition selected = availableCompositions.get(choice - 1);
            compilation.addTrack(selected);
            System.out.println("✓ Додано: " + selected.getTitle());

            System.out.println("\nПоточна збірка: " + compilation.getTrackCount() + " треків, "
                    + compilation.getFormattedDuration());

            boolean continueAdding = validator.readBoolean("\nДодати ще композиції?");
            if (!continueAdding) {
                break;
            }
        }
    }
}