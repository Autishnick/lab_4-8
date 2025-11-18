package com.musicsystem.service;

import java.util.ArrayList;
import java.util.List;

import com.musicsystem.model.Compilation;
import com.musicsystem.model.MusicComposition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompilationManager {
    private static final Logger logger = LogManager.getLogger(CompilationManager.class);

    private List<Compilation> compilations;

    public CompilationManager() {
        this.compilations = new ArrayList<>();
    }

    public Compilation create(String name, String description) {
        logger.info("Створення нової збірки: " + name);

        if (name == null || name.trim().isEmpty()) {
            logger.error("Спроба створити збірку з порожньою назвою");
            throw new IllegalArgumentException("Назва збірки не може бути порожньою");
        }

        Compilation compilation = new Compilation(name, description);
        compilations.add(compilation);
        logger.info("✓ Збірку успішно створено: " + name + " (ID: " + compilation.getId() + ")");
        return compilation;
    }

    public boolean delete(int id) {
        logger.info("Видалення збірки з ID: " + id);
        boolean deleted = compilations.removeIf(c -> c.getId() == id);

        if (deleted) {
            logger.info("✓ Збірку успішно видалено (ID: " + id + ")");
        } else {
            logger.warn("Збірку з ID " + id + " не знайдено");
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
        logger.info("Додавання треку '" + track.getTitle() + "' до збірки ID: " + compilationId);

        Compilation compilation = getById(compilationId);
        if (compilation == null) {
            logger.error("Збірку з ID " + compilationId + " не знайдено");
            throw new IllegalArgumentException("Збірку не знайдено");
        }

        compilation.addTrack(track);
        logger.info("✓ Трек успішно додано до збірки");
    }

    public void removeTrackFromCompilation(int compilationId, int trackId) {
        logger.info("Видалення треку ID: " + trackId + " зі збірки ID: " + compilationId);

        Compilation compilation = getById(compilationId);
        if (compilation == null) {
            logger.error("Збірку з ID " + compilationId + " не знайдено");
            throw new IllegalArgumentException("Збірку не знайдено");
        }

        boolean removed = compilation.removeTrack(trackId);
        if (removed) {
            logger.info("✓ Трек успішно видалено зі збірки");
        } else {
            logger.warn("Трек з ID " + trackId + " не знайдено у збірці");
        }
    }

    public void sortCompilationByStyle(int compilationId) {
        logger.info("Сортування збірки ID: " + compilationId + " за стилем");

        Compilation compilation = getById(compilationId);
        if (compilation == null) {
            logger.error("Збірку з ID " + compilationId + " не знайдено");
            throw new IllegalArgumentException("Збірку не знайдено");
        }

        compilation.sortByStyle();
        logger.info("✓ Збірку успішно відсортовано за стилем");
    }

    public void updateCompilation(Compilation compilation) {
        logger.info("Оновлення збірки ID: " + (compilation != null ? compilation.getId() : "null"));

        if (compilation == null) {
            logger.error("Спроба оновити null збірку");
            throw new IllegalArgumentException("Збірка не може бути null");
        }

        for (int i = 0; i < compilations.size(); i++) {
            if (compilations.get(i).getId() == compilation.getId()) {
                compilations.set(i, compilation);
                logger.info("✓ Збірку успішно оновлено: " + compilation.getName());
                return;
            }
        }

        logger.error("Збірку з ID " + compilation.getId() + " не знайдено для оновлення");
        throw new IllegalArgumentException("Збірку не знайдено");
    }

    public int getTotalCount() {
        return compilations.size();
    }

    public void clear() {
        int count = compilations.size();
        compilations.clear();
        logger.info("Очищено список збірок (видалено " + count + " збірок)");
    }

    public boolean isEmpty() {
        return compilations.isEmpty();
    }
}