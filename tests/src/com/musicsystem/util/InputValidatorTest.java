package com.musicsystem.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

@DisplayName("Тести для класу InputValidator")
class InputValidatorTest {

    private InputValidator validator;

    private InputValidator createValidatorWithInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        return new InputValidator(scanner);
    }

    @Test
    @DisplayName("readInt() парсить ціле число")
    void testReadInt() {
        // ARRANGE
        validator = createValidatorWithInput("42\n");

        // ACT
        int result = validator.readInt("Enter number: ");

        // ASSERT
        assertEquals(42, result);
    }

    @Test
    @DisplayName("readInt() повторює запит при невалідному вводі")
    void testReadIntRetryOnInvalid() {
        // ARRANGE
        validator = createValidatorWithInput("abc\n123\n");

        // ACT
        int result = validator.readInt("Enter number: ");

        // ASSERT
        assertEquals(123, result);
    }

    @Test
    @DisplayName("readInt(min, max) перевіряє діапазон")
    void testReadIntWithRange() {
        // ARRANGE
        validator = createValidatorWithInput("5\n");

        // ACT
        int result = validator.readInt("Enter: ", 1, 10);

        // ASSERT
        assertEquals(5, result);
    }

    @Test
    @DisplayName("readInt(min, max) повторює при виході за межі")
    void testReadIntOutOfRange() {
        // ARRANGE
        validator = createValidatorWithInput("15\n5\n");

        // ACT
        int result = validator.readInt("Enter: ", 1, 10);

        // ASSERT
        assertEquals(5, result);
    }

    @Test
    @DisplayName("readPositiveInt() приймає тільки додатні числа")
    void testReadPositiveInt() {
        // ARRANGE
        validator = createValidatorWithInput("10\n");

        // ACT
        int result = validator.readPositiveInt("Enter: ");

        // ASSERT
        assertEquals(10, result);
    }

    @Test
    @DisplayName("readPositiveInt() відхиляє від'ємні та 0")
    void testReadPositiveIntRejectsNegative() {
        // ARRANGE
        validator = createValidatorWithInput("-5\n0\n10\n");

        // ACT
        int result = validator.readPositiveInt("Enter: ");

        // ASSERT
        assertEquals(10, result);
    }

    @Test
    @DisplayName("readNonEmptyString() не приймає порожній рядок")
    void testReadNonEmptyString() {
        // ARRANGE
        validator = createValidatorWithInput("\n   \nvalid\n");

        // ACT
        String result = validator.readNonEmptyString("Enter: ");

        // ASSERT
        assertEquals("valid", result);
    }

    @Test
    @DisplayName("readString() повертає рядок з trim")
    void testReadString() {
        // ARRANGE
        validator = createValidatorWithInput("  test  \n");

        // ACT
        String result = validator.readString("Enter: ");

        // ASSERT
        assertEquals("test", result);
    }

    @Test
    @DisplayName("readBoolean() розпізнає 'так'")
    void testReadBooleanYes() {
        // ARRANGE
        validator = createValidatorWithInput("так\n");

        // ACT
        boolean result = validator.readBoolean("Confirm: ");

        // ASSERT
        assertTrue(result);
    }

    @Test
    @DisplayName("readBoolean() розпізнає 'ні'")
    void testReadBooleanNo() {
        // ARRANGE
        validator = createValidatorWithInput("ні\n");

        // ACT
        boolean result = validator.readBoolean("Confirm: ");

        // ASSERT
        assertFalse(result);
    }

    @Test
    @DisplayName("readBoolean() розпізнає різні варіанти")
    void testReadBooleanVariants() {
        assertAll(
                () -> {
                    var v = createValidatorWithInput("yes\n");
                    assertTrue(v.readBoolean(""));
                },
                () -> {
                    var v = createValidatorWithInput("no\n");
                    assertFalse(v.readBoolean(""));
                },
                () -> {
                    var v = createValidatorWithInput("т\n");
                    assertTrue(v.readBoolean(""));
                },
                () -> {
                    var v = createValidatorWithInput("н\n");
                    assertFalse(v.readBoolean(""));
                }
        );
    }

    @Test
    @DisplayName("readDuration() парсить MM:SS формат")
    void testReadDurationMMSS() {
        // ARRANGE
        validator = createValidatorWithInput("03:30\n");

        // ACT
        int result = validator.readDuration("Enter: ");

        // ASSERT
        assertEquals(210, result); // 3*60 + 30
    }

    @Test
    @DisplayName("readDuration() парсить HH:MM:SS формат")
    void testReadDurationHHMMSS() {
        // ARRANGE
        validator = createValidatorWithInput("01:30:45\n");

        // ACT
        int result = validator.readDuration("Enter: ");

        // ASSERT
        assertEquals(5445, result); // 1*3600 + 30*60 + 45
    }

    @Test
    @DisplayName("readDuration() повторює при невалідному форматі")
    void testReadDurationInvalid() {
        // ARRANGE
        validator = createValidatorWithInput("invalid\n02:30\n");

        // ACT
        int result = validator.readDuration("Enter: ");

        // ASSERT
        assertEquals(150, result);
    }

    @Test
    @DisplayName("readDuration() відхиляє секунди >= 60")
    void testReadDurationInvalidSeconds() {
        // ARRANGE
        validator = createValidatorWithInput("03:70\n03:30\n");

        // ACT
        int result = validator.readDuration("Enter: ");

        // ASSERT
        assertEquals(210, result);
    }

    @Test
    @DisplayName("waitForEnter() очікує натискання Enter")
    void testWaitForEnter() {
        // ARRANGE
        validator = createValidatorWithInput("\n");

        // ACT & ASSERT
        assertDoesNotThrow(() -> validator.waitForEnter());
    }
}