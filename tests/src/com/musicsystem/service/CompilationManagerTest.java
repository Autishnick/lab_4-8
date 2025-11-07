package com.musicsystem.service;

import com.musicsystem.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тести для класу CompilationManager")
class CompilationManagerTest {

    private CompilationManager manager;
    private Song song1;
    private Song song2;

    @BeforeEach
    void setUp() {
        manager = new CompilationManager();
        song1 = new Song("Song 1", "Artist", MusicStyle.ROCK, 180, 2020);
        song2 = new Song("Song 2", "Artist", MusicStyle.POP, 200, 2021);
    }

    @Test
    @DisplayName("Новий менеджер порожній")
    void testNewManagerIsEmpty() {
        assertAll(
                () -> assertTrue(manager.isEmpty()),
                () -> assertEquals(0, manager.getTotalCount())
        );
    }

    @Test
    @DisplayName("create() створює нову збірку")
    void testCreate() {
        // ACT
        Compilation compilation = manager.create("Test", "Description");

        // ASSERT
        assertAll(
                () -> assertNotNull(compilation),
                () -> assertEquals("Test", compilation.getName()),
                () -> assertEquals("Description", compilation.getDescription()),
                () -> assertEquals(1, manager.getTotalCount())
        );
    }

    @Test
    @DisplayName("create() кидає виняток для порожньої назви")
    void testCreateEmptyName() {
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            manager.create("", "Description");
        });
    }

    @Test
    @DisplayName("create() кидає виняток для null назви")
    void testCreateNullName() {
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            manager.create(null, "Description");
        });
    }

    @Test
    @DisplayName("delete() видаляє збірку")
    void testDelete() {
        // ARRANGE
        Compilation comp = manager.create("Test", "Desc");
        int id = comp.getId();

        // ACT
        boolean deleted = manager.delete(id);

        // ASSERT
        assertAll(
                () -> assertTrue(deleted),
                () -> assertTrue(manager.isEmpty())
        );
    }

    @Test
    @DisplayName("delete() повертає false для неіснуючого ID")
    void testDeleteNonExistent() {
        // ACT
        boolean deleted = manager.delete(999);

        // ASSERT
        assertFalse(deleted);
    }

    @Test
    @DisplayName("getById() знаходить збірку")
    void testGetById() {
        // ARRANGE
        Compilation comp = manager.create("Test", "Desc");
        int id = comp.getId();

        // ACT
        Compilation found = manager.getById(id);

        // ASSERT
        assertAll(
                () -> assertNotNull(found),
                () -> assertEquals(comp, found)
        );
    }

    @Test
    @DisplayName("getById() повертає null для неіснуючого ID")
    void testGetByIdNotFound() {
        // ACT
        Compilation found = manager.getById(999);

        // ASSERT
        assertNull(found);
    }

    @Test
    @DisplayName("getAll() повертає всі збірки")
    void testGetAll() {
        // ARRANGE
        manager.create("Comp1", "Desc1");
        manager.create("Comp2", "Desc2");

        // ACT
        var compilations = manager.getAll();

        // ASSERT
        assertEquals(2, compilations.size());
    }

    @Test
    @DisplayName("addTrackToCompilation() додає трек до збірки")
    void testAddTrackToCompilation() {
        // ARRANGE
        Compilation comp = manager.create("Test", "Desc");

        // ACT
        manager.addTrackToCompilation(comp.getId(), song1);

        // ASSERT
        assertEquals(1, comp.getTrackCount());
    }

    @Test
    @DisplayName("addTrackToCompilation() кидає виняток для неіснуючої збірки")
    void testAddTrackToNonExistentCompilation() {
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            manager.addTrackToCompilation(999, song1);
        });
    }

    @Test
    @DisplayName("removeTrackFromCompilation() видаляє трек")
    void testRemoveTrackFromCompilation() {
        // ARRANGE
        Compilation comp = manager.create("Test", "Desc");
        song1.setId(10);
        comp.addTrack(song1);

        // ACT
        manager.removeTrackFromCompilation(comp.getId(), 10);

        // ASSERT
        assertEquals(0, comp.getTrackCount());
    }

    @Test
    @DisplayName("removeTrackFromCompilation() кидає виняток для неіснуючої збірки")
    void testRemoveTrackFromNonExistentCompilation() {
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            manager.removeTrackFromCompilation(999, 10);
        });
    }

    @Test
    @DisplayName("sortCompilationByStyle() сортує збірку")
    void testSortCompilationByStyle() {
        // ARRANGE
        Compilation comp = manager.create("Test", "Desc");
        comp.addTrack(song2); // POP
        comp.addTrack(song1); // ROCK

        // ACT
        manager.sortCompilationByStyle(comp.getId());

        // ASSERT
        var tracks = comp.getTracks();
        assertEquals(MusicStyle.POP, tracks.get(0).getStyle());
    }

    @Test
    @DisplayName("sortCompilationByStyle() кидає виняток для неіснуючої збірки")
    void testSortNonExistentCompilation() {
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            manager.sortCompilationByStyle(999);
        });
    }

    @Test
    @DisplayName("updateCompilation() оновлює збірку")
    void testUpdateCompilation() {
        // ARRANGE
        Compilation comp = manager.create("Old Name", "Desc");
        comp.setName("New Name");

        // ACT
        manager.updateCompilation(comp);

        // ASSERT
        Compilation updated = manager.getById(comp.getId());
        assertEquals("New Name", updated.getName());
    }

    @Test
    @DisplayName("updateCompilation() кидає виняток для null")
    void testUpdateNull() {
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            manager.updateCompilation(null);
        });
    }

    @Test
    @DisplayName("updateCompilation() кидає виняток для неіснуючої збірки")
    void testUpdateNonExistent() {
        // ARRANGE
        Compilation comp = new Compilation("Test", "Desc");
        comp.setId(999);

        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            manager.updateCompilation(comp);
        });
    }

    @Test
    @DisplayName("clear() очищає менеджер")
    void testClear() {
        // ARRANGE
        manager.create("Comp1", "Desc1");
        manager.create("Comp2", "Desc2");

        // ACT
        manager.clear();

        // ASSERT
        assertTrue(manager.isEmpty());
    }
}