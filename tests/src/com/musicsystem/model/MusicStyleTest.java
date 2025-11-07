package com.musicsystem.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тести для enum MusicStyle")
class MusicStyleTest {

    @Test
    @DisplayName("Всі стилі мають правильні displayName")
    void testDisplayNames() {
        assertAll(
                () -> assertEquals("Rock", MusicStyle.ROCK.getDisplayName()),
                () -> assertEquals("Pop", MusicStyle.POP.getDisplayName()),
                () -> assertEquals("Jazz", MusicStyle.JAZZ.getDisplayName()),
                () -> assertEquals("Classical", MusicStyle.CLASSICAL.getDisplayName()),
                () -> assertEquals("Electronic", MusicStyle.ELECTRONIC.getDisplayName()),
                () -> assertEquals("Hip-Hop", MusicStyle.HIP_HOP.getDisplayName()),
                () -> assertEquals("Metal", MusicStyle.METAL.getDisplayName()),
                () -> assertEquals("Blues", MusicStyle.BLUES.getDisplayName()),
                () -> assertEquals("Country", MusicStyle.COUNTRY.getDisplayName()),
                () -> assertEquals("Reggae", MusicStyle.REGGAE.getDisplayName()),
                () -> assertEquals("Other", MusicStyle.OTHER.getDisplayName())
        );
    }

    @Test
    @DisplayName("fromString() знаходить стиль за displayName")
    void testFromStringByDisplayName() {
        // ACT
        MusicStyle style = MusicStyle.fromString("Rock");

        // ASSERT
        assertEquals(MusicStyle.ROCK, style);
    }

    @Test
    @DisplayName("fromString() знаходить стиль за enum name")
    void testFromStringByEnumName() {
        // ACT
        MusicStyle style = MusicStyle.fromString("ROCK");

        // ASSERT
        assertEquals(MusicStyle.ROCK, style);
    }

    @Test
    @DisplayName("fromString() не чутливий до регістру")
    void testFromStringCaseInsensitive() {
        assertAll(
                () -> assertEquals(MusicStyle.ROCK, MusicStyle.fromString("rock")),
                () -> assertEquals(MusicStyle.ROCK, MusicStyle.fromString("ROCK")),
                () -> assertEquals(MusicStyle.ROCK, MusicStyle.fromString("Rock"))
        );
    }

    @Test
    @DisplayName("fromString() повертає OTHER для невідомого стилю")
    void testFromStringUnknown() {
        // ACT
        MusicStyle style = MusicStyle.fromString("Unknown Style");

        // ASSERT
        assertEquals(MusicStyle.OTHER, style);
    }

    @Test
    @DisplayName("toString() повертає displayName")
    void testToString() {
        // ACT
        String str = MusicStyle.ROCK.toString();

        // ASSERT
        assertEquals("Rock", str);
    }

    @Test
    @DisplayName("Enum містить 11 стилів")
    void testEnumSize() {
        // ACT
        MusicStyle[] styles = MusicStyle.values();

        // ASSERT
        assertEquals(11, styles.length);
    }
}