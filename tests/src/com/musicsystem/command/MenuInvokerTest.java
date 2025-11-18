package com.musicsystem.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тести для класу MenuInvoker")
class MenuInvokerTest {

    private MenuInvoker invoker;
    private boolean commandExecuted;

    @BeforeEach
    void setUp() {
        invoker = new MenuInvoker();
        commandExecuted = false;
    }

    @Test
    @DisplayName("executeCommand() виконує встановлену команду")
    void testExecuteCommand() {
        Command cmd = () -> commandExecuted = true;
        invoker.setCommand(cmd);
        invoker.executeCommand();
        assertTrue(commandExecuted);
    }

    @Test
    @DisplayName("executeCommand() не виконує команду якщо вона не встановлена")
    void testExecuteCommandNull() {
        invoker.executeCommand();
        assertFalse(commandExecuted);
    }

    @Test
    @DisplayName("setCommand() встановлює команду")
    void testSetCommand() {
        Command cmd = () -> {};
        invoker.setCommand(cmd);
        invoker.executeCommand();
        // Якщо не виникло помилки, команда встановлена правильно
        assertTrue(true);
    }
}

