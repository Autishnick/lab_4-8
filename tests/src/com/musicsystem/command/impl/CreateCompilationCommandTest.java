package com.musicsystem.command.impl;

import com.musicsystem.model.Song;
import com.musicsystem.model.MusicStyle;
import com.musicsystem.service.CompilationManager;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

@DisplayName("Тести для CreateCompilationCommand")
class CreateCompilationCommandTest {

    private MusicCollection collection;
    private CompilationManager compilationManager;
    private InputValidator validator;
    private CreateCompilationCommand command;

    @BeforeEach
    void setUp() {
        collection = new MusicCollection();
        compilationManager = new CompilationManager();
    }

    @Test
    @DisplayName("execute() обробляє порожню колекцію")
    void testExecuteEmptyCollection() {
        String input = "\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new CreateCompilationCommand(collection, compilationManager, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() створює збірку з композиціями")
    void testExecuteCreatesCompilation() {
        Song song = new Song("Test Song", "Artist", MusicStyle.ROCK, 180, 2020);
        collection.add(song);
        
        // Введення: назва, опис, не додавати треки, не продовжувати
        String input = "Test Compilation\nTest Description\nні\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new CreateCompilationCommand(collection, compilationManager, validator);
        
        assertDoesNotThrow(() -> command.execute());
        assertEquals(1, compilationManager.getTotalCount());
    }
}

