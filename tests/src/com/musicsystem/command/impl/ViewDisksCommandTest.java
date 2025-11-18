package com.musicsystem.command.impl;

import com.musicsystem.model.Compilation;
import com.musicsystem.model.DiskType;
import com.musicsystem.model.Song;
import com.musicsystem.model.MusicStyle;
import com.musicsystem.service.DiskManager;
import com.musicsystem.util.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

@DisplayName("Тести для ViewDisksCommand")
class ViewDisksCommandTest {

    private DiskManager diskManager;
    private InputValidator validator;
    private ViewDisksCommand command;

    @BeforeEach
    void setUp() {
        diskManager = new DiskManager();
    }

    @Test
    @DisplayName("execute() обробляє порожній менеджер")
    void testExecuteEmptyManager() {
        String input = "\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new ViewDisksCommand(diskManager, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() відображає диски")
    void testExecuteShowsDisks() {
        Compilation compilation = new Compilation("Test", "Test");
        Song song = new Song("Test Song", "Artist", MusicStyle.ROCK, 180, 2020);
        compilation.addTrack(song);
        diskManager.burnCompilation(compilation, DiskType.CD);
        
        String input = "ні\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new ViewDisksCommand(diskManager, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }
}

