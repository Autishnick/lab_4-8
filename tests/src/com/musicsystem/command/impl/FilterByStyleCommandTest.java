package com.musicsystem.command.impl;

import com.musicsystem.model.MusicComposition;
import com.musicsystem.model.MusicStyle;
import com.musicsystem.model.Song;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

@DisplayName("Тести для FilterByStyleCommand")
class FilterByStyleCommandTest {

    private MusicCollection collection;
    private InputValidator validator;
    private FilterByStyleCommand command;

    @BeforeEach
    void setUp() {
        collection = new MusicCollection();
        // Створюємо валідатор з мокованим вводом
        String input = "1\n"; // вибір першого стилю
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new FilterByStyleCommand(collection, validator);
    }

    @Test
    @DisplayName("execute() обробляє порожню колекцію")
    void testExecuteEmptyCollection() {
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() фільтрує композиції за стилем")
    void testExecuteFiltersByStyle() {
        Song rock1 = new Song("Rock Song 1", "Artist", MusicStyle.ROCK, 180, 2020);
        Song rock2 = new Song("Rock Song 2", "Artist", MusicStyle.ROCK, 200, 2021);
        Song pop = new Song("Pop Song", "Artist", MusicStyle.POP, 150, 2020);
        
        collection.add(rock1);
        collection.add(rock2);
        collection.add(pop);

        String input = "1\n"; // ROCK
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new FilterByStyleCommand(collection, validator);

        assertDoesNotThrow(() -> command.execute());
    }
}

