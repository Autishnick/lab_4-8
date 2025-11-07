package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.model.MusicComposition;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.InputValidator;

import java.util.List;

public class ViewCollectionCommand implements Command {
    private MusicCollection collection;
    private InputValidator validator;

    public ViewCollectionCommand(MusicCollection collection, InputValidator validator) {
        this.collection = collection;
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== ПЕРЕГЛЯД КОЛЕКЦІЇ ===\n");

        if (collection.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }

        List<MusicComposition> compositions = collection.getAll();

        System.out.println("Загальна кількість композицій: " + compositions.size());
        System.out.println("Загальна тривалість: " + formatDuration(collection.getTotalDuration()));
        System.out.println("\n" + "=".repeat(120));
        System.out.printf("%-5s %-10s %-30s %-25s %-15s %-12s %-6s\n",
                "ID", "Тип", "Назва", "Виконавець", "Стиль", "Тривалість", "Рік");
        System.out.println("=".repeat(120));

        for (MusicComposition composition : compositions) {
            System.out.printf("%-5d %-10s %-30s %-25s %-15s %-12s %-6d\n",
                    composition.getId(),
                    composition.getType(),
                    truncate(composition.getTitle(), 30),
                    truncate(composition.getArtist(), 25),
                    composition.getStyle(),
                    composition.getFormattedDuration(),
                    composition.getYear());
        }

        System.out.println("=".repeat(120));
        validator.waitForEnter();
    }

    private String formatDuration(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        if (hours > 0) {
            return String.format("%d год %d хв %d сек", hours, minutes, secs);
        } else {
            return String.format("%d хв %d сек", minutes, secs);
        }
    }

    private String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
}