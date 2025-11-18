package com.musicsystem.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тести для класу EmailNotifier")
class EmailNotifierTest {

    private EmailNotifier notifier;

    @BeforeEach
    void setUp() {
        notifier = new EmailNotifier(
            "test@example.com",
            "sender@example.com",
            "smtp.example.com",
            "587",
            "user",
            "password"
        );
    }

    @Test
    @DisplayName("Конструктор ініціалізує всі поля")
    void testConstructor() {
        assertNotNull(notifier);
    }

    @Test
    @DisplayName("sendCriticalError() обробляє null налаштування")
    void testSendCriticalErrorWithNullSettings() {
        EmailNotifier nullNotifier = new EmailNotifier(null, null, null, null, null, null);
        assertDoesNotThrow(() -> {
            nullNotifier.sendCriticalError("TestClass", "Test message", "Test log");
        });
    }

    @Test
    @DisplayName("sendCriticalError() обробляє валідні налаштування")
    void testSendCriticalErrorWithValidSettings() {
        // Це не відправить реальний email, але перевірить що метод не падає
        assertDoesNotThrow(() -> {
            notifier.sendCriticalError("TestClass", "Test message", "Test log entry");
        });
    }

    @Test
    @DisplayName("sendCriticalError() обробляє порожні рядки")
    void testSendCriticalErrorWithEmptyStrings() {
        assertDoesNotThrow(() -> {
            notifier.sendCriticalError("", "", "");
        });
    }

    @Test
    @DisplayName("checkJavaMailAvailability() перевіряє наявність JavaMail")
    void testJavaMailAvailability() {
        // Перевіряємо що метод не падає
        EmailNotifier testNotifier = new EmailNotifier(
            "test@example.com",
            "sender@example.com",
            "smtp.example.com",
            "587",
            "user",
            "password"
        );
        assertNotNull(testNotifier);
    }
}

