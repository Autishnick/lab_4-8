package com.musicsystem.util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Logger {
    private static Logger instance;
    private LogLevel currentLevel;
    private String logFilePath;
    private boolean emailOnFatal;
    private EmailNotifier emailNotifier;
    private DateTimeFormatter formatter;

    private Logger() {
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        loadConfiguration();
        initializeLogFile();
    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    private void loadConfiguration() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("resources/logging.properties")) {
            props.load(input);

            String level = props.getProperty("log.level", "INFO");
            this.currentLevel = LogLevel.fromString(level);

            this.logFilePath = props.getProperty("log.file.path", "logs/application.log");
            this.emailOnFatal = Boolean.parseBoolean(props.getProperty("log.email.on.fatal", "true"));

            if (emailOnFatal) {
                String emailTo = props.getProperty("log.email.to");
                String emailFrom = props.getProperty("log.email.from");
                String smtpHost = props.getProperty("log.email.smtp.host");
                String smtpPort = props.getProperty("log.email.smtp.port");
                String smtpUser = props.getProperty("log.email.smtp.user");
                String smtpPassword = props.getProperty("log.email.smtp.password");

                this.emailNotifier = new EmailNotifier(emailTo, emailFrom, smtpHost,
                        smtpPort, smtpUser, smtpPassword);
            }
        } catch (IOException e) {
            // Використовуємо дефолтні значення
            this.currentLevel = LogLevel.INFO;
            this.logFilePath = "logs/application.log";
            this.emailOnFatal = false;
            System.err.println("Не вдалося завантажити logging.properties, використано дефолтні значення");
        }
    }

    private void initializeLogFile() {
        File logFile = new File(logFilePath);
        File logDir = logFile.getParentFile();

        if (logDir != null && !logDir.exists()) {
            logDir.mkdirs();
        }

        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                System.err.println("Не вдалося створити лог-файл: " + e.getMessage());
            }
        }
    }

    public void log(LogLevel level, String className, String message) {
        if (level.getSeverity() >= currentLevel.getSeverity()) {
            String logEntry = formatLogEntry(level, className, message);

            // Виводимо в консоль
            if (level.getSeverity() >= LogLevel.WARN.getSeverity()) {
                System.err.println(logEntry);
            } else {
                System.out.println(logEntry);
            }

            // Записуємо у файл
            writeToFile(logEntry);

            // Відправляємо email для FATAL помилок
            if (level == LogLevel.FATAL && emailOnFatal && emailNotifier != null) {
                emailNotifier.sendCriticalError(className, message, logEntry);
            }
        }
    }

    private String formatLogEntry(LogLevel level, String className, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        return String.format("[%s] [%s] [%s] %s", timestamp, level, className, message);
    }

    private void writeToFile(String logEntry) {
        try (FileWriter fw = new FileWriter(logFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(logEntry);
        } catch (IOException e) {
            System.err.println("Помилка запису в лог-файл: " + e.getMessage());
        }
    }

    // Зручні методи для різних рівнів
    public void debug(String className, String message) {
        log(LogLevel.DEBUG, className, message);
    }

    public void info(String className, String message) {
        log(LogLevel.INFO, className, message);
    }

    public void warn(String className, String message) {
        log(LogLevel.WARN, className, message);
    }

    public void error(String className, String message) {
        log(LogLevel.ERROR, className, message);
    }

    public void error(String className, String message, Exception e) {
        String fullMessage = message + " | Exception: " + e.getClass().getSimpleName() +
                " - " + e.getMessage();
        log(LogLevel.ERROR, className, fullMessage);
    }

    public void fatal(String className, String message) {
        log(LogLevel.FATAL, className, message);
    }

    public void fatal(String className, String message, Throwable t) {
        String fullMessage = message + " | " + t.getClass().getSimpleName() +
                " - " + t.getMessage();
        log(LogLevel.FATAL, className, fullMessage);
    }

    // Геттери для тестування
    public LogLevel getCurrentLevel() {
        return currentLevel;
    }

    public String getLogFilePath() {
        return logFilePath;
    }
}