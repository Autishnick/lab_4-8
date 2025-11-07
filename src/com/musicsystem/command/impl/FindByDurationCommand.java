package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.model.MusicComposition;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.InputValidator;

import java.util.List;

public class FindByDurationCommand implements Command {
    private MusicCollection collection;
    private InputValidator validator;

    public FindByDurationCommand(MusicCollection collection, InputValidator validator) {
        this.collection = collection;
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== ПОШУК ЗА ТРИВАЛІСТЮ ===\n");

        if (collection.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }

        int minDuration = validator.readDuration("Мінімальна тривалість (MM:SS): ");
        int maxDuration = validator.readDuration("Максимальна тривалість (MM:SS): ");

        if (minDuration > maxDuration) {
            System.out.println("\n✗ Помилка: мінімальна тривалість не може бути більшою за максимальну.");
            return;
        }

        List<MusicComposition> found = collection.findByDuration(minDuration, maxDuration);

        if (found.isEmpty()) {
            System.out.println("\nКомпозицій у заданому діапазоні не знайдено.");
            return;
        }

        System.out.println("\nЗнайдено композицій: " + found.size());

        int totalDuration = found.stream()
                .mapToInt(MusicComposition::getDurationSeconds)
                .sum();
        System.out.println("Загальна тривалість: " + formatDuration(totalDuration));

        System.out.println("\n" + "=".repeat(100));
        for (MusicComposition composition : found) {
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