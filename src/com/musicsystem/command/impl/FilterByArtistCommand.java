package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.model.MusicComposition;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.InputValidator;

import java.util.List;

public class FilterByArtistCommand implements Command {
    private MusicCollection collection;
    private InputValidator validator;

    public FilterByArtistCommand(MusicCollection collection, InputValidator validator) {
        this.collection = collection;
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== ФІЛЬТРУВАННЯ ЗА ВИКОНАВЦЕМ ===\n");

        if (collection.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }

        String artist = validator.readNonEmptyString("Введіть ім'я виконавця (або частину): ");

        List<MusicComposition> filtered = collection.filterByArtist(artist);

        if (filtered.isEmpty()) {
            System.out.println("\nКомпозицій виконавця \"" + artist + "\" не знайдено.");
            return;
        }

        System.out.println("\nЗнайдено композицій: " + filtered.size());

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