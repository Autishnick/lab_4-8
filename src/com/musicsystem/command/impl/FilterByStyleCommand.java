package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.model.MusicComposition;
import com.musicsystem.model.MusicStyle;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.InputValidator;

import java.util.List;

public class FilterByStyleCommand implements Command {
    private MusicCollection collection;
    private InputValidator validator;

    public FilterByStyleCommand(MusicCollection collection, InputValidator validator) {
        this.collection = collection;
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== ФІЛЬТРУВАННЯ ЗА СТИЛЕМ ===\n");

        if (collection.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }

        System.out.println("Оберіть стиль:");
        MusicStyle[] styles = MusicStyle.values();
        for (int i = 0; i < styles.length; i++) {
            System.out.println((i + 1) + ". " + styles[i]);
        }

        int choice = validator.readInt("Ваш вибір: ", 1, styles.length);
        MusicStyle selectedStyle = styles[choice - 1];

        List<MusicComposition> filtered = collection.filterByStyle(selectedStyle);

        if (filtered.isEmpty()) {
            System.out.println("\nКомпозицій стилю \"" + selectedStyle + "\" не знайдено.");
            return;
        }

        System.out.println("\nЗнайдено композицій стилю \"" + selectedStyle + "\": " + filtered.size());

        int totalDuration = filtered.stream()
                .mapToInt(MusicComposition::getDurationSeconds)
                .sum();
        System.out.println("Загальна тривалість: " + formatDuration(totalDuration));

        System.out.println("\n" + "=".repeat(100));
        for (MusicComposition composition : filtered) {
            System.out.println(composition.getInfo());
        }
        System.out.println("=".repeat(100));

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
}