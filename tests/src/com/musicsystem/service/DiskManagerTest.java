package com.musicsystem.service;

import com.musicsystem.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тести для класу DiskManager")
class DiskManagerTest {

    private DiskManager manager;
    private Compilation smallCompilation;
    private Compilation largeCompilation;

    @BeforeEach
    void setUp() {
        manager = new DiskManager();

        // Маленька збірка (~25 MB)
        smallCompilation = new Compilation("Small", "Desc");
        Song song1 = new Song("Song", "Artist", MusicStyle.ROCK, 300, 2020); // 5 хв
        smallCompilation.addTrack(song1);

        // Велика збірка (~1000 MB)
        largeCompilation = new Compilation("Large", "Desc");
        for (int i = 0; i < 200; i++) { // 200 * 5 хв = 1000 хв
            Song song = new Song("Song" + i, "Artist", MusicStyle.ROCK, 300, 2020);
            largeCompilation.addTrack(song);
        }
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
    @DisplayName("burnCompilation() записує збірку на диск")
    void testBurnCompilation() {
        // ACT
        Disk disk = manager.burnCompilation(smallCompilation, DiskType.CD);

        // ASSERT
        assertAll(
                () -> assertNotNull(disk),
                () -> assertEquals(DiskType.CD, disk.getType()),
                () -> assertEquals(smallCompilation, disk.getCompilation()),
                () -> assertEquals(1, manager.getTotalCount())
        );
    }

    @Test
    @DisplayName("burnCompilation() кидає виняток для null збірки")
    void testBurnNullCompilation() {
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            manager.burnCompilation(null, DiskType.CD);
        });
    }

    @Test
    @DisplayName("burnCompilation() кидає виняток для порожньої збірки")
    void testBurnEmptyCompilation() {
        // ARRANGE
        Compilation empty = new Compilation("Empty", "Desc");

        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            manager.burnCompilation(empty, DiskType.CD);
        });
    }

    @Test
    @DisplayName("burnCompilation() кидає виняток якщо не вміщується")
    void testBurnCompilationTooLarge() {
        // ACT & ASSERT
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.burnCompilation(largeCompilation, DiskType.CD);
        });

        assertTrue(exception.getMessage().contains("не вміщується"));
    }

    @Test
    @DisplayName("deleteDisk() видаляє диск")
    void testDeleteDisk() {
        // ARRANGE
        Disk disk = manager.burnCompilation(smallCompilation, DiskType.CD);
        int id = disk.getId();

        // ACT
        boolean deleted = manager.deleteDisk(id);

        // ASSERT
        assertAll(
                () -> assertTrue(deleted),
                () -> assertTrue(manager.isEmpty())
        );
    }

    @Test
    @DisplayName("deleteDisk() повертає false для неіснуючого ID")
    void testDeleteNonExistentDisk() {
        // ACT
        boolean deleted = manager.deleteDisk(999);

        // ASSERT
        assertFalse(deleted);
    }

    @Test
    @DisplayName("getById() знаходить диск")
    void testGetById() {
        // ARRANGE
        Disk disk = manager.burnCompilation(smallCompilation, DiskType.CD);
        int id = disk.getId();

        // ACT
        Disk found = manager.getById(id);

        // ASSERT
        assertAll(
                () -> assertNotNull(found),
                () -> assertEquals(disk, found)
        );
    }

    @Test
    @DisplayName("getById() повертає null для неіснуючого ID")
    void testGetByIdNotFound() {
        // ACT
        Disk found = manager.getById(999);

        // ASSERT
        assertNull(found);
    }

    @Test
    @DisplayName("getAll() повертає всі диски")
    void testGetAll() {
        // ARRANGE
        manager.burnCompilation(smallCompilation, DiskType.CD);
        manager.burnCompilation(smallCompilation, DiskType.DVD);

        // ACT
        var disks = manager.getAll();

        // ASSERT
        assertEquals(2, disks.size());
    }

    @Test
    @DisplayName("getDisksByType() фільтрує за типом")
    void testGetDisksByType() {
        // ARRANGE
        manager.burnCompilation(smallCompilation, DiskType.CD);
        manager.burnCompilation(smallCompilation, DiskType.CD);
        manager.burnCompilation(smallCompilation, DiskType.DVD);

        // ACT
        var cdDisks = manager.getDisksByType(DiskType.CD);

        // ASSERT
        assertEquals(2, cdDisks.size());
    }

    @Test
    @DisplayName("canFitOnDisk() перевіряє чи вміщується")
    void testCanFitOnDisk() {
        // ACT
        boolean fitsOnCD = manager.canFitOnDisk(smallCompilation, DiskType.CD);
        boolean fitsOnDVD = manager.canFitOnDisk(largeCompilation, DiskType.DVD);
        boolean notFits = manager.canFitOnDisk(largeCompilation, DiskType.CD);

        // ASSERT
        assertAll(
                () -> assertTrue(fitsOnCD),
                () -> assertTrue(fitsOnDVD),
                () -> assertFalse(notFits)
        );
    }

    @Test
    @DisplayName("recommendDiskType() рекомендує CD для малих збірок")
    void testRecommendDiskTypeSmall() {
        // ACT
        DiskType recommended = manager.recommendDiskType(smallCompilation);

        // ASSERT
        assertEquals(DiskType.CD, recommended);
    }

    @Test
    @DisplayName("recommendDiskType() рекомендує DVD для середніх збірок")
    void testRecommendDiskTypeMedium() {
        // ARRANGE - збірка ~800 MB
        Compilation mediumComp = new Compilation("Medium", "Desc");
        for (int i = 0; i < 160; i++) { // 160 * 5 хв = 800 хв = 4000 MB
            Song song = new Song("Song" + i, "Artist", MusicStyle.ROCK, 300, 2020);
            mediumComp.addTrack(song);
        }

        // ACT
        DiskType recommended = manager.recommendDiskType(mediumComp);

        // ASSERT
        assertEquals(DiskType.DVD, recommended);
    }

    @Test
    @DisplayName("recommendDiskType() рекомендує Blu-ray для великих збірок")
    void testRecommendDiskTypeLarge() {
        // ARRANGE - збірка > 4700 MB
        Compilation hugeComp = new Compilation("Huge", "Desc");
        for (int i = 0; i < 1000; i++) { // 1000 * 5 хв
            Song song = new Song("Song" + i, "Artist", MusicStyle.ROCK, 300, 2020);
            hugeComp.addTrack(song);
        }

        // ACT
        DiskType recommended = manager.recommendDiskType(hugeComp);

        // ASSERT
        assertEquals(DiskType.BLURAY, recommended);
    }

    @Test
    @DisplayName("clear() очищує менеджер")
    void testClear() {
        // ARRANGE
        manager.burnCompilation(smallCompilation, DiskType.CD);

        // ACT
        manager.clear();

        // ASSERT
        assertTrue(manager.isEmpty());
    }
}