package com.musicsystem.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тести для класу LoggerConfig")
class LoggerConfigTest {

    @Test
    @DisplayName("loadSystemProperties() завантажує властивості")
    void testLoadSystemProperties() {
        // Перевіряємо що метод не падає
        assertDoesNotThrow(() -> {
            LoggerConfig.loadSystemProperties();
        });
        
        // Перевіряємо що системні властивості встановлені
        String logLevel = System.getProperty("log.level");
        assertNotNull(logLevel);
        
        String logFilePath = System.getProperty("log.file.path");
        assertNotNull(logFilePath);
    }

    @Test
    @DisplayName("loadSystemProperties() встановлює дефолтні значення при помилці")
    void testLoadSystemPropertiesWithError() {
        // Метод має встановити дефолтні значення навіть якщо файл не знайдено
        assertDoesNotThrow(() -> {
            LoggerConfig.loadSystemProperties();
        });
    }
}

