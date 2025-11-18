package com.musicsystem.command.impl;

import com.musicsystem.model.Song;
import com.musicsystem.model.MusicStyle;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.FileManager;
import com.musicsystem.util.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

@DisplayName("Тести для SaveToFileCommand")
class SaveToFileCommandTest {

    private MusicCollection collection;
    private FileManager fileManager;
    private InputValidator validator;
    private SaveToFileCommand command;

    @BeforeEach
    void setUp() {
        collection = new MusicCollection();
        fileManager = new FileManager();
    }

    @Test
    @DisplayName("execute() обробляє порожню колекцію")
    void testExecuteEmptyCollection() {
        String input = "\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new SaveToFileCommand(collection, fileManager, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() скасовує збереження")
    void testExecuteCancels() {
        Song song = new Song("Test Song", "Artist", MusicStyle.ROCK, 180, 2020);
        collection.add(song);
        
        String input = "0\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new SaveToFileCommand(collection, fileManager, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }
}

