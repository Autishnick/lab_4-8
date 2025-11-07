package com.musicsystem.service;

import com.musicsystem.model.MusicComposition;
import com.musicsystem.model.MusicStyle;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Statistics {
    private MusicCollection collection;

    public Statistics(MusicCollection collection) {
        this.collection = collection;
    }

    public int getTotalCount() {
        return collection.getTotalCount();
    }

    public int getTotalDuration() {
        return collection.getTotalDuration();
    }

    public String getFormattedTotalDuration() {
        int totalSeconds = getTotalDuration();
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        return String.format("%d год %d хв %d сек", hours, minutes, seconds);
    }

    public Map<MusicStyle, Integer> getStyleDistribution() {
        Map<MusicStyle, Integer> distribution = new HashMap<>();

        for (MusicComposition composition : collection.getAll()) {
            MusicStyle style = composition.getStyle();
            distribution.put(style, distribution.getOrDefault(style, 0) + 1);
        }

        return distribution.entrySet().stream()
                .sorted(Map.Entry.<MusicStyle, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public Map<String, Integer> getArtistStatistics() {
        Map<String, Integer> artistStats = new HashMap<>();

        for (MusicComposition composition : collection.getAll()) {
            String artist = composition.getArtist();
            artistStats.put(artist, artistStats.getOrDefault(artist, 0) + 1);
        }

        return artistStats.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public Map<String, Integer> getArtistDurationStatistics() {
        Map<String, Integer> artistDuration = new HashMap<>();

        for (MusicComposition composition : collection.getAll()) {
            String artist = composition.getArtist();
            artistDuration.put(artist,
                    artistDuration.getOrDefault(artist, 0) + composition.getDurationSeconds());
        }

        return artistDuration;
    }

    public double getAverageDuration() {
        if (collection.isEmpty()) {
            return 0.0;
        }
        return (double) getTotalDuration() / getTotalCount();
    }

    public MusicComposition getLongestTrack() {
        return collection.getAll().stream()
                .max((c1, c2) -> Integer.compare(c1.getDurationSeconds(), c2.getDurationSeconds()))
                .orElse(null);
    }

    public MusicComposition getShortestTrack() {
        return collection.getAll().stream()
                .min((c1, c2) -> Integer.compare(c1.getDurationSeconds(), c2.getDurationSeconds()))
                .orElse(null);
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("===========================================\n");
        report.append("       СТАТИСТИКА МУЗИЧНОЇ КОЛЕКЦІЇ\n");
        report.append("===========================================\n\n");

        report.append("Загальна кількість композицій: ").append(getTotalCount()).append("\n");
        report.append("Загальна тривалість: ").append(getFormattedTotalDuration()).append("\n");
        report.append("Середня тривалість треку: ").append(formatSeconds((int)getAverageDuration())).append("\n\n");

        MusicComposition longest = getLongestTrack();
        if (longest != null) {
            report.append("Найдовший трек: ").append(longest.getTitle())
                    .append(" - ").append(longest.getFormattedDuration()).append("\n");
        }

        MusicComposition shortest = getShortestTrack();
        if (shortest != null) {
            report.append("Найкоротший трек: ").append(shortest.getTitle())
                    .append(" - ").append(shortest.getFormattedDuration()).append("\n\n");
        }

        report.append("--- Розподіл за стилями ---\n");
        Map<MusicStyle, Integer> styleDistribution = getStyleDistribution();
        for (Map.Entry<MusicStyle, Integer> entry : styleDistribution.entrySet()) {
            report.append(String.format("  %s: %d треків (%.1f%%)\n",
                    entry.getKey(), entry.getValue(),
                    (entry.getValue() * 100.0 / getTotalCount())));
        }

        report.append("\n--- Топ-10 виконавців ---\n");
        Map<String, Integer> artistStats = getArtistStatistics();
        int rank = 1;
        for (Map.Entry<String, Integer> entry : artistStats.entrySet()) {
            report.append(String.format("  %d. %s: %d треків\n",
                    rank++, entry.getKey(), entry.getValue()));
        }

        report.append("\n===========================================\n");
        return report.toString();
    }

    private String formatSeconds(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%d:%02d", minutes, secs);
    }
}