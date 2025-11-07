package com.musicsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тести для класу Disk")
class DiskTest {

    private Compilation compilation;
    private Disk disk;

    @BeforeEach
    void setUp() {
        compilation = new Compilation("Test Compilation", "Description");
        Song song = new Song("Song", "Artist", MusicStyle.ROCK, 300, 2020); // 5 хв = 25MB
        compilation.addTrack(song);

        disk = new Disk(DiskType.CD, compilation);
    }

    @Test
    @DisplayName("Конструктор створює диск з правильними параметрами")
    void testConstructor() {
        // ASSERT
        assertAll(
                () -> assertEquals(DiskType.CD, disk.getType()),
                () -> assertEquals(700, disk.getCapacityMB()),
                () -> assertEquals(compilation, disk.getCompilation()),
                () -> assertEquals(25, disk.getUsedSpaceMB()), // 5 хв * 5 MB/хв
                () -> assertNotNull(disk.getRecordDate())
        );
    }

    @Test
    @DisplayName("checkCapacity() повертає true якщо вміщується")
    void testCheckCapacityFits() {
        // ACT
        boolean fits = disk.checkCapacity();

        // ASSERT
        assertTrue(fits, "25 MB має вміститись на CD 700 MB");
    }

    @Test
    @DisplayName("checkCapacity() повертає false якщо не вміщується")
    void testCheckCapacityDoesNotFit() {
        // ARRANGE - створюємо велику збірку
        Compilation largeComp = new Compilation("Large", "Desc");
        for (int i = 0; i < 200; i++) { // 200 * 5 хв = 1000 хв = 5000 MB
            Song song = new Song("Song" + i, "Artist", MusicStyle.ROCK, 300, 2020);
            largeComp.addTrack(song);
        }
        Disk largeDisk = new Disk(DiskType.CD, largeComp);

        // ACT
        boolean fits = largeDisk.checkCapacity();

        // ASSERT
        assertFalse(fits, "5000 MB не має вміститись на CD 700 MB");
    }

    @Test
    @DisplayName("getFreeSpaceMB() рахує вільне місце")
    void testGetFreeSpaceMB() {
        // ACT
        int freeSpace = disk.getFreeSpaceMB();

        // ASSERT
        assertEquals(675, freeSpace); // 700 - 25
    }

    @Test
    @DisplayName("getUsagePercentage() рахує відсоток використання")
    void testGetUsagePercentage() {
        // ACT
        double percentage = disk.getUsagePercentage();

        // ASSERT
        assertEquals(3.57, percentage, 0.01); // 25/700 * 100 ≈ 3.57%
    }

    @Test
    @DisplayName("setType() змінює тип диску та місткість")
    void testSetType() {
        // ACT
        disk.setType(DiskType.DVD);

        // ASSERT
        assertAll(
                () -> assertEquals(DiskType.DVD, disk.getType()),
                () -> assertEquals(4700, disk.getCapacityMB())
        );
    }

    @Test
    @DisplayName("setCompilation() змінює збірку та перераховує розмір")
    void testSetCompilation() {
        // ARRANGE
        Compilation newComp = new Compilation("New", "Desc");
        Song song = new Song("S", "A", MusicStyle.POP, 600, 2020); // 10 хв = 50MB
        newComp.addTrack(song);

        // ACT
        disk.setCompilation(newComp);

        // ASSERT
        assertAll(
                () -> assertEquals(newComp, disk.getCompilation()),
                () -> assertEquals(50, disk.getUsedSpaceMB())
        );
    }

    @Test
    @DisplayName("getInfo() повертає інформацію про диск")
    void testGetInfo() {
        // ACT
        String info = disk.getInfo();

        // ASSERT
        assertAll(
                () -> assertNotNull(info),
                () -> assertTrue(info.contains("CD")),
                () -> assertTrue(info.contains("Test Compilation")),
                () -> assertTrue(info.contains("25")),
                () -> assertTrue(info.contains("700"))
        );
    }

    @Test
    @DisplayName("toString() повертає короткий опис")
    void testToString() {
        // ACT
        String str = disk.toString();

        // ASSERT
        assertAll(
                () -> assertNotNull(str),
                () -> assertTrue(str.contains("CD")),
                () -> assertTrue(str.contains("Test Compilation"))
        );
    }

    @Test
    @DisplayName("equals() порівнює диски по ID")
    void testEquals() {
        // ARRANGE
        Disk disk1 = new Disk(DiskType.CD, compilation);
        Disk disk2 = new Disk(DiskType.DVD, compilation);
        disk1.setId(1);
        disk2.setId(1);

        Disk disk3 = new Disk(DiskType.CD, compilation);
        disk3.setId(2);

        // ASSERT
        assertEquals(disk1, disk2);
        assertNotEquals(disk1, disk3);
    }

    @Test
    @DisplayName("hashCode() генерує однаковий хеш для однакових ID")
    void testHashCode() {
        // ARRANGE
        Disk disk1 = new Disk(DiskType.CD, compilation);
        Disk disk2 = new Disk(DiskType.DVD, compilation);
        disk1.setId(5);
        disk2.setId(5);

        // ASSERT
        assertEquals(disk1.hashCode(), disk2.hashCode());
    }
}