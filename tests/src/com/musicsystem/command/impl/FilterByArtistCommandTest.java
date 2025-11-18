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

@DisplayName("Тести для FilterByArtistCommand")
class FilterByArtistCommandTest {

    private MusicCollection collection;
    private InputValidator validator;
    private FilterByArtistCommand command;

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
        command = new FilterByArtistCommand(collection, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() фільтрує композиції за виконавцем")
    void testExecuteFiltersByArtist() {
        Song song1 = new Song("Song 1", "Artist A", MusicStyle.ROCK, 180, 2020);
        Song song2 = new Song("Song 2", "Artist B", MusicStyle.POP, 200, 2021);
        collection.add(song1);
        collection.add(song2);
        
        String input = "Artist A\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new FilterByArtistCommand(collection, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }
}

