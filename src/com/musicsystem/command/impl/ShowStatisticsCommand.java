package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.service.Statistics;
import com.musicsystem.util.InputValidator;

public class ShowStatisticsCommand implements Command {
    private MusicCollection collection;
    private InputValidator validator;

    public ShowStatisticsCommand(MusicCollection collection, InputValidator validator) {
        this.collection = collection;
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== СТАТИСТИКА КОЛЕКЦІЇ ===\n");

        if (collection.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }

        Statistics statistics = new Statistics(collection);
        String report = statistics.generateReport();

        System.out.println(report);

        validator.waitForEnter();
    }
}