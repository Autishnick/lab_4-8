package com.musicsystem.service;

import com.musicsystem.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

@DisplayName("Тести для класу Statistics")
class StatisticsTest {

    private MusicCollection collection;
    private Statistics statistics;

    @BeforeEach
    void setUp() {
        collection = new MusicCollection();
        statistics = new Statistics(collection);

        // Додаємо тестові композиції
        Song rock1 = new Song("Rock 1", "Artist A", MusicStyle.ROCK, 180, 2020);
        Song rock2 = new Song("Rock 2", "Artist A", MusicStyle.ROCK, 200, 2021);
        Song pop = new Song("Pop 1", "Artist B", MusicStyle.POP, 150, 2022);
        Song jazz = new Song("Jazz 1", "Artist C", MusicStyle.JAZZ, 240, 2023);

        collection.add(rock1);
        collection.add(rock2);
        collection.add(pop);
        collection.add(jazz);
    }

    @Test
    @DisplayName("getTotalCount() повертає кількість композицій")
    void testGetTotalCount() {
        // ACT
        int count = statistics.getTotalCount();

        // ASSERT
        assertEquals(4, count);
    }

    @Test
    @DisplayName("getTotalDuration() повертає загальну тривалість")
    void testGetTotalDuration() {
        // ACT
        int duration = statistics.getTotalDuration();

        // ASSERT
        assertEquals(770, duration); // 180 + 200 + 150 + 240
    }

    @Test
    @DisplayName("getFormattedTotalDuration() форматує тривалість")
    void testGetFormattedTotalDuration() {
        // ACT
        String formatted = statistics.getFormattedTotalDuration();

        // ASSERT
        assertTrue(formatted.contains("12 хв")); // 770 сек = 12 хв 50 сек
    }

    @Test
    @DisplayName("getStyleDistribution() рахує розподіл за стилями")
    void testGetStyleDistribution() {
        // ACT
        Map<MusicStyle, Integer> distribution = statistics.getStyleDistribution();

        // ASSERT
        assertAll(
                () -> assertEquals(2, distribution.get(MusicStyle.ROCK)),
                () -> assertEquals(1, distribution.get(MusicStyle.POP)),
                () -> assertEquals(1, distribution.get(MusicStyle.JAZZ))
        );
    }

    @Test
    @DisplayName("getStyleDistribution() сортує за кількістю")
    void testGetStyleDistributionSorted() {
        // ACT
        Map<MusicStyle, Integer> distribution = statistics.getStyleDistribution();
        var iterator = distribution.keySet().iterator();

        // ASSERT - перший має бути ROCK (найбільше треків)
        assertEquals(MusicStyle.ROCK, iterator.next());
    }

    @Test
    @DisplayName("getArtistStatistics() рахує треки по виконавцях")
    void testGetArtistStatistics() {
        // ACT
        Map<String, Integer> artistStats = statistics.getArtistStatistics();

        // ASSERT
        assertAll(
                () -> assertEquals(2, artistStats.get("Artist A")),
                () -> assertEquals(1, artistStats.get("Artist B")),
                () -> assertEquals(1, artistStats.get("Artist C"))
        );
    }

    @Test
    @DisplayName("getArtistStatistics() повертає максимум 10 виконавців")
    void testGetArtistStatisticsLimit() {
        // ARRANGE - додаємо багато виконавців
        for (int i = 0; i < 15; i++) {
            Song song = new Song("Song" + i, "Artist " + i, MusicStyle.ROCK, 100, 2020);
            collection.add(song);
        }

        // ACT
        Map<String, Integer> artistStats = statistics.getArtistStatistics();

        // ASSERT
        assertTrue(artistStats.size() <= 10);
    }

    @Test
    @DisplayName("getArtistDurationStatistics() рахує тривалість по виконавцях")
    void testGetArtistDurationStatistics() {
        // ACT
        Map<String, Integer> durationStats = statistics.getArtistDurationStatistics();

        // ASSERT
        assertEquals(380, durationStats.get("Artist A")); // 180 + 200
    }

    @Test
    @DisplayName("getAverageDuration() рахує середню тривалість")
    void testGetAverageDuration() {
        // ACT
        double average = statistics.getAverageDuration();

        // ASSERT
        assertEquals(192.5, average, 0.1); // 770 / 4
    }

    @Test
    @DisplayName("getAverageDuration() повертає 0 для порожньої колекції")
    void testGetAverageDurationEmpty() {
        // ARRANGE
        MusicCollection emptyCollection = new MusicCollection();
        Statistics emptyStats = new Statistics(emptyCollection);

        // ACT
        double average = emptyStats.getAverageDuration();

        // ASSERT
        assertEquals(0.0, average);
    }

    @Test
    @DisplayName("getLongestTrack() знаходить найдовший трек")
    void testGetLongestTrack() {
        // ACT
        MusicComposition longest = statistics.getLongestTrack();

        // ASSERT
        assertAll(
                () -> assertNotNull(longest),
                () -> assertEquals(240, longest.getDurationSeconds())
        );
    }

    @Test
    @DisplayName("getLongestTrack() повертає null для порожньої колекції")
    void testGetLongestTrackEmpty() {
        // ARRANGE
        MusicCollection emptyCollection = new MusicCollection();
        Statistics emptyStats = new Statistics(emptyCollection);

        // ACT
        MusicComposition longest = emptyStats.getLongestTrack();

        // ASSERT
        assertNull(longest);
    }

    @Test
    @DisplayName("getShortestTrack() знаходить найкоротший трек")
    void testGetShortestTrack() {
        // ACT
        MusicComposition shortest = statistics.getShortestTrack();

        // ASSERT
        assertAll(
                () -> assertNotNull(shortest),
                () -> assertEquals(150, shortest.getDurationSeconds())
        );
    }

    @Test
    @DisplayName("getShortestTrack() повертає null для порожньої колекції")
    void testGetShortestTrackEmpty() {
        // ARRANGE
        MusicCollection emptyCollection = new MusicCollection();
        Statistics emptyStats = new Statistics(emptyCollection);

        // ACT
        MusicComposition shortest = emptyStats.getShortestTrack();

        // ASSERT
        assertNull(shortest);
    }

    @Test
    @DisplayName("generateReport() генерує повний звіт")
    void testGenerateReport() {
        // ACT
        String report = statistics.generateReport();

        // ASSERT
        assertAll(
                () -> assertNotNull(report),
                () -> assertTrue(report.contains("СТАТИСТИКА")),
                () -> assertTrue(report.contains("4")), // кількість композицій
                () -> assertTrue(report.contains("Rock")),
                () -> assertTrue(report.contains("Artist A"))
        );
    }
}