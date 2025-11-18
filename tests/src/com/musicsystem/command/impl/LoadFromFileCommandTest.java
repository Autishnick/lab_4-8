package com.musicsystem.command.impl;

import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.FileManager;
import com.musicsystem.util.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

@DisplayName("Тести для LoadFromFileCommand")
class LoadFromFileCommandTest {

    private MusicCollection collection;
    private FileManager fileManager;
    private InputValidator validator;
    private LoadFromFileCommand command;

    @BeforeEach
    void setUp() {
        collection = new MusicCollection();
        fileManager = new FileManager();
    }

    @Test
    @DisplayName("execute() скасовує завантаження")
    void testExecuteCancels() {
        String input = "0\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new LoadFromFileCommand(collection, fileManager, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() обробляє відсутність файлу за замовчуванням")
    void testExecuteHandlesMissingDefaultFile() {
        String input = "1\nні\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new LoadFromFileCommand(collection, fileManager, validator);
        
        assertDoesNotThrow(() -> command.execute());
    }
}

