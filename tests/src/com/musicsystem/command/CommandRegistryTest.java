package com.musicsystem.command;

import com.musicsystem.service.CompilationManager;
import com.musicsystem.service.DiskManager;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.FileManager;
import com.musicsystem.util.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.util.Map;

@DisplayName("Тести для класу CommandRegistry")
class CommandRegistryTest {

    private CommandRegistry registry;
    private CommandRegistry.CommandContext context;

    @BeforeEach
    void setUp() {
        registry = new CommandRegistry();
        MusicCollection collection = new MusicCollection();
        CompilationManager compilationManager = new CompilationManager();
        DiskManager diskManager = new DiskManager();
        FileManager fileManager = new FileManager();
        InputValidator validator = new InputValidator(new Scanner(new ByteArrayInputStream("".getBytes())));
        
        context = new CommandRegistry.CommandContext(
            collection, compilationManager, diskManager, fileManager, validator
        );
    }

    @Test
    @DisplayName("hasCommand() повертає true для існуючих команд")
    void testHasCommand() {
        assertTrue(registry.hasCommand(1));
        assertTrue(registry.hasCommand(17));
        assertFalse(registry.hasCommand(999));
        assertFalse(registry.hasCommand(0));
    }

    @Test
    @DisplayName("getCommand() повертає команду для валідного вибору")
    void testGetCommand() {
        Command cmd = registry.getCommand(1, context);
        assertNotNull(cmd);
    }

    @Test
    @DisplayName("getCommand() повертає null для невалідного вибору")
    void testGetCommandInvalid() {
        Command cmd = registry.getCommand(999, context);
        assertNull(cmd);
    }

    @Test
    @DisplayName("getAllOptions() повертає всі опції")
    void testGetAllOptions() {
        Map<Integer, CommandRegistry.MenuOption> options = registry.getAllOptions();
        assertNotNull(options);
        assertFalse(options.isEmpty());
        assertTrue(options.size() >= 17);
    }

    @Test
    @DisplayName("getMaxOptionNumber() повертає максимальний номер")
    void testGetMaxOptionNumber() {
        int max = registry.getMaxOptionNumber();
        assertTrue(max >= 17);
    }

    @Test
    @DisplayName("MenuOption містить правильні дані")
    void testMenuOption() {
        Map<Integer, CommandRegistry.MenuOption> options = registry.getAllOptions();
        CommandRegistry.MenuOption option = options.get(1);
        
        assertNotNull(option);
        assertEquals(1, option.getNumber());
        assertNotNull(option.getDescription());
        assertNotNull(option.getCategory());
        assertNotNull(option.getCommandFactory());
    }

    @Test
    @DisplayName("CommandContext містить всі необхідні компоненти")
    void testCommandContext() {
        assertNotNull(context.getCollection());
        assertNotNull(context.getCompilationManager());
        assertNotNull(context.getDiskManager());
        assertNotNull(context.getFileManager());
        assertNotNull(context.getValidator());
    }
}

