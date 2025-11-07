package com.musicsystem.service;

import com.musicsystem.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@DisplayName("Тести для класу MusicCollection")
class MusicCollectionTest {

    private MusicCollection collection;
    private Song song1;
    private Song song2;
    private Song song3;

    @BeforeEach
    void setUp() {
        collection = new MusicCollection();

        song1 = new Song("Rock Song", "Rock Artist", MusicStyle.ROCK, 180, 2020);
        song2 = new Song("Pop Song", "Pop Artist", MusicStyle.POP, 200, 2021);
        song3 = new Song("Jazz Song", "Rock Artist", MusicStyle.JAZZ, 240, 2022);
    }

    @Test
    @DisplayName("Нова колекція порожня")
    void testNewCollectionIsEmpty() {
        // ASSERT
        assertAll(
                () -> assertTrue(collection.isEmpty()),
                () -> assertEquals(0, collection.getTotalCount()),
                () -> assertEquals(0, collection.getTotalDuration())
        );
    }

    @Test
    @DisplayName("add() додає валідну композицію")
    void testAddValidComposition() {
        // ACT
        collection.add(song1);

        // ASSERT
        assertAll(
                () -> assertEquals(1, collection.getTotalCount()),
                () -> assertFalse(collection.isEmpty()),
                () -> assertTrue(collection.getAll().contains(song1))
        );
    }

    @Test
    @DisplayName("add() кидає виняток при додаванні null")
    void testAddNull() {
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            collection.add(null);
        });
    }

    @Test
    @DisplayName("add() кидає виняток при додаванні невалідної композиції")
    void testAddInvalidComposition() {
        // ARRANGE
        Song invalidSong = new Song("", "", MusicStyle.ROCK, -10, 2020);

        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            collection.add(invalidSong);
        });
    }

    @Test
    @DisplayName("add() додає декілька композицій")
    void testAddMultipleCompositions() {
        // ACT
        collection.add(song1);
        collection.add(song2);
        collection.add(song3);

        // ASSERT
        assertEquals(3, collection.getTotalCount());
    }

    @Test
    @DisplayName("remove() видаляє композицію")
    void testRemoveComposition() {
        // ARRANGE
        collection.add(song1);
        collection.add(song2);

        // ACT
        boolean removed = collection.remove(song1);

        // ASSERT
        assertAll(
                () -> assertTrue(removed),
                () -> assertEquals(1, collection.getTotalCount()),
                () -> assertFalse(collection.getAll().contains(song1))
        );
    }

    @Test
    @DisplayName("remove() повертає false для неіснуючої композиції")
    void testRemoveNonExistent() {
        // ARRANGE
        collection.add(song1);

        // ACT
        boolean removed = collection.remove(song2);

        // ASSERT
        assertFalse(removed);
    }

    @Test
    @DisplayName("remove(id) видаляє композицію по ID")
    void testRemoveById() {
        // ARRANGE
        song1.setId(10);
        collection.add(song1);
        collection.add(song2);

        // ACT
        boolean removed = collection.remove(10);

        // ASSERT
        assertAll(
                () -> assertTrue(removed),
                () -> assertEquals(1, collection.getTotalCount())
        );
    }

    @Test
    @DisplayName("getById() знаходить композицію")
    void testGetById() {
        // ARRANGE
        song1.setId(5);
        collection.add(song1);

        // ACT
        MusicComposition found = collection.getById(5);

        // ASSERT
        assertAll(
                () -> assertNotNull(found),
                () -> assertEquals(song1, found)
        );
    }

    @Test
    @DisplayName("getById() повертає null для неіснуючого ID")
    void testGetByIdNotFound() {
        // ACT
        MusicComposition found = collection.getById(999);

        // ASSERT
        assertNull(found);
    }

    @Test
    @DisplayName("getAll() повертає всі композиції")
    void testGetAll() {
        // ARRANGE
        collection.add(song1);
        collection.add(song2);

        // ACT
        List<MusicComposition> all = collection.getAll();

        // ASSERT
        assertAll(
                () -> assertEquals(2, all.size()),
                () -> assertTrue(all.contains(song1)),
                () -> assertTrue(all.contains(song2))
        );
    }

    @Test
    @DisplayName("search() знаходить композиції за назвою")
    void testSearchByTitle() {
        // ARRANGE
        collection.add(song1); // "Rock Song"
        collection.add(song2); // "Pop Song"

        // ACT
        List<MusicComposition> results = collection.search("Rock");

        // ASSERT
        assertAll(
                () -> assertEquals(1, results.size()),
                () -> assertTrue(results.contains(song1))
        );
    }

    @Test
    @DisplayName("search() знаходить композиції за виконавцем")
    void testSearchByArtist() {
        // ARRANGE
        collection.add(song1); // "Rock Artist"
        collection.add(song2); // "Pop Artist"
        collection.add(song3); // "Rock Artist"

        // ACT
        List<MusicComposition> results = collection.search("Rock Artist");

        // ASSERT
        assertEquals(2, results.size());
    }

    @Test
    @DisplayName("search() не чутливий до регістру")
    void testSearchCaseInsensitive() {
        // ARRANGE
        collection.add(song1);

        // ACT
        List<MusicComposition> results = collection.search("ROCK SONG");

        // ASSERT
        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("filterByStyle() фільтрує за стилем")
    void testFilterByStyle() {
        // ARRANGE
        collection.add(song1); // ROCK
        collection.add(song2); // POP
        collection.add(song3); // JAZZ

        // ACT
        List<MusicComposition> rockSongs = collection.filterByStyle(MusicStyle.ROCK);

        // ASSERT
        assertAll(
                () -> assertEquals(1, rockSongs.size()),
                () -> assertTrue(rockSongs.contains(song1))
        );
    }

    @Test
    @DisplayName("filterByArtist() фільтрує за виконавцем")
    void testFilterByArtist() {
        // ARRANGE
        collection.add(song1); // "Rock Artist"
        collection.add(song2); // "Pop Artist"
        collection.add(song3); // "Rock Artist"

        // ACT
        List<MusicComposition> results = collection.filterByArtist("Rock Artist");

        // ASSERT
        assertEquals(2, results.size());
    }

    @Test
    @DisplayName("findByDuration() знаходить треки у діапазоні")
    void testFindByDuration() {
        // ARRANGE
        collection.add(song1); // 180
        collection.add(song2); // 200
        collection.add(song3); // 240

        // ACT
        List<MusicComposition> results = collection.findByDuration(190, 250);

        // ASSERT
        assertAll(
                () -> assertEquals(2, results.size()),
                () -> assertTrue(results.contains(song2)),
                () -> assertTrue(results.contains(song3))
        );
    }

    @Test
    @DisplayName("getTotalDuration() рахує загальну тривалість")
    void testGetTotalDuration() {
        // ARRANGE
        collection.add(song1); // 180
        collection.add(song2); // 200

        // ACT
        int total = collection.getTotalDuration();

        // ASSERT
        assertEquals(380, total);
    }

    @Test
    @DisplayName("update() оновлює композицію")
    void testUpdateComposition() {
        // ARRANGE
        song1.setId(1);
        collection.add(song1);

        Song updatedSong = new Song("Updated Title", "Rock Artist",
                MusicStyle.ROCK, 180, 2020);
        updatedSong.setId(1);

        // ACT
        collection.update(updatedSong);

        // ASSERT
        MusicComposition found = collection.getById(1);
        assertEquals("Updated Title", found.getTitle());
    }

    @Test
    @DisplayName("update() кидає виняток при оновленні невалідної композиції")
    void testUpdateInvalidComposition() {
        // ARRANGE
        Song invalidSong = new Song("", "", MusicStyle.ROCK, -10, 2020);

        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            collection.update(invalidSong);
        });
    }

    @Test
    @DisplayName("update() кидає виняток для неіснуючої композиції")
    void testUpdateNonExistent() {
        // ARRANGE
        song1.setId(999);

        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            collection.update(song1);
        });
    }

    @Test
    @DisplayName("clear() очищає колекцію")
    void testClear() {
        // ARRANGE
        collection.add(song1);
        collection.add(song2);

        // ACT
        collection.clear();

        // ASSERT
        assertAll(
                () -> assertTrue(collection.isEmpty()),
                () -> assertEquals(0, collection.getTotalCount())
        );
    }
}