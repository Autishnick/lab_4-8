package com.musicsystem.util;

public enum LogLevel {
    DEBUG("DEBUG", 0),
    INFO("INFO", 1),
    WARN("WARN", 2),
    ERROR("ERROR", 3),
    FATAL("FATAL", 4);

    private final String displayName;
    private final int severity;

    LogLevel(String displayName, int severity) {
        this.displayName = displayName;
        this.severity = severity;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getSeverity() {
        return severity;
    }

    public static LogLevel fromString(String text) {
        for (LogLevel level : LogLevel.values()) {
            if (level.displayName.equalsIgnoreCase(text)) {
                return level;
            }
        }
        return INFO;
    }

    @Override
    public String toString() {
        return displayName;
    }
}