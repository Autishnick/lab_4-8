package com.musicsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@DisplayName("Тести для класу Instrumental")
class InstrumentalTest {

    private Instrumental instrumental;

    @BeforeEach
    void setUp() {
        instrumental = new Instrumental("Moonlight Sonata", "Beethoven",
                MusicStyle.CLASSICAL, 900, 1801);
    }

    @Test
    @DisplayName("Конструктор створює інструментал з порожнім списком інструментів")
    void testConstructorWithoutInstruments() {
        // ASSERT
        assertAll(
                () -> assertEquals("Moonlight Sonata", instrumental.getTitle()),
                () -> assertEquals("Beethoven", instrumental.getArtist()),
                () -> assertEquals("INSTRUMENTAL", instrumental.getType()),
                () -> assertTrue(instrumental.getInstruments().isEmpty())
        );
    }

    @Test
    @DisplayName("Конструктор зі списком інструментів працює коректно")
    void testConstructorWithInstruments() {
        // ARRANGE
        List<String> instruments = Arrays.asList("Piano", "Violin");

        // ACT
        Instrumental inst = new Instrumental("Test", "Artist", MusicStyle.CLASSICAL,
                300, 2000, instruments);

        // ASSERT
        assertAll(
                () -> assertEquals(2, inst.getInstruments().size()),
                () -> assertTrue(inst.getInstruments().contains("Piano")),
                () -> assertTrue(inst.getInstruments().contains("Violin"))
        );
    }

    @Test
    @DisplayName("addInstrument() додає інструмент")
    void testAddInstrument() {
        // ACT
        instrumental.addInstrument("Piano");

        // ASSERT
        assertAll(
                () -> assertEquals(1, instrumental.getInstruments().size()),
                () -> assertTrue(instrumental.getInstruments().contains("Piano"))
        );
    }

    @Test
    @DisplayName("addInstrument() не додає порожній рядок")
    void testAddEmptyInstrument() {
        // ACT
        instrumental.addInstrument("");

        // ASSERT
        assertTrue(instrumental.getInstruments().isEmpty());
    }

    @Test
    @DisplayName("addInstrument() не додає null")
    void testAddNullInstrument() {
        // ACT
        instrumental.addInstrument(null);

        // ASSERT
        assertTrue(instrumental.getInstruments().isEmpty());
    }

    @Test
    @DisplayName("setInstruments() замінює список інструментів")
    void testSetInstruments() {
        // ARRANGE
        List<String> newInstruments = Arrays.asList("Guitar", "Drums");

        // ACT
        instrumental.setInstruments(newInstruments);

        // ASSERT
        assertAll(
                () -> assertEquals(2, instrumental.getInstruments().size()),
                () -> assertTrue(instrumental.getInstruments().contains("Guitar")),
                () -> assertTrue(instrumental.getInstruments().contains("Drums"))
        );
    }

    @Test
    @DisplayName("setInstruments(null) створює порожній список")
    void testSetInstrumentsNull() {
        // ACT
        instrumental.setInstruments(null);

        // ASSERT
        assertNotNull(instrumental.getInstruments());
        assertTrue(instrumental.getInstruments().isEmpty());
    }

    @Test
    @DisplayName("getInstruments() повертає копію списку")
    void testGetInstrumentsReturnsCopy() {
        // ARRANGE
        instrumental.addInstrument("Piano");

        // ACT
        List<String> instruments = instrumental.getInstruments();
        instruments.clear(); // змінюємо копію

        // ASSERT
        assertEquals(1, instrumental.getInstruments().size(),
                "Зміна копії не має впливати на оригінал");
    }

    @Test
    @DisplayName("toFileString() генерує правильний формат")
    void testToFileString() {
        // ARRANGE
        instrumental.addInstrument("Piano");
        instrumental.addInstrument("Violin");

        // ACT
        String fileString = instrumental.toFileString();

        // ASSERT
        assertAll(
                () -> assertTrue(fileString.contains("INSTRUMENTAL")),
                () -> assertTrue(fileString.contains("Piano;Violin"))
        );
    }

    @Test
    @DisplayName("validate() працює коректно")
    void testValidate() {
        // ACT
        boolean isValid = instrumental.validate();

        // ASSERT
        assertTrue(isValid);
    }
}