package com.musicsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@DisplayName("Тести для класу LiveVersion")
class LiveVersionTest {

    private LiveVersion liveVersion;

    @BeforeEach
    void setUp() {
        liveVersion = new LiveVersion("Stairway to Heaven", "Led Zeppelin",
                MusicStyle.ROCK, 482, 1971);
    }

    @Test
    @DisplayName("Конструктор встановлює значення за замовчуванням")
    void testConstructorDefaults() {
        // ASSERT
        assertAll(
                () -> assertEquals("Stairway to Heaven", liveVersion.getTitle()),
                () -> assertEquals("Led Zeppelin", liveVersion.getArtist()),
                () -> assertEquals("", liveVersion.getVenue()),
                () -> assertNotNull(liveVersion.getLiveDate()),
                () -> assertEquals("LIVE", liveVersion.getType())
        );
    }

    @Test
    @DisplayName("Конструктор з параметрами встановлює всі поля")
    void testConstructorWithParameters() {
        // ARRANGE
        LocalDate testDate = LocalDate.of(1973, 7, 27);

        // ACT
        LiveVersion live = new LiveVersion("Song", "Artist", MusicStyle.ROCK,
                300, 1973, "Madison Square Garden", testDate);

        // ASSERT
        assertAll(
                () -> assertEquals("Madison Square Garden", live.getVenue()),
                () -> assertEquals(testDate, live.getLiveDate())
        );
    }

    @Test
    @DisplayName("setVenue() змінює місце виступу")
    void testSetVenue() {
        // ACT
        liveVersion.setVenue("Wembley Stadium");

        // ASSERT
        assertEquals("Wembley Stadium", liveVersion.getVenue());
    }

    @Test
    @DisplayName("setLiveDate() змінює дату виступу")
    void testSetLiveDate() {
        // ARRANGE
        LocalDate newDate = LocalDate.of(1975, 1, 15);

        // ACT
        liveVersion.setLiveDate(newDate);

        // ASSERT
        assertEquals(newDate, liveVersion.getLiveDate());
    }

    @Test
    @DisplayName("getInfo() містить інформацію про live версію")
    void testGetInfo() {
        // ARRANGE
        LocalDate date = LocalDate.of(1973, 7, 27);
        liveVersion.setVenue("MSG");
        liveVersion.setLiveDate(date);

        // ACT
        String info = liveVersion.getInfo();

        // ASSERT
        assertAll(
                () -> assertNotNull(info),
                () -> assertTrue(info.contains("LIVE")),
                () -> assertTrue(info.contains("MSG")),
                () -> assertTrue(info.contains("27.07.1973")),
                () -> assertTrue(info.contains("Stairway to Heaven"))
        );
    }

    @Test
    @DisplayName("toFileString() генерує правильний формат")
    void testToFileString() {
        // ARRANGE
        LocalDate date = LocalDate.of(1973, 7, 27);
        liveVersion.setVenue("Venue");
        liveVersion.setLiveDate(date);

        // ACT
        String fileString = liveVersion.toFileString();

        // ASSERT
        assertAll(
                () -> assertTrue(fileString.contains("LIVE")),
                () -> assertTrue(fileString.contains("Venue")),
                () -> assertTrue(fileString.contains("1973-07-27"))
        );
    }

    @Test
    @DisplayName("validate() працює коректно")
    void testValidate() {
        // ACT
        boolean isValid = liveVersion.validate();

        // ASSERT
        assertTrue(isValid);
    }
}