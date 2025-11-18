package com.musicsystem.command.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.musicsystem.command.Command;
import com.musicsystem.model.Instrumental;
import com.musicsystem.model.LiveVersion;
import com.musicsystem.model.MusicComposition;
import com.musicsystem.model.MusicStyle;
import com.musicsystem.model.Remix;
import com.musicsystem.model.Song;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddCompositionCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddCompositionCommand.class);

    private MusicCollection collection;
    private InputValidator validator;

    public AddCompositionCommand(MusicCollection collection, InputValidator validator) {
        this.collection = collection;
        this.validator = validator;
    }

    @Override
    public void execute() {
        logger.debug("Виконання команди додавання композиції");
        System.out.println("\n=== ДОДАВАННЯ КОМПОЗИЦІЇ ===\n");

        System.out.println("Оберіть тип композиції:");
        System.out.println("1. Пісня (Song)");
        System.out.println("2. Інструментал (Instrumental)");
        System.out.println("3. Ремікс (Remix)");
        System.out.println("4. Live версія (LiveVersion)");

        int type = validator.readInt("Ваш вибір: ", 1, 4);

        String title = validator.readNonEmptyString("Назва: ");
        String artist = validator.readNonEmptyString("Виконавець: ");

        System.out.println("\nОберіть стиль:");
        MusicStyle[] styles = MusicStyle.values();
        for (int i = 0; i < styles.length; i++) {
            System.out.println((i + 1) + ". " + styles[i]);
        }
        int styleIndex = validator.readInt("Ваш вибір: ", 1, styles.length) - 1;
        MusicStyle style = styles[styleIndex];

        int duration = validator.readDuration("Тривалість (MM:SS або HH:MM:SS): ");
        int year = validator.readInt("Рік випуску: ", 1900, 2100);

        MusicComposition composition = null;

        try {
            switch (type) {
                case 1:
                    String lyrics = validator.readString("Текст пісні (опціонально): ");
                    boolean hasFeaturing = validator.readBoolean("Є featuring?");
                    composition = new Song(title, artist, style, duration, year, lyrics, hasFeaturing);
                    break;

                case 2:
                    List<String> instruments = new ArrayList<>();
                    System.out.println("Введіть інструменти (порожній рядок для завершення):");
                    while (true) {
                        String instrument = validator.readString("Інструмент: ");
                        if (instrument.isEmpty())
                            break;
                        instruments.add(instrument);
                    }
                    composition = new Instrumental(title, artist, style, duration, year, instruments);
                    break;

                case 3:
                    String originalArtist = validator.readNonEmptyString("Оригінальний виконавець: ");
                    String remixer = validator.readNonEmptyString("Ремікс від: ");
                    composition = new Remix(title, artist, style, duration, year, originalArtist, remixer);
                    break;

                case 4:
                    String venue = validator.readNonEmptyString("Місце виступу: ");
                    System.out.println("Дата виступу:");
                    int day = validator.readInt("День: ", 1, 31);
                    int month = validator.readInt("Місяць: ", 1, 12);
                    int liveYear = validator.readInt("Рік: ", 1900, 2100);
                    LocalDate liveDate = LocalDate.of(liveYear, month, day);
                    composition = new LiveVersion(title, artist, style, duration, year, venue, liveDate);
                    break;
            }

            collection.add(composition);
            logger.debug("Композицію успішно додано: " + composition.getTitle());
            System.out.println("\n✓ Композицію успішно додано!");
            System.out.println(composition.getInfo());

        } catch (Exception e) {
            logger.error("Помилка додавання композиції: " + e.getMessage(), e);
            System.out.println("\n✗ Помилка додавання: " + e.getMessage());
        }
    }
}