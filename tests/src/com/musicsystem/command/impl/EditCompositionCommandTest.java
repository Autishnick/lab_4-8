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

@DisplayName("Тести для EditCompositionCommand")
class EditCompositionCommandTest {

    private MusicCollection collection;
    private InputValidator validator;
    private EditCompositionCommand command;

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
        command = new EditCompositionCommand(collection, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() скасовує редагування")
    void testExecuteCancels() {
        Song song = new Song("Test Song", "Artist", MusicStyle.ROCK, 180, 2020);
        collection.add(song);
        
        String input = "0\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new EditCompositionCommand(collection, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() редагує назву композиції")
    void testExecuteEditsTitle() {
        Song song = new Song("Old Title", "Artist", MusicStyle.ROCK, 180, 2020);
        collection.add(song);
        
        String input = "1\n1\nNew Title\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new EditCompositionCommand(collection, validator);
        
        assertDoesNotThrow(() -> command.execute());
        assertEquals("New Title", song.getTitle());
    }
}

