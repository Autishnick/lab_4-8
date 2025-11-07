package com.musicsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тести для класу Song")
class SongTest {

    private Song song;

    @BeforeEach
    @DisplayName("Підготовка: створення тестової пісні")
    void setUp() {
        // Виконується перед КОЖНИМ тестом
        song = new Song("Bohemian Rhapsody", "Queen",
                MusicStyle.ROCK, 354, 1975);
    }

    @Test
    @DisplayName("Конструктор створює пісню з правильними параметрами")
    void testSongConstructor() {
        // ARRANGE - вже в setUp()

        // ACT - нічого, об'єкт створено

        // ASSERT - перевіряємо всі поля
        assertAll("Song properties",
                () -> assertEquals("Bohemian Rhapsody", song.getTitle(), "Назва має співпадати"),
                () -> assertEquals("Queen", song.getArtist(), "Виконавець має співпадати"),
                () -> assertEquals(MusicStyle.ROCK, song.getStyle(), "Стиль має співпадати"),
                () -> assertEquals(354, song.getDurationSeconds(), "Тривалість має співпадати"),
                () -> assertEquals(1975, song.getYear(), "Рік має співпадати"),
                () -> assertEquals("SONG", song.getType(), "Тип має бути SONG")
        );
    }

    @Test
    @DisplayName("Конструктор з lyrics і featuring працює коректно")
    void testSongConstructorWithLyrics() {
        // ARRANGE & ACT
        Song songWithLyrics = new Song("Test Song", "Artist", MusicStyle.POP,
                200, 2020, "Some lyrics", true);

        // ASSERT
        assertAll(
                () -> assertEquals("Some lyrics", songWithLyrics.getLyrics()),
                () -> assertTrue(songWithLyrics.isHasFeaturing())
        );
    }

    @Test
    @DisplayName("Метод validate() повертає true для валідної пісні")
    void testValidSong() {
        // ACT
        boolean isValid = song.validate();

        // ASSERT
        assertTrue(isValid, "Валідна пісня має пройти перевірку");
    }

    @Test
    @DisplayName("Метод validate() повертає false для пісні з порожньою назвою")
    void testInvalidSongEmptyTitle() {
        // ARRANGE
        song.setTitle("");

        // ACT
        boolean isValid = song.validate();

        // ASSERT
        assertFalse(isValid, "Пісня з порожньою назвою не валідна");
    }

    @Test
    @DisplayName("Метод validate() повертає false для пісні з від'ємною тривалістю")
    void testInvalidSongNegativeDuration() {
        // ARRANGE
        song.setDurationSeconds(-10);

        // ACT
        boolean isValid = song.validate();

        // ASSERT
        assertFalse(isValid, "Пісня з від'ємною тривалістю не валідна");
    }

    @Test
    @DisplayName("Метод validate() повертає false для пісні з невалідним роком")
    void testInvalidSongBadYear() {
        // ARRANGE
        song.setYear(1800); // менше мінімального (1900)

        // ACT
        boolean isValid = song.validate();

        // ASSERT
        assertFalse(isValid, "Пісня з роком < 1900 не валідна");
    }

    @Test
    @DisplayName("getFormattedDuration() форматує тривалість без годин")
    void testFormattedDurationWithoutHours() {
        // ARRANGE
        song.setDurationSeconds(125); // 2:05

        // ACT
        String formatted = song.getFormattedDuration();

        // ASSERT
        assertEquals("02:05", formatted);
    }

    @Test
    @DisplayName("getFormattedDuration() форматує тривалість з годинами")
    void testFormattedDurationWithHours() {
        // ARRANGE
        song.setDurationSeconds(3661); // 1:01:01

        // ACT
        String formatted = song.getFormattedDuration();

        // ASSERT
        assertEquals("01:01:01", formatted);
    }

    @Test
    @DisplayName("setTitle() змінює назву пісні")
    void testSetTitle() {
        // ACT
        song.setTitle("New Title");

        // ASSERT
        assertEquals("New Title", song.getTitle());
    }

    @Test
    @DisplayName("setArtist() змінює виконавця")
    void testSetArtist() {
        // ACT
        song.setArtist("New Artist");

        // ASSERT
        assertEquals("New Artist", song.getArtist());
    }

    @Test
    @DisplayName("setStyle() змінює стиль")
    void testSetStyle() {
        // ACT
        song.setStyle(MusicStyle.JAZZ);

        // ASSERT
        assertEquals(MusicStyle.JAZZ, song.getStyle());
    }

    @Test
    @DisplayName("setLyrics() змінює текст пісні")
    void testSetLyrics() {
        // ACT
        song.setLyrics("New lyrics");

        // ASSERT
        assertEquals("New lyrics", song.getLyrics());
    }

    @Test
    @DisplayName("setHasFeaturing() змінює прапорець featuring")
    void testSetHasFeaturing() {
        // ACT
        song.setHasFeaturing(true);

        // ASSERT
        assertTrue(song.isHasFeaturing());
    }

    @Test
    @DisplayName("getInfo() повертає інформацію про пісню")
    void testGetInfo() {
        // ACT
        String info = song.getInfo();

        // ASSERT
        assertAll(
                () -> assertNotNull(info),
                () -> assertTrue(info.contains("Bohemian Rhapsody")),
                () -> assertTrue(info.contains("Queen")),
                () -> assertTrue(info.contains("ROCK"))
        );
    }

    @Test
    @DisplayName("toFileString() генерує правильний формат для файлу")
    void testToFileString() {
        // ACT
        String fileString = song.toFileString();

        // ASSERT
        assertAll(
                () -> assertNotNull(fileString),
                () -> assertTrue(fileString.contains("SONG")),
                () -> assertTrue(fileString.contains("Bohemian Rhapsody")),
                () -> assertTrue(fileString.contains("Queen"))
        );
    }

    @Test
    @DisplayName("equals() порівнює пісні по ID")
    void testEquals() {
        // ARRANGE
        Song song1 = new Song("Song1", "Artist", MusicStyle.ROCK, 200, 2020);
        Song song2 = new Song("Song2", "Artist", MusicStyle.POP, 300, 2021);
        song1.setId(1);
        song2.setId(1);

        Song song3 = new Song("Song3", "Artist", MusicStyle.JAZZ, 150, 2019);
        song3.setId(2);

        // ACT & ASSERT
        assertEquals(song1, song2, "Пісні з однаковим ID мають бути рівні");
        assertNotEquals(song1, song3, "Пісні з різними ID не рівні");
    }

    @Test
    @DisplayName("hashCode() генерує однаковий хеш для однакових ID")
    void testHashCode() {
        // ARRANGE
        Song song1 = new Song("Song1", "Artist", MusicStyle.ROCK, 200, 2020);
        Song song2 = new Song("Song2", "Artist", MusicStyle.POP, 300, 2021);
        song1.setId(5);
        song2.setId(5);

        // ACT & ASSERT
        assertEquals(song1.hashCode(), song2.hashCode());
    }
}