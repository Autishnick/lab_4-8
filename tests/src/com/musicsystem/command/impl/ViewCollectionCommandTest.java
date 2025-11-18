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

@DisplayName("Тести для ViewCollectionCommand")
class ViewCollectionCommandTest {

    private MusicCollection collection;
    private InputValidator validator;
    private ViewCollectionCommand command;

    @BeforeEach
    void setUp() {
        collection = new MusicCollection();
        String input = "\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new ViewCollectionCommand(collection, validator);
    }

    @Test
    @DisplayName("execute() обробляє порожню колекцію")
    void testExecuteEmptyCollection() {
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() відображає колекцію з композиціями")
    void testExecuteWithCompositions() {
        Song song1 = new Song("Song 1", "Artist 1", MusicStyle.ROCK, 180, 2020);
        Song song2 = new Song("Song 2", "Artist 2", MusicStyle.POP, 200, 2021);
        
        collection.add(song1);
        collection.add(song2);

        assertDoesNotThrow(() -> command.execute());
    }
}

