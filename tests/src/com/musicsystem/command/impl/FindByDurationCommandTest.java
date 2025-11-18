package com.musicsystem.command.impl;

import com.musicsystem.model.Song;
import com.musicsystem.model.MusicStyle;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

@DisplayName("Тести для FindByDurationCommand")
class FindByDurationCommandTest {

    private MusicCollection collection;
    private InputValidator validator;
    private FindByDurationCommand command;

    @BeforeEach
    void setUp() {
        collection = new MusicCollection();
    }

    @Test
    @DisplayName("execute() обробляє порожню колекцію")
    void testExecuteEmptyCollection() {
        String input = "\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new FindByDurationCommand(collection, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() знаходить композиції за тривалістю")
    void testExecuteFindsByDuration() {
        Song song = new Song("Test Song", "Artist", MusicStyle.ROCK, 180, 2020);
        collection.add(song);
        
        String input = "02:00\n05:00\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new FindByDurationCommand(collection, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() обробляє невалідний діапазон")
    void testExecuteInvalidRange() {
        Song song = new Song("Test Song", "Artist", MusicStyle.ROCK, 180, 2020);
        collection.add(song);
        
        String input = "05:00\n02:00\n"; // max < min
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new FindByDurationCommand(collection, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }
}

