package com.musicsystem.command.impl;

import com.musicsystem.model.Compilation;
import com.musicsystem.model.Song;
import com.musicsystem.model.MusicStyle;
import com.musicsystem.service.CompilationManager;
import com.musicsystem.util.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

@DisplayName("Тести для SortByStyleCommand")
class SortByStyleCommandTest {

    private CompilationManager compilationManager;
    private InputValidator validator;
    private SortByStyleCommand command;

    @BeforeEach
    void setUp() {
        compilationManager = new CompilationManager();
    }

    @Test
    @DisplayName("execute() обробляє порожній менеджер")
    void testExecuteEmptyManager() {
        String input = "\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new SortByStyleCommand(compilationManager, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() скасовує сортування")
    void testExecuteCancels() {
        Compilation compilation = compilationManager.create("Test", "Test");
        
        String input = "0\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new SortByStyleCommand(compilationManager, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() сортує збірку за стилем")
    void testExecuteSorts() {
        Compilation compilation = compilationManager.create("Test", "Test");
        Song rock = new Song("Rock Song", "Artist", MusicStyle.ROCK, 180, 2020);
        Song pop = new Song("Pop Song", "Artist", MusicStyle.POP, 200, 2021);
        compilation.addTrack(rock);
        compilation.addTrack(pop);
        
        String input = "1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new SortByStyleCommand(compilationManager, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }
}

