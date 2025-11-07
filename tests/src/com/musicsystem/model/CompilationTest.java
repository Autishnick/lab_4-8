package com.musicsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тести для класу Compilation")
class CompilationTest {

    private Compilation compilation;
    private Song song1;
    private Song song2;
    private Song song3;

    @BeforeEach
    void setUp() {
        compilation = new Compilation("Rock Hits", "Best rock songs");

        song1 = new Song("Song 1", "Artist 1", MusicStyle.ROCK, 180, 2020);
        song2 = new Song("Song 2", "Artist 2", MusicStyle.ROCK, 240, 2021);
        song3 = new Song("Song 3", "Artist 3", MusicStyle.POP, 200, 2022);
    }

    @Test
    @DisplayName("Конструктор створює збірку з правильними параметрами")
    void testCompilationConstructor() {
        // ASSERT
        assertAll(
                () -> assertEquals("Rock Hits", compilation.getName()),
                () -> assertEquals("Best rock songs", compilation.getDescription()),
                () -> assertEquals(0, compilation.getTrackCount()),
                () -> assertTrue(compilation.getTracks().isEmpty())
        );
    }

    @Test
    @DisplayName("addTrack() додає композицію до збірки")
    void testAddTrack() {
        // ACT
        compilation.addTrack(song1);

        // ASSERT
        assertAll(
                () -> assertEquals(1, compilation.getTrackCount()),
                () -> assertTrue(compilation.getTracks().contains(song1))
        );
    }

    @Test
    @DisplayName("addTrack() не додає null")
    void testAddTrackNull() {
        // ACT
        compilation.addTrack(null);

        // ASSERT
        assertEquals(0, compilation.getTrackCount());
    }

    @Test
    @DisplayName("addTrack() не додає дублікати")
    void testAddTrackDuplicate() {
        // ACT
        compilation.addTrack(song1);
        compilation.addTrack(song1); // повторно

        // ASSERT
        assertEquals(1, compilation.getTrackCount(), "Дублікати не мають додаватись");
    }

    @Test
    @DisplayName("addTrack() додає декілька різних треків")
    void testAddMultipleTracks() {
        // ACT
        compilation.addTrack(song1);
        compilation.addTrack(song2);
        compilation.addTrack(song3);

        // ASSERT
        assertEquals(3, compilation.getTrackCount());
    }

    @Test
    @DisplayName("removeTrack() видаляє композицію зі збірки")
    void testRemoveTrack() {
        // ARRANGE
        compilation.addTrack(song1);
        compilation.addTrack(song2);

        // ACT
        boolean removed = compilation.removeTrack(song1);

        // ASSERT
        assertAll(
                () -> assertTrue(removed),
                () -> assertEquals(1, compilation.getTrackCount()),
                () -> assertFalse(compilation.getTracks().contains(song1)),
                () -> assertTrue(compilation.getTracks().contains(song2))
        );
    }

    @Test
    @DisplayName("removeTrack() повертає false при видаленні неіснуючого треку")
    void testRemoveNonExistentTrack() {
        // ARRANGE
        compilation.addTrack(song1);

        // ACT
        boolean removed = compilation.removeTrack(song2);

        // ASSERT
        assertFalse(removed);
    }

    @Test
    @DisplayName("removeTrack(id) видаляє трек по ID")
    void testRemoveTrackById() {
        // ARRANGE
        song1.setId(10);
        compilation.addTrack(song1);
        compilation.addTrack(song2);

        // ACT
        boolean removed = compilation.removeTrack(10);

        // ASSERT
        assertAll(
                () -> assertTrue(removed),
                () -> assertEquals(1, compilation.getTrackCount())
        );
    }

    @Test
    @DisplayName("calculateDuration() рахує загальну тривалість")
    void testCalculateDuration() {
        // ARRANGE
        compilation.addTrack(song1); // 180 сек
        compilation.addTrack(song2); // 240 сек

        // ACT
        int totalDuration = compilation.calculateDuration();

        // ASSERT
        assertEquals(420, totalDuration); // 180 + 240
    }

    @Test
    @DisplayName("calculateDuration() повертає 0 для порожньої збірки")
    void testCalculateDurationEmpty() {
        // ACT
        int duration = compilation.calculateDuration();

        // ASSERT
        assertEquals(0, duration);
    }

    @Test
    @DisplayName("getFormattedDuration() форматує тривалість без годин")
    void testGetFormattedDurationWithoutHours() {
        // ARRANGE
        compilation.addTrack(song1); // 180 сек = 3:00

        // ACT
        String formatted = compilation.getFormattedDuration();

        // ASSERT
        assertEquals("03:00", formatted);
    }

    @Test
    @DisplayName("getFormattedDuration() форматує тривалість з годинами")
    void testGetFormattedDurationWithHours() {
        // ARRANGE
        song1.setDurationSeconds(3600); // 1 година
        song2.setDurationSeconds(125);  // 2:05
        compilation.addTrack(song1);
        compilation.addTrack(song2);

        // ACT
        String formatted = compilation.getFormattedDuration();

        // ASSERT
        assertEquals("01:02:05", formatted);
    }

    @Test
    @DisplayName("sortByStyle() сортує треки за стилем")
    void testSortByStyle() {
        // ARRANGE - додаємо треки в різному порядку
        compilation.addTrack(song3); // POP
        compilation.addTrack(song1); // ROCK
        compilation.addTrack(song2); // ROCK

        // ACT
        compilation.sortByStyle();

        // ASSERT
        var tracks = compilation.getTracks();
        assertAll(
                () -> assertEquals(MusicStyle.POP, tracks.get(0).getStyle()),
                () -> assertEquals(MusicStyle.ROCK, tracks.get(1).getStyle()),
                () -> assertEquals(MusicStyle.ROCK, tracks.get(2).getStyle())
        );
    }

    @Test
    @DisplayName("estimateSizeMB() розраховує орієнтовний розмір")
    void testEstimateSizeMB() {
        // ARRANGE
        song1.setDurationSeconds(300); // 5 хвилин
        compilation.addTrack(song1);

        // ACT
        int sizeMB = compilation.estimateSizeMB();

        // ASSERT
        assertEquals(25, sizeMB); // 5 хвилин * 5 MB/хв
    }

    @Test
    @DisplayName("getInfo() повертає інформацію про збірку")
    void testGetInfo() {
        // ARRANGE
        compilation.addTrack(song1);

        // ACT
        String info = compilation.getInfo();

        // ASSERT
        assertAll(
                () -> assertNotNull(info),
                () -> assertTrue(info.contains("Rock Hits")),
                () -> assertTrue(info.contains("Best rock songs"))
        );
    }

    @Test
    @DisplayName("setName() змінює назву збірки")
    void testSetName() {
        // ACT
        compilation.setName("New Name");

        // ASSERT
        assertEquals("New Name", compilation.getName());
    }

    @Test
    @DisplayName("setDescription() змінює опис")
    void testSetDescription() {
        // ACT
        compilation.setDescription("New description");

        // ASSERT
        assertEquals("New description", compilation.getDescription());
    }

    @Test
    @DisplayName("getTracks() повертає копію списку")
    void testGetTracksReturnsCopy() {
        // ARRANGE
        compilation.addTrack(song1);

        // ACT
        var tracks = compilation.getTracks();
        tracks.clear(); // очищаємо копію

        // ASSERT
        assertEquals(1, compilation.getTrackCount(),
                "Зміна копії не має впливати на оригінал");
    }

    @Test
    @DisplayName("equals() порівнює збірки по ID")
    void testEquals() {
        // ARRANGE
        Compilation comp1 = new Compilation("Comp1", "Desc1");
        Compilation comp2 = new Compilation("Comp2", "Desc2");
        comp1.setId(1);
        comp2.setId(1);

        Compilation comp3 = new Compilation("Comp3", "Desc3");
        comp3.setId(2);

        // ASSERT
        assertEquals(comp1, comp2);
        assertNotEquals(comp1, comp3);
    }
}