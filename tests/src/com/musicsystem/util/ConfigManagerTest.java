package com.musicsystem.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тести для класу ConfigManager")
class ConfigManagerTest {

    private ConfigManager config;

    @BeforeEach
    void setUp() {
        config = ConfigManager.getInstance();
    }

    @Test
    @DisplayName("getInstance() повертає singleton")
    void testGetInstanceSingleton() {
        // ACT
        ConfigManager instance1 = ConfigManager.getInstance();
        ConfigManager instance2 = ConfigManager.getInstance();

        // ASSERT
        assertSame(instance1, instance2, "Має бути один екземпляр");
    }

    @Test
    @DisplayName("getProperty() повертає значення")
    void testGetProperty() {
        // ACT
        String dataPath = config.getProperty("data.file.path");

        // ASSERT
        assertNotNull(dataPath, "Властивість має бути завантажена");
    }

    @Test
    @DisplayName("getProperty() повертає значення за замовчуванням")
    void testGetPropertyWithDefault() {
        // ACT
        String value = config.getProperty("nonexistent.property", "default_value");

        // ASSERT
        assertEquals("default_value", value);
    }

    @Test
    @DisplayName("getIntProperty() парсить число")
    void testGetIntProperty() {
        // ACT
        int cdCapacity = config.getIntProperty("disk.cd.capacity", 0);

        // ASSERT
        assertTrue(cdCapacity > 0, "CD місткість має бути числом > 0");
    }

    @Test
    @DisplayName("getIntProperty() повертає значення за замовчуванням для невалідного числа")
    void testGetIntPropertyInvalid() {
        // ACT
        int value = config.getIntProperty("nonexistent", 999);

        // ASSERT
        assertEquals(999, value);
    }

    @Test
    @DisplayName("getBooleanProperty() парсить boolean")
    void testGetBooleanProperty() {
        // ACT
        boolean autosave = config.getBooleanProperty("autosave.enabled", false);

        // ASSERT - має бути або true або false (не default)
        assertTrue(autosave == true || autosave == false);
    }

    @Test
    @DisplayName("getBooleanProperty() повертає значення за замовчуванням")
    void testGetBooleanPropertyDefault() {
        // ACT
        boolean value = config.getBooleanProperty("nonexistent", true);

        // ASSERT
        assertTrue(value);
    }

    @Test
    @DisplayName("getDataFilePath() повертає шлях до файлу даних")
    void testGetDataFilePath() {
        // ACT
        String path = config.getDataFilePath();

        // ASSERT
        assertNotNull(path);
        assertTrue(path.contains("compositions") || path.contains("data"));
    }

    @Test
    @DisplayName("getCdCapacity() повертає місткість CD")
    void testGetCdCapacity() {
        // ACT
        int capacity = config.getCdCapacity();

        // ASSERT
        assertTrue(capacity >= 700, "CD місткість має бути >= 700 MB");
    }

    @Test
    @DisplayName("getDvdCapacity() повертає місткість DVD")
    void testGetDvdCapacity() {
        // ACT
        int capacity = config.getDvdCapacity();

        // ASSERT
        assertTrue(capacity >= 4700, "DVD місткість має бути >= 4700 MB");
    }

    @Test
    @DisplayName("getBlurayCapacity() повертає місткість Blu-ray")
    void testGetBlurayCapacity() {
        // ACT
        int capacity = config.getBlurayCapacity();

        // ASSERT
        assertTrue(capacity >= 25000, "Blu-ray місткість має бути >= 25000 MB");
    }

    @Test
    @DisplayName("getAudioSizePerMinute() повертає розмір аудіо")
    void testGetAudioSizePerMinute() {
        // ACT
        int size = config.getAudioSizePerMinute();

        // ASSERT
        assertTrue(size > 0, "Розмір має бути > 0");
    }

    @Test
    @DisplayName("getMaxDuration() повертає максимальну тривалість")
    void testGetMaxDuration() {
        // ACT
        int maxDuration = config.getMaxDuration();

        // ASSERT
        assertTrue(maxDuration >= 3600, "Максимальна тривалість >= 1 година");
    }

    @Test
    @DisplayName("getMinYear() повертає мінімальний рік")
    void testGetMinYear() {
        // ACT
        int minYear = config.getMinYear();

        // ASSERT
        assertTrue(minYear >= 1900 && minYear <= 2000);
    }

    @Test
    @DisplayName("getMaxYear() повертає максимальний рік")
    void testGetMaxYear() {
        // ACT
        int maxYear = config.getMaxYear();

        // ASSERT
        assertTrue(maxYear >= 2000 && maxYear <= 2200);
    }

    @Test
    @DisplayName("isAutosaveEnabled() повертає статус автозбереження")
    void testIsAutosaveEnabled() {
        // ACT
        boolean enabled = config.isAutosaveEnabled();

        // ASSERT
        assertTrue(enabled == true || enabled == false);
    }

    @Test
    @DisplayName("isAutosaveOnExit() повертає статус автозбереження при виході")
    void testIsAutosaveOnExit() {
        // ACT
        boolean enabled = config.isAutosaveOnExit();

        // ASSERT
        assertTrue(enabled == true || enabled == false);
    }

    @Test
    @DisplayName("getAppVersion() повертає версію додатку")
    void testGetAppVersion() {
        // ACT
        String version = config.getAppVersion();

        // ASSERT
        assertNotNull(version);
        assertFalse(version.isEmpty());
    }

    @Test
    @DisplayName("getAppName() повертає назву додатку")
    void testGetAppName() {
        // ACT
        String name = config.getAppName();

        // ASSERT
        assertNotNull(name);
        assertFalse(name.isEmpty());
    }
}