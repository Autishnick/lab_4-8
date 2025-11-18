package com.musicsystem.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static ConfigManager instance;
    private Properties properties;
    private static final String CONFIG_FILE = "data/config.properties";

    private ConfigManager() {
        properties = new Properties();
        loadConfig();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadConfig() {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("Не вдалося знайти конфігураційний файл: " + CONFIG_FILE);
                loadDefaultConfig();
            }
        } catch (IOException e) {
            System.err.println("Не вдалося завантажити конфігурацію: " + e.getMessage());
            loadDefaultConfig();
        }
    }

    private void loadDefaultConfig() {
        properties.setProperty("data.file.path", "data/compositions.txt");
        properties.setProperty("disk.cd.capacity", "700");
        properties.setProperty("disk.dvd.capacity", "4700");
        properties.setProperty("disk.bluray.capacity", "25000");
        properties.setProperty("audio.size.per.minute", "5");
        properties.setProperty("validation.duration.max", "3600");
        properties.setProperty("validation.year.min", "1900");
        properties.setProperty("validation.year.max", "2100");
        properties.setProperty("autosave.enabled", "true");
        properties.setProperty("autosave.on.exit", "true");
        properties.setProperty("language", "uk");
        properties.setProperty("app.version", "1.0");
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }

    public String getDataFilePath() {
        return getProperty("data.file.path", "data/compositions.txt");
    }

    public int getCdCapacity() {
        return getIntProperty("disk.cd.capacity", 700);
    }

    public int getDvdCapacity() {
        return getIntProperty("disk.dvd.capacity", 4700);
    }

    public int getBlurayCapacity() {
        return getIntProperty("disk.bluray.capacity", 25000);
    }

    public int getAudioSizePerMinute() {
        return getIntProperty("audio.size.per.minute", 5);
    }

    public int getMaxDuration() {
        return getIntProperty("validation.duration.max", 3600);
    }

    public int getMinYear() {
        return getIntProperty("validation.year.min", 1900);
    }

    public int getMaxYear() {
        return getIntProperty("validation.year.max", 2100);
    }

    public boolean isAutosaveEnabled() {
        return getBooleanProperty("autosave.enabled", true);
    }

    public boolean isAutosaveOnExit() {
        return getBooleanProperty("autosave.on.exit", true);
    }

    public String getAppVersion() {
        return getProperty("app.version", "1.0");
    }

    public String getAppName() {
        return getProperty("app.name", "Music System Management");
    }
}