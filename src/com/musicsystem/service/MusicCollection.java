package com.musicsystem.service;

import com.musicsystem.model.MusicComposition;
import com.musicsystem.model.MusicStyle;
import com.musicsystem.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MusicCollection {
    private static final String CLASS_NAME = "MusicCollection";
    private static final Logger logger = Logger.getInstance();

    private List<MusicComposition> compositions;

    public MusicCollection() {
        this.compositions = new ArrayList<>();
    }

    public void add(MusicComposition composition) {
        if (composition != null && composition.validate()) {
            compositions.add(composition);
            logger.info(CLASS_NAME, "Додано композицію: " + composition.getTitle() + " (ID: " + composition.getId() + ")");
        } else {
            logger.error(CLASS_NAME, "Спроба додати невалідну композицію");
            throw new IllegalArgumentException("Невалідна композиція");
        }
    }

    public boolean remove(MusicComposition composition) {
        boolean removed = compositions.remove(composition);
        if (removed) {
            logger.info(CLASS_NAME, "Видалено композицію: " + composition.getTitle() + " (ID: " + composition.getId() + ")");
        } else {
            logger.warn(CLASS_NAME, "Спроба видалити неіснуючу композицію");
        }
        return removed;
    }

    public boolean remove(int id) {
        boolean removed = compositions.removeIf(c -> c.getId() == id);
        if (removed) {
            logger.info(CLASS_NAME, "Видалено композицію по ID: " + id);
        }
        return removed;
    }

    public MusicComposition getById(int id) {
        return compositions.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<MusicComposition> getAll() {
        return new ArrayList<>(compositions);
    }

    public List<MusicComposition> search(String query) {
        String lowerQuery = query.toLowerCase();
        return compositions.stream()
                .filter(c -> c.getTitle().toLowerCase().contains(lowerQuery) ||
                        c.getArtist().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    public List<MusicComposition> filterByStyle(MusicStyle style) {
        return compositions.stream()
                .filter(c -> c.getStyle() == style)
                .collect(Collectors.toList());
    }

    public List<MusicComposition> filterByArtist(String artist) {
        String lowerArtist = artist.toLowerCase();
        return compositions.stream()
                .filter(c -> c.getArtist().toLowerCase().contains(lowerArtist))
                .collect(Collectors.toList());
    }

    public List<MusicComposition> findByDuration(int minSeconds, int maxSeconds) {
        return compositions.stream()
                .filter(c -> c.getDurationSeconds() >= minSeconds &&
                        c.getDurationSeconds() <= maxSeconds)
                .collect(Collectors.toList());
    }

    public int getTotalCount() {
        return compositions.size();
    }

    public int getTotalDuration() {
        return compositions.stream()
                .mapToInt(MusicComposition::getDurationSeconds)
                .sum();
    }

    public void clear() {
        compositions.clear();
    }

    public boolean isEmpty() {
        return compositions.isEmpty();
    }

    public void update(MusicComposition composition) {
        if (composition == null || !composition.validate()) {
            throw new IllegalArgumentException("Невалідна композиція");
        }

        for (int i = 0; i < compositions.size(); i++) {
            if (compositions.get(i).getId() == composition.getId()) {
                compositions.set(i, composition);
                return;
            }
        }
        throw new IllegalArgumentException("Композицію не знайдено");
    }
}