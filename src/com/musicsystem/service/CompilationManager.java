package com.musicsystem.service;

import java.util.ArrayList;
import java.util.List;

import com.musicsystem.model.Compilation;
import com.musicsystem.model.MusicComposition;
import com.musicsystem.util.Logger;

public class CompilationManager {
    private static final String CLASS_NAME = "CompilationManager";
    private static final Logger logger = Logger.getInstance();

    private List<Compilation> compilations;

    public CompilationManager() {
        this.compilations = new ArrayList<>();
    }

    public Compilation create(String name, String description) {
        logger.info(CLASS_NAME, "Створення нової збірки: " + name);

        if (name == null || name.trim().isEmpty()) {
            logger.error(CLASS_NAME, "Спроба створити збірку з порожньою назвою");
            throw new IllegalArgumentException("Назва збірки не може бути порожньою");
        }

        Compilation compilation = new Compilation(name, description);
        compilations.add(compilation);
        logger.info(CLASS_NAME, "✓ Збірку успішно створено: " + name + " (ID: " + compilation.getId() + ")");
        return compilation;
    }

    public boolean delete(int id) {
        logger.info(CLASS_NAME, "Видалення збірки з ID: " + id);
        boolean deleted = compilations.removeIf(c -> c.getId() == id);

        if (deleted) {
            logger.info(CLASS_NAME, "✓ Збірку успішно видалено (ID: " + id + ")");
        } else {
            logger.warn(CLASS_NAME, "Збірку з ID " + id + " не знайдено");
        }

        return deleted;
    }

    public Compilation getById(int id) {
        return compilations.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Compilation> getAll() {
        return new ArrayList<>(compilations);
    }

    public void addTrackToCompilation(int compilationId, MusicComposition track) {
        logger.info(CLASS_NAME, "Додавання треку '" + track.getTitle() + "' до збірки ID: " + compilationId);

        Compilation compilation = getById(compilationId);
        if (compilation == null) {
            logger.error(CLASS_NAME, "Збірку з ID " + compilationId + " не знайдено");
            throw new IllegalArgumentException("Збірку не знайдено");
        }

        compilation.addTrack(track);
        logger.info(CLASS_NAME, "✓ Трек успішно додано до збірки");
    }

    public void removeTrackFromCompilation(int compilationId, int trackId) {
        logger.info(CLASS_NAME, "Видалення треку ID: " + trackId + " зі збірки ID: " + compilationId);

        Compilation compilation = getById(compilationId);
        if (compilation == null) {
            logger.error(CLASS_NAME, "Збірку з ID " + compilationId + " не знайдено");
            throw new IllegalArgumentException("Збірку не знайдено");
        }

        boolean removed = compilation.removeTrack(trackId);
        if (removed) {
            logger.info(CLASS_NAME, "✓ Трек успішно видалено зі збірки");
        } else {
            logger.warn(CLASS_NAME, "Трек з ID " + trackId + " не знайдено у збірці");
        }
    }

    public void sortCompilationByStyle(int compilationId) {
        logger.info(CLASS_NAME, "Сортування збірки ID: " + compilationId + " за стилем");

        Compilation compilation = getById(compilationId);
        if (compilation == null) {
            logger.error(CLASS_NAME, "Збірку з ID " + compilationId + " не знайдено");
            throw new IllegalArgumentException("Збірку не знайдено");
        }

        compilation.sortByStyle();
        logger.info(CLASS_NAME, "✓ Збірку успішно відсортовано за стилем");
    }

    public void updateCompilation(Compilation compilation) {
        logger.info(CLASS_NAME, "Оновлення збірки ID: " + (compilation != null ? compilation.getId() : "null"));

        if (compilation == null) {
            logger.error(CLASS_NAME, "Спроба оновити null збірку");
            throw new IllegalArgumentException("Збірка не може бути null");
        }

        for (int i = 0; i < compilations.size(); i++) {
            if (compilations.get(i).getId() == compilation.getId()) {
                compilations.set(i, compilation);
                logger.info(CLASS_NAME, "✓ Збірку успішно оновлено: " + compilation.getName());
                return;
            }
        }

        logger.error(CLASS_NAME, "Збірку з ID " + compilation.getId() + " не знайдено для оновлення");
        throw new IllegalArgumentException("Збірку не знайдено");
    }

    public int getTotalCount() {
        return compilations.size();
    }

    public void clear() {
        int count = compilations.size();
        compilations.clear();
        logger.info(CLASS_NAME, "Очищено список збірок (видалено " + count + " збірок)");
    }

    public boolean isEmpty() {
        return compilations.isEmpty();
    }
}