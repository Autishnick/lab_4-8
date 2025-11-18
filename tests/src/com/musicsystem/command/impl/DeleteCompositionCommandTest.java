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

@DisplayName("Тести для DeleteCompositionCommand")
class DeleteCompositionCommandTest {

    private MusicCollection collection;
    private InputValidator validator;
    private DeleteCompositionCommand command;

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
        command = new DeleteCompositionCommand(collection, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() скасовує видалення")
    void testExecuteCancels() {
        Song song = new Song("Test Song", "Artist", MusicStyle.ROCK, 180, 2020);
        collection.add(song);
        
        String input = "0\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new DeleteCompositionCommand(collection, validator);
        
        assertDoesNotThrow(() -> command.execute());
        assertEquals(1, collection.getTotalCount());
    }

    @Test
    @DisplayName("execute() видаляє композицію при підтвердженні")
    void testExecuteDeletes() {
        Song song = new Song("Test Song", "Artist", MusicStyle.ROCK, 180, 2020);
        collection.add(song);
        
        String input = "1\nтак\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new DeleteCompositionCommand(collection, validator);
        
        assertDoesNotThrow(() -> command.execute());
        assertEquals(0, collection.getTotalCount());
    }
}

