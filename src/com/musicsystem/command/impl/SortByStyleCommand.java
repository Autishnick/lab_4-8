package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.model.Compilation;
import com.musicsystem.model.MusicComposition;
import com.musicsystem.service.CompilationManager;
import com.musicsystem.util.InputValidator;

import java.util.List;

public class SortByStyleCommand implements Command {
    private CompilationManager compilationManager;
    private InputValidator validator;

    public SortByStyleCommand(CompilationManager compilationManager, InputValidator validator) {
        this.compilationManager = compilationManager;
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== СОРТУВАННЯ ЗБІРКИ ЗА СТИЛЕМ ===\n");

        if (compilationManager.isEmpty()) {
            System.out.println("Немає створених збірок.");
            return;
        }

        List<Compilation> compilations = compilationManager.getAll();
        System.out.println("Збірки:\n");
        for (int i = 0; i < compilations.size(); i++) {
            System.out.println((i + 1) + ". " + compilations.get(i));
        }

        int choice = validator.readInt("\nОберіть збірку для сортування (0 для скасування): ",
                0, compilations.size());

        if (choice == 0) {
            System.out.println("Сортування скасовано.");
            return;
        }

        Compilation compilation = compilations.get(choice - 1);

        if (compilation.getTrackCount() == 0) {
            System.out.println("\nЗбірка порожня.");
            return;
        }

        System.out.println("\n--- До сортування ---");
        displayTracks(compilation);

        try {
            compilationManager.sortCompilationByStyle(compilation.getId());

            System.out.println("\n--- Після сортування ---");
            displayTracks(compilation);

            System.out.println("\n✓ Збірку відсортовано за стилем!");

        } catch (Exception e) {
            System.out.println("\n✗ Помилка сортування: " + e.getMessage());
        }

        validator.waitForEnter();
    }

    private void displayTracks(Compilation compilation) {
        List<MusicComposition> tracks = compilation.getTracks();
        for (int i = 0; i < tracks.size(); i++) {
            System.out.println((i + 1) + ". " + tracks.get(i).getInfo());
        }
    }
}