package com.musicsystem.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тести для enum DiskType")
class DiskTypeTest {

    @Test
    @DisplayName("CD має місткість 700 MB")
    void testCdCapacity() {
        assertEquals(700, DiskType.CD.getCapacityMB());
    }

    @Test
    @DisplayName("DVD має місткість 4700 MB")
    void testDvdCapacity() {
        assertEquals(4700, DiskType.DVD.getCapacityMB());
    }

    @Test
    @DisplayName("Blu-ray має місткість 25000 MB")
    void testBlurayCapacity() {
        assertEquals(25000, DiskType.BLURAY.getCapacityMB());
    }

    @Test
    @DisplayName("fromString() знаходить тип за назвою")
    void testFromString() {
        assertAll(
                () -> assertEquals(DiskType.CD, DiskType.fromString("CD")),
                () -> assertEquals(DiskType.DVD, DiskType.fromString("DVD")),
                () -> assertEquals(DiskType.BLURAY, DiskType.fromString("BLURAY"))
        );
    }

    @Test
    @DisplayName("fromString() не чутливий до регістру")
    void testFromStringCaseInsensitive() {
        assertAll(
                () -> assertEquals(DiskType.CD, DiskType.fromString("cd")),
                () -> assertEquals(DiskType.DVD, DiskType.fromString("dvd")),
                () -> assertEquals(DiskType.BLURAY, DiskType.fromString("bluray"))
        );
    }

    @Test
    @DisplayName("fromString() повертає CD для невідомого типу")
    void testFromStringUnknown() {
        assertEquals(DiskType.CD, DiskType.fromString("Unknown"));
    }

    @Test
    @DisplayName("toString() повертає назву з місткістю")
    void testToString() {
        assertAll(
                () -> assertEquals("CD (700 MB)", DiskType.CD.toString()),
                () -> assertEquals("DVD (4700 MB)", DiskType.DVD.toString()),
                () -> assertEquals("BLURAY (25000 MB)", DiskType.BLURAY.toString())
        );
    }

    @Test
    @DisplayName("Enum містить 3 типи")
    void testEnumSize() {
        assertEquals(3, DiskType.values().length);
    }
}