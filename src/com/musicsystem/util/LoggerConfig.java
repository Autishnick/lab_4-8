package com.musicsystem.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoggerConfig {
    
    public static void loadSystemProperties() {
        Properties props = new Properties();
        try (InputStream input = LoggerConfig.class.getClassLoader().getResourceAsStream("logging.properties")) {
            if (input != null) {
            props.load(input);
            } else {
                System.setProperty("log.level", "INFO");
                System.setProperty("log.file.path", "logs/application.log");
                return;
            }

            String level = props.getProperty("log.level", "INFO");
            System.setProperty("log.level", level);

            String logFilePath = props.getProperty("log.file.path", "logs/application.log");
            System.setProperty("log.file.path", logFilePath);

            boolean emailOnFatal = Boolean.parseBoolean(props.getProperty("log.email.on.fatal", "true"));

            if (emailOnFatal) {
                String emailTo = props.getProperty("log.email.to");
                String emailFrom = props.getProperty("log.email.from");
                String smtpHost = props.getProperty("log.email.smtp.host");
                String smtpPort = props.getProperty("log.email.smtp.port");
                String smtpUser = props.getProperty("log.email.smtp.user");
                String smtpPassword = props.getProperty("log.email.smtp.password");

                if (emailTo != null) System.setProperty("log.email.to", emailTo);
                if (emailFrom != null) System.setProperty("log.email.from", emailFrom);
                if (smtpHost != null) System.setProperty("log.email.smtp.host", smtpHost);
                if (smtpPort != null) System.setProperty("log.email.smtp.port", smtpPort);
                if (smtpUser != null) System.setProperty("log.email.smtp.user", smtpUser);
                if (smtpPassword != null) System.setProperty("log.email.smtp.password", smtpPassword);
            }
        } catch (IOException e) {
            System.setProperty("log.level", "INFO");
            System.setProperty("log.file.path", "logs/application.log");
            System.err.println("Не вдалося завантажити logging.properties, використано дефолтні значення");
        }
    }
}

