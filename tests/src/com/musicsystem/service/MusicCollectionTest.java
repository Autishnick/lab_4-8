package com.musicsystem.service;

import com.musicsystem.model.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тести для класу MusicCollection")
class MusicCollectionTest {

    private MusicCollection collection;
    private Song song1;
    private Song song2;
    private Song song3;

    @BeforeEach
    @DisplayName("Підготовка колекції з 3 піснями")
    void setUp() {
        collection = new MusicCollection();
        song1 = new Song("Song One", "Artist A", MusicStyle.ROCK, 200, 2020);
        song2 = new Song("Song Two", "Artist B", MusicStyle.POP, 180, 2021);
        song3 = new Song("Song Three", "Artist A", MusicStyle.ROCK, 240, 2019);

        collection.add(song1);
        collection.add(song2);
        collection.add(song3);
    }

    @Test
    @DisplayName("add() додає валідну композицію")
    void testAddValidComposition() {
        int before = collection.getTotalCount();
        Song song = new Song("New Song", "Artist X", MusicStyle.JAZZ, 300, 2022);
        collection.add(song);
        assertEquals(before + 1, collection.getTotalCount());
    }

    @Test
    @DisplayName("add() кидає виняток для невалідної композиції")
    void testAddInvalidComposition() {
        Song invalidSong = new Song("", "Artist", MusicStyle.POP, 100, 2022);
        assertThrows(IllegalArgumentException.class, () -> collection.add(invalidSong));
    }

    @Test
    @DisplayName("remove() видаляє композицію за об’єктом")
    void testRemoveByObject() {
        assertTrue(collection.remove(song2));
        assertEquals(2, collection.getTotalCount());
    }

    @Test
    @DisplayName("remove() видаляє композицію за ID")
    void testRemoveById() {
        int id = song1.getId();
        assertTrue(collection.remove(id));
        assertEquals(2, collection.getTotalCount());
    }

    @Test
    @DisplayName("getById() повертає правильну композицію")
    void testGetById() {
        int id = song3.getId();
        MusicComposition found = collection.getById(id);
        assertNotNull(found);
        assertEquals("Song Three", found.getTitle());
    }

    @Test
    @DisplayName("getById() повертає null, якщо не знайдено")
    void testGetByIdNotFound() {
        assertNull(collection.getById(9999));
    }

    @Test
    @DisplayName("getAll() повертає копію списку")
    void testGetAllReturnsCopy() {
        List<MusicComposition> list = collection.getAll();
        list.clear();
        assertEquals(3, collection.getTotalCount(), "Оригінальна колекція не має змінюватися");
    }

    @Test
    @DisplayName("search() знаходить за назвою або виконавцем")
    void testSearch() {
        List<MusicComposition> found = collection.search("Artist A");
        assertEquals(2, found.size());
    }

    @Test
    @DisplayName("filterByStyle() фільтрує за стилем")
    void testFilterByStyle() {
        List<MusicComposition> rockSongs = collection.filterByStyle(MusicStyle.ROCK);
        assertEquals(2, rockSongs.size());
        assertTrue(rockSongs.stream().allMatch(s -> s.getStyle() == MusicStyle.ROCK));
    }

    @Test
    @DisplayName("filterByArtist() фільтрує за виконавцем")
    void testFilterByArtist() {
        List<MusicComposition> result = collection.filterByArtist("artist b");
        assertEquals(1, result.size());
        assertEquals("Song Two", result.get(0).getTitle());
    }

    @Test
    @DisplayName("findByDuration() знаходить композиції у межах тривалості")
    void testFindByDuration() {
        List<MusicComposition> result = collection.findByDuration(190, 250);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("getTotalCount() повертає правильну кількість")
    void testGetTotalCount() {
        assertEquals(3, collection.getTotalCount());
    }

    @Test
    @DisplayName("getTotalDuration() підсумовує тривалість усіх композицій")
    void testGetTotalDuration() {
        int total = song1.getDurationSeconds() + song2.getDurationSeconds() + song3.getDurationSeconds();
        assertEquals(total, collection.getTotalDuration());
    }

    @Test
    @DisplayName("clear() очищає колекцію")
    void testClear() {
        collection.clear();
        assertTrue(collection.isEmpty());
    }

    @Test
    @DisplayName("update() замінює композицію з тим самим ID")
    void testUpdate() {
        song2.setArtist("Updated Artist");
        collection.update(song2);
        MusicComposition updated = collection.getById(song2.getId());
        assertEquals("Updated Artist", updated.getArtist());
    }

    @Test
    @DisplayName("update() кидає виняток, якщо композицію не знайдено")
    void testUpdateNotFound() {
        Song fake = new Song("Ghost", "Nobody", MusicStyle.POP, 100, 2022);
        fake.setId(999);
        assertThrows(IllegalArgumentException.class, () -> collection.update(fake));
    }

    @Test
    @DisplayName("update() кидає виняток, якщо композиція невалідна")
    void testUpdateInvalid() {
        Song invalid = new Song("", "Artist", MusicStyle.ROCK, 100, 2020);
        invalid.setId(song1.getId());
        assertThrows(IllegalArgumentException.class, () -> collection.update(invalid));
    }
}
