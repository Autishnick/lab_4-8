package com.musicsystem.command.impl;

import com.musicsystem.model.Compilation;
import com.musicsystem.model.Song;
import com.musicsystem.model.MusicStyle;
import com.musicsystem.service.CompilationManager;
import com.musicsystem.service.DiskManager;
import com.musicsystem.util.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

@DisplayName("Тести для BurnToDiskCommand")
class BurnToDiskCommandTest {

    private CompilationManager compilationManager;
    private DiskManager diskManager;
    private InputValidator validator;
    private BurnToDiskCommand command;

    @BeforeEach
    void setUp() {
        compilationManager = new CompilationManager();
        diskManager = new DiskManager();
    }

    @Test
    @DisplayName("execute() обробляє порожній менеджер збірок")
    void testExecuteEmptyManager() {
        String input = "\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new BurnToDiskCommand(compilationManager, diskManager, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() записує збірку на диск")
    void testExecuteBurnsDisk() {
        Compilation compilation = compilationManager.create("Test Compilation", "Test");
        Song song = new Song("Test Song", "Artist", MusicStyle.ROCK, 180, 2020);
        compilation.addTrack(song);
        
        // Введення: вибір збірки, вибір типу диску, підтвердження
        String input = "1\n1\nтак\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new BurnToDiskCommand(compilationManager, diskManager, validator);
        
        assertDoesNotThrow(() -> command.execute());
        assertEquals(1, diskManager.getTotalCount());
    }
}

