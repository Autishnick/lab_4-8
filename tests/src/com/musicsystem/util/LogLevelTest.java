package com.musicsystem.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тести для класу LogLevel")
class LogLevelTest {

    @Test
    @DisplayName("getDisplayName() повертає правильну назву")
    void testGetDisplayName() {
        assertEquals("DEBUG", LogLevel.DEBUG.getDisplayName());
        assertEquals("INFO", LogLevel.INFO.getDisplayName());
        assertEquals("WARN", LogLevel.WARN.getDisplayName());
        assertEquals("ERROR", LogLevel.ERROR.getDisplayName());
        assertEquals("FATAL", LogLevel.FATAL.getDisplayName());
    }

    @Test
    @DisplayName("getSeverity() повертає правильну важливість")
    void testGetSeverity() {
        assertEquals(0, LogLevel.DEBUG.getSeverity());
        assertEquals(1, LogLevel.INFO.getSeverity());
        assertEquals(2, LogLevel.WARN.getSeverity());
        assertEquals(3, LogLevel.ERROR.getSeverity());
        assertEquals(4, LogLevel.FATAL.getSeverity());
    }

    @Test
    @DisplayName("fromString() розпізнає валідні значення")
    void testFromStringValid() {
        assertEquals(LogLevel.DEBUG, LogLevel.fromString("DEBUG"));
        assertEquals(LogLevel.INFO, LogLevel.fromString("INFO"));
        assertEquals(LogLevel.WARN, LogLevel.fromString("WARN"));
        assertEquals(LogLevel.ERROR, LogLevel.fromString("ERROR"));
        assertEquals(LogLevel.FATAL, LogLevel.fromString("FATAL"));
    }

    @Test
    @DisplayName("fromString() нечутливий до регістру")
    void testFromStringCaseInsensitive() {
        assertEquals(LogLevel.DEBUG, LogLevel.fromString("debug"));
        assertEquals(LogLevel.INFO, LogLevel.fromString("Info"));
        assertEquals(LogLevel.WARN, LogLevel.fromString("WARN"));
    }

    @Test
    @DisplayName("fromString() повертає INFO для невалідного значення")
    void testFromStringInvalid() {
        assertEquals(LogLevel.INFO, LogLevel.fromString("INVALID"));
        assertEquals(LogLevel.INFO, LogLevel.fromString(""));
    }

    @Test
    @DisplayName("toString() повертає displayName")
    void testToString() {
        assertEquals("DEBUG", LogLevel.DEBUG.toString());
        assertEquals("INFO", LogLevel.INFO.toString());
        assertEquals("WARN", LogLevel.WARN.toString());
        assertEquals("ERROR", LogLevel.ERROR.toString());
        assertEquals("FATAL", LogLevel.FATAL.toString());
    }

    @Test
    @DisplayName("values() повертає всі значення")
    void testValues() {
        LogLevel[] levels = LogLevel.values();
        assertEquals(5, levels.length);
        assertTrue(java.util.Arrays.asList(levels).contains(LogLevel.DEBUG));
        assertTrue(java.util.Arrays.asList(levels).contains(LogLevel.INFO));
        assertTrue(java.util.Arrays.asList(levels).contains(LogLevel.WARN));
        assertTrue(java.util.Arrays.asList(levels).contains(LogLevel.ERROR));
        assertTrue(java.util.Arrays.asList(levels).contains(LogLevel.FATAL));
    }
}

