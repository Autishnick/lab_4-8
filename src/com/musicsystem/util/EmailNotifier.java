package com.musicsystem.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailNotifier {
    private String emailTo;
    private String emailFrom;
    private String smtpHost;
    private String smtpPort;
    private String smtpUser;
    private String smtpPassword;
    private boolean javaMailAvailable;

    public EmailNotifier(String emailTo, String emailFrom, String smtpHost,
            String smtpPort, String smtpUser, String smtpPassword) {
        this.emailTo = emailTo;
        this.emailFrom = emailFrom;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.smtpUser = smtpUser;
        this.smtpPassword = smtpPassword;

        this.javaMailAvailable = checkJavaMailAvailability();
    }

    private boolean checkJavaMailAvailability() {
        try {
            Class.forName("javax.mail.Session");
            Class.forName("javax.activation.DataHandler");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public void sendCriticalError(String className, String message, String logEntry) {
        if (emailTo == null || emailFrom == null || smtpHost == null) {
            System.err.println("Email налаштування не задані. Email не відправлено.");
            return;
        }

        if (!javaMailAvailable) {
            System.err.println("⚠️  JavaMail бібліотека не знайдена. Email не відправлено.");
            System.err.println("   Для відправки email додайте javax.mail.jar та activation.jar до classpath.");
            System.err.println("   Критична помилка: [" + className + "] " + message);
            return;
        }

        new Thread(() -> {
            try {
                sendEmailViaJavaMail(className, message, logEntry);
            } catch (Exception e) {
                System.err.println("Помилка відправки email: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    private void sendEmailViaJavaMail(String className, String message, String logEntry) {
        try {
            Session session = createMailSession();
            Message mailMessage = new MimeMessage(session);

            mailMessage.setFrom(new InternetAddress(emailFrom));
            mailMessage.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(emailTo));

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            mailMessage.setSubject("🚨 КРИТИЧНА ПОМИЛКА в Music System - " + timestamp);

            String emailBody = buildEmailBody(className, message, logEntry, timestamp);
            mailMessage.setContent(emailBody, "text/html; charset=utf-8");

            Transport.send(mailMessage);

            System.out.println("✓ Email з критичною помилкою відправлено на: " + emailTo);

        } catch (Exception e) {
            System.err.println("Помилка відправки email через JavaMail: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Session createMailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", smtpHost);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUser, smtpPassword);
            }
        });
    }

    private String buildEmailBody(String className, String message, String logEntry, String timestamp) {
        StringBuilder body = new StringBuilder();
        body.append("<!DOCTYPE html>");
        body.append("<html><head><meta charset='UTF-8'></head><body>");
        body.append("<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>");

        body.append(
                "<div style='background-color: #d32f2f; color: white; padding: 20px; border-radius: 5px 5px 0 0;'>");
        body.append("<h2 style='margin: 0;'>🚨 КРИТИЧНА ПОМИЛКА</h2>");
        body.append("<p style='margin: 5px 0 0 0;'>Music System Management</p>");
        body.append("</div>");

        body.append("<div style='background-color: #f5f5f5; padding: 20px; border-radius: 0 0 5px 5px;'>");

        body.append(
                "<div style='background-color: white; padding: 15px; margin-bottom: 15px; border-left: 4px solid #d32f2f;'>");
        body.append("<p style='margin: 0 0 10px 0;'><strong>Час:</strong> ").append(timestamp).append("</p>");
        body.append("<p style='margin: 0 0 10px 0;'><strong>Клас:</strong> ").append(className).append("</p>");
        body.append("<p style='margin: 0;'><strong>Повідомлення:</strong></p>");
        body.append("<p style='margin: 5px 0; color: #d32f2f; font-weight: bold;'>").append(escapeHtml(message))
                .append("</p>");
        body.append("</div>");

        body.append(
                "<div style='background-color: #263238; color: #aed581; padding: 15px; border-radius: 5px; font-family: monospace; font-size: 12px;'>");
        body.append("<p style='margin: 0 0 5px 0; color: #90a4ae;'><strong>Лог запис:</strong></p>");
        body.append("<pre style='margin: 0; white-space: pre-wrap; word-wrap: break-word;'>")
                .append(escapeHtml(logEntry)).append("</pre>");
        body.append("</div>");

        body.append(
                "<div style='background-color: #fff3cd; border: 1px solid #ffc107; padding: 15px; margin-top: 15px; border-radius: 5px;'>");
        body.append("<p style='margin: 0; color: #856404;'><strong>⚠️ Рекомендовані дії:</strong></p>");
        body.append("<ul style='margin: 10px 0 0 0; color: #856404;'>");
        body.append("<li>Перевірте лог-файл для деталей</li>");
        body.append("<li>Перевірте стан системи</li>");
        body.append("<li>За необхідності перезапустіть додаток</li>");
        body.append("</ul>");
        body.append("</div>");

        body.append("</div>");

        body.append("<div style='text-align: center; padding: 20px; color: #999; font-size: 12px;'>");
        body.append("<p style='margin: 0;'>Це автоматичне повідомлення від Music System Management</p>");
        body.append("<p style='margin: 5px 0 0 0;'>Будь ласка, не відповідайте на цей email</p>");
        body.append("</div>");

        body.append("</div>");
        body.append("</body></html>");

        return body.toString();
    }

    private String escapeHtml(String text) {
        if (text == null)
            return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
}
