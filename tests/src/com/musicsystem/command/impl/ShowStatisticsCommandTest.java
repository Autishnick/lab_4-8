package com.musicsystem.command.impl;

import com.musicsystem.model.Song;
import com.musicsystem.model.MusicStyle;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

@DisplayName("Тести для ShowStatisticsCommand")
class ShowStatisticsCommandTest {

    private MusicCollection collection;
    private InputValidator validator;
    private ShowStatisticsCommand command;

    @BeforeEach
    void setUp() {
        collection = new MusicCollection();
        String input = "\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        validator = new InputValidator(new Scanner(in));
        command = new ShowStatisticsCommand(collection, validator);
    }

    @Test
    @DisplayName("execute() обробляє порожню колекцію")
    void testExecuteEmptyCollection() {
        assertDoesNotThrow(() -> command.execute());
    }

    @Test
    @DisplayName("execute() показує статистику для непустої колекції")
    void testExecuteShowsStatistics() {
        Song song1 = new Song("Song 1", "Artist", MusicStyle.ROCK, 180, 2020);
        Song song2 = new Song("Song 2", "Artist", MusicStyle.POP, 200, 2021);
        collection.add(song1);
        collection.add(song2);
        
        assertDoesNotThrow(() -> command.execute());
    }
}

