package com.musicsystem.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.musicsystem.model.Instrumental;
import com.musicsystem.model.LiveVersion;
import com.musicsystem.model.MusicComposition;
import com.musicsystem.model.MusicStyle;
import com.musicsystem.model.Remix;
import com.musicsystem.model.Song;
import com.musicsystem.service.MusicCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileManager {
    private static final Logger logger = LogManager.getLogger(FileManager.class);
    private static final String DELIMITER = "\\|";
    private final String DEFAULT_FILE;

    public FileManager() {
        ConfigManager config = ConfigManager.getInstance();
        this.DEFAULT_FILE = config.getDataFilePath();
    }

    public void saveToFile(MusicCollection collection, String filename) throws IOException {
        logger.info("Початок збереження колекції у файл: " + filename);

        try {
            File file = new File(filename);
            file.getParentFile().mkdirs();

            int compositionCount = collection.getAll().size();
            logger.debug("Кількість композицій для збереження: " + compositionCount);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (MusicComposition composition : collection.getAll()) {
                    writer.write(composition.toFileString());
                    writer.newLine();
                }
            }

            logger.info("✓ Успішно збережено " + compositionCount + " композицій у файл: " + filename);

        } catch (IOException e) {
            logger.error("Помилка збереження колекції у файл: " + filename, e);
            throw e;
        } catch (Exception e) {
            logger.error("Неочікувана помилка при збереженні у файл: " + filename, e);
            throw new IOException("Неочікувана помилка збереження", e);
        }
    }

    public void saveToDefaultFile(MusicCollection collection) throws IOException {
        logger.info("Збереження у файл за замовчуванням: " + DEFAULT_FILE);
        saveToFile(collection, DEFAULT_FILE);
    }

    public void loadFromFile(MusicCollection collection, String filename) throws IOException {
        logger.info("Початок завантаження колекції з файлу: " + filename);

        File file = new File(filename);
        if (!file.exists()) {
            logger.error("Файл не знайдено: " + filename);
            throw new FileNotFoundException("Файл не знайдено: " + filename);
        }

        collection.clear();
        int loadedCount = 0;
        int errorCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty())
                    continue;

                try {
                    MusicComposition composition = parseComposition(line);
                    if (composition != null) {
                        collection.add(composition);
                        loadedCount++;
                        logger.debug("Завантажено композицію #" + lineNumber + ": " + composition.getTitle());
                    }
                } catch (Exception e) {
                    errorCount++;
                    String errorMsg = "Помилка парсингу рядка #" + lineNumber + ": " + e.getMessage();
                    logger.warn(errorMsg);
                    System.err.println(errorMsg);
                    System.err.println("Рядок: " + line);
                }
            }

            if (errorCount > 0) {
                logger.warn("Завантаження завершено з помилками. Завантажено: " + loadedCount + ", Помилок: " + errorCount);
            } else {
                logger.info("✓ Успішно завантажено " + loadedCount + " композицій з файлу: " + filename);
            }

        } catch (IOException e) {
            logger.error("Помилка читання файлу: " + filename, e);
            throw e;
        }
    }

    public void loadFromDefaultFile(MusicCollection collection) throws IOException {
        logger.info("Завантаження з файлу за замовчуванням: " + DEFAULT_FILE);
        loadFromFile(collection, DEFAULT_FILE);
    }

    private MusicComposition parseComposition(String line) {
        try {
            String[] parts = line.split(DELIMITER);

            if (parts.length < 7) {
                throw new IllegalArgumentException("Недостатньо даних у рядку (очікується мінімум 7 полів)");
            }

            int id = Integer.parseInt(parts[0]);
            String type = parts[1];
            String title = parts[2];
            String artist = parts[3];
            MusicStyle style = MusicStyle.fromString(parts[4]);
            int duration = Integer.parseInt(parts[5]);
            int year = Integer.parseInt(parts[6]);

            MusicComposition composition;

            switch (type) {
                case "SONG":
                    String lyrics = parts.length > 7 ? parts[7] : "";
                    boolean hasFeaturing = parts.length > 8 ? Boolean.parseBoolean(parts[8]) : false;
                    composition = new Song(title, artist, style, duration, year, lyrics, hasFeaturing);
                    break;

                case "INSTRUMENTAL":
                    List<String> instruments = new ArrayList<>();
                    if (parts.length > 7 && !parts[7].isEmpty()) {
                        instruments = Arrays.asList(parts[7].split(";"));
                    }
                    composition = new Instrumental(title, artist, style, duration, year, instruments);
                    break;

                case "REMIX":
                    String originalArtist = parts.length > 7 ? parts[7] : artist;
                    String remixer = parts.length > 8 ? parts[8] : "";
                    composition = new Remix(title, artist, style, duration, year, originalArtist, remixer);
                    break;

                case "LIVE":
                    String venue = parts.length > 7 ? parts[7] : "";
                    LocalDate liveDate = parts.length > 8 ? LocalDate.parse(parts[8]) : LocalDate.now();
                    composition = new LiveVersion(title, artist, style, duration, year, venue, liveDate);
                    break;

                default:
                    logger.warn("Невідомий тип композиції: " + type + ", створено як Song");
                    composition = new Song(title, artist, style, duration, year);
                    break;
            }

            composition.setId(id);
            return composition;

        } catch (NumberFormatException e) {
            logger.error("Помилка парсингу числових даних: " + e.getMessage());
            throw new IllegalArgumentException("Невірний формат числових даних: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Помилка парсингу композиції: " + e.getMessage());
            throw e;
        }
    }

    public void createDefaultFile() {
        File file = new File(DEFAULT_FILE);
        if (file.exists()) {
            logger.debug("Файл за замовчуванням вже існує: " + DEFAULT_FILE);
            return;
        }

        logger.info("Створення файлу за замовчуванням з прикладами композицій...");

        try {
            file.getParentFile().mkdirs();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("1|SONG|Bohemian Rhapsody|Queen|ROCK|354|1975||false\n");
                writer.write("2|SONG|Billie Jean|Michael Jackson|POP|294|1983||false\n");
                writer.write("3|INSTRUMENTAL|Moonlight Sonata|Ludwig van Beethoven|CLASSICAL|900|1801|Piano\n");
                writer.write("4|REMIX|Blue Monday|New Order|ELECTRONIC|447|1983|New Order|88 Remix\n");
                writer.write("5|LIVE|Stairway to Heaven|Led Zeppelin|ROCK|482|1971|Madison Square Garden|1973-07-27\n");
            }
            logger.debug("Створено файл з прикладами: " + DEFAULT_FILE);
            System.out.println("Створено файл з прикладами: " + DEFAULT_FILE);

        } catch (IOException e) {
            logger.error("Помилка створення файлу за замовчуванням: " + DEFAULT_FILE, e);
            System.err.println("Помилка створення файлу: " + e.getMessage());
        }
    }

    public boolean defaultFileExists() {
        return new File(DEFAULT_FILE).exists();
    }
}