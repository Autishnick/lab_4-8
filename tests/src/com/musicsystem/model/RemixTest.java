package com.musicsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тести для класу Remix")
class RemixTest {

    private Remix remix;

    @BeforeEach
    void setUp() {
        remix = new Remix("Blue Monday", "New Order", MusicStyle.ELECTRONIC,
                447, 1983);
    }

    @Test
    @DisplayName("Конструктор без параметрів ремікса встановлює значення за замовчуванням")
    void testConstructorDefaults() {
        // ASSERT
        assertAll(
                () -> assertEquals("Blue Monday", remix.getTitle()),
                () -> assertEquals("New Order", remix.getArtist()),
                () -> assertEquals("New Order", remix.getOriginalArtist()),
                () -> assertEquals("", remix.getRemixer()),
                () -> assertEquals("REMIX", remix.getType())
        );
    }

    @Test
    @DisplayName("Конструктор з параметрами встановлює всі поля")
    void testConstructorWithParameters() {
        // ACT
        Remix customRemix = new Remix("Song", "Artist", MusicStyle.POP,
                300, 2020, "Original Artist", "DJ Remix");

        // ASSERT
        assertAll(
                () -> assertEquals("Original Artist", customRemix.getOriginalArtist()),
                () -> assertEquals("DJ Remix", customRemix.getRemixer())
        );
    }

    @Test
    @DisplayName("setOriginalArtist() змінює оригінального виконавця")
    void testSetOriginalArtist() {
        // ACT
        remix.setOriginalArtist("Different Artist");

        // ASSERT
        assertEquals("Different Artist", remix.getOriginalArtist());
    }

    @Test
    @DisplayName("setRemixer() змінює ремікшера")
    void testSetRemixer() {
        // ACT
        remix.setRemixer("New Remixer");

        // ASSERT
        assertEquals("New Remixer", remix.getRemixer());
    }

    @Test
    @DisplayName("getInfo() містить інформацію про ремікс")
    void testGetInfo() {
        // ARRANGE
        remix.setOriginalArtist("Original");
        remix.setRemixer("Remixer");

        // ACT
        String info = remix.getInfo();

        // ASSERT
        assertAll(
                () -> assertNotNull(info),
                () -> assertTrue(info.contains("REMIX")),
                () -> assertTrue(info.contains("Original")),
                () -> assertTrue(info.contains("Remixer")),
                () -> assertTrue(info.contains("Blue Monday"))
        );
    }

    @Test
    @DisplayName("toFileString() генерує правильний формат")
    void testToFileString() {
        // ARRANGE
        remix.setOriginalArtist("Orig");
        remix.setRemixer("DJ");

        // ACT
        String fileString = remix.toFileString();

        // ASSERT
        assertAll(
                () -> assertTrue(fileString.contains("REMIX")),
                () -> assertTrue(fileString.contains("Orig")),
                () -> assertTrue(fileString.contains("DJ"))
        );
    }

    @Test
    @DisplayName("validate() працює коректно")
    void testValidate() {
        // ACT
        boolean isValid = remix.validate();

        // ASSERT
        assertTrue(isValid);
    }
}