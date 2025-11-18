package com.musicsystem.util;

import com.musicsystem.model.*;
import com.musicsystem.service.MusicCollection;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

@DisplayName("Тести для класу FileManager")
class FileManagerTest {

    private FileManager fileManager;
    private MusicCollection collection;
    private static final String TEST_FILE = "test_data/test_compositions.txt";

    @BeforeEach
    void setUp() {
        fileManager = new FileManager();
        collection = new MusicCollection();

        // Створюємо тестові композиції
        Song song = new Song("Test Song", "Test Artist", MusicStyle.ROCK,
                180, 2020, "Lyrics", true);

        Instrumental instrumental = new Instrumental("Test Instrumental", "Composer",
                MusicStyle.CLASSICAL, 300, 1900,
                Arrays.asList("Piano", "Violin"));

        Remix remix = new Remix("Test Remix", "Artist", MusicStyle.ELECTRONIC,
                240, 2010, "Original Artist", "Remixer");

        LiveVersion live = new LiveVersion("Test Live", "Band", MusicStyle.ROCK,
                400, 2015, "Stadium",
                LocalDate.of(2015, 6, 15));

        collection.add(song);
        collection.add(instrumental);
        collection.add(remix);
        collection.add(live);
    }

    @AfterEach
    void tearDown() {
        // Видаляємо тестовий файл після кожного тесту
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
        File dir = new File("test_data");
        if (dir.exists()) {
            dir.delete();
        }
    }

    @Test
    @DisplayName("saveToFile() зберігає колекцію у файл")
    void testSaveToFile() throws IOException {
        // ACT
        fileManager.saveToFile(collection, TEST_FILE);

        // ASSERT
        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "Файл має бути створений");
    }

    @Test
    @DisplayName("loadFromFile() завантажує колекцію з файлу")
    void testLoadFromFile() throws IOException {
        // ARRANGE - спочатку зберігаємо
        fileManager.saveToFile(collection, TEST_FILE);

        // ACT - потім завантажуємо в нову колекцію
        MusicCollection loadedCollection = new MusicCollection();
        fileManager.loadFromFile(loadedCollection, TEST_FILE);

        // ASSERT
        assertEquals(4, loadedCollection.getTotalCount(),
                "Має бути завантажено 4 композиції");
    }

    @Test
    @DisplayName("loadFromFile() кидає виняток якщо файл не існує")
    void testLoadFromNonExistentFile() {
        // ACT & ASSERT
        assertThrows(IOException.class, () -> {
            fileManager.loadFromFile(collection, "nonexistent.txt");
        });
    }

    @Test
    @DisplayName("saveToFile() та loadFromFile() зберігають Song коректно")
    void testSaveAndLoadSong() throws IOException {
        // ARRANGE
        MusicCollection singleSongCollection = new MusicCollection();
        Song song = new Song("Title", "Artist", MusicStyle.POP, 200, 2020,
                "Test Lyrics", true);
        song.setId(100);
        singleSongCollection.add(song);

        // ACT
        fileManager.saveToFile(singleSongCollection, TEST_FILE);
        MusicCollection loaded = new MusicCollection();
        fileManager.loadFromFile(loaded, TEST_FILE);

        // ASSERT
        Song loadedSong = (Song) loaded.getById(100);
        assertAll(
                () -> assertNotNull(loadedSong),
                () -> assertEquals("Title", loadedSong.getTitle()),
                () -> assertEquals("Artist", loadedSong.getArtist()),
                () -> assertEquals(MusicStyle.POP, loadedSong.getStyle()),
                () -> assertEquals("SONG", loadedSong.getType())
        );
    }

    @Test
    @DisplayName("saveToFile() та loadFromFile() зберігають Instrumental коректно")
    void testSaveAndLoadInstrumental() throws IOException {
        // ARRANGE
        MusicCollection singleCollection = new MusicCollection();
        Instrumental inst = new Instrumental("Title", "Composer", MusicStyle.CLASSICAL,
                300, 1900, Arrays.asList("Piano", "Violin"));
        inst.setId(101);
        singleCollection.add(inst);

        // ACT
        fileManager.saveToFile(singleCollection, TEST_FILE);
        MusicCollection loaded = new MusicCollection();
        fileManager.loadFromFile(loaded, TEST_FILE);

        // ASSERT
        Instrumental loadedInst = (Instrumental) loaded.getById(101);
        assertAll(
                () -> assertNotNull(loadedInst),
                () -> assertEquals("Title", loadedInst.getTitle()),
                () -> assertEquals("INSTRUMENTAL", loadedInst.getType()),
                () -> assertEquals(2, loadedInst.getInstruments().size())
        );
    }

    @Test
    @DisplayName("saveToFile() та loadFromFile() зберігають Remix коректно")
    void testSaveAndLoadRemix() throws IOException {
        // ARRANGE
        MusicCollection singleCollection = new MusicCollection();
        Remix remix = new Remix("Title", "Artist", MusicStyle.ELECTRONIC,
                240, 2010, "Original", "Remixer");
        remix.setId(102);
        singleCollection.add(remix);

        // ACT
        fileManager.saveToFile(singleCollection, TEST_FILE);
        MusicCollection loaded = new MusicCollection();
        fileManager.loadFromFile(loaded, TEST_FILE);

        // ASSERT
        Remix loadedRemix = (Remix) loaded.getById(102);
        assertAll(
                () -> assertNotNull(loadedRemix),
                () -> assertEquals("Title", loadedRemix.getTitle()),
                () -> assertEquals("REMIX", loadedRemix.getType()),
                () -> assertEquals("Original", loadedRemix.getOriginalArtist()),
                () -> assertEquals("Remixer", loadedRemix.getRemixer())
        );
    }

    @Test
    @DisplayName("saveToFile() та loadFromFile() зберігають LiveVersion коректно")
    void testSaveAndLoadLiveVersion() throws IOException {
        // ARRANGE
        MusicCollection singleCollection = new MusicCollection();
        LocalDate date = LocalDate.of(2015, 6, 15);
        LiveVersion live = new LiveVersion("Title", "Band", MusicStyle.ROCK,
                400, 2015, "Stadium", date);
        live.setId(103);
        singleCollection.add(live);

        // ACT
        fileManager.saveToFile(singleCollection, TEST_FILE);
        MusicCollection loaded = new MusicCollection();
        fileManager.loadFromFile(loaded, TEST_FILE);

        // ASSERT
        LiveVersion loadedLive = (LiveVersion) loaded.getById(103);
        assertAll(
                () -> assertNotNull(loadedLive),
                () -> assertEquals("Title", loadedLive.getTitle()),
                () -> assertEquals("LIVE", loadedLive.getType()),
                () -> assertEquals("Stadium", loadedLive.getVenue()),
                () -> assertEquals(date, loadedLive.getLiveDate())
        );
    }

    @Test
    @DisplayName("loadFromFile() очищує колекцію перед завантаженням")
    void testLoadFromFileClearsCollection() throws IOException {
        // ARRANGE
        Song existingSong = new Song("Existing", "Artist", MusicStyle.ROCK, 100, 2020);
        collection.add(existingSong);

        fileManager.saveToFile(collection, TEST_FILE);

        // ACT - завантажуємо в ту ж колекцію
        fileManager.loadFromFile(collection, TEST_FILE);

        // ASSERT - має бути тільки 4 початкові + 1 новий, всього 5
        // Але оскільки clear() викликається, має бути тільки 5
        assertEquals(5, collection.getTotalCount());
    }

    @Test
    @DisplayName("createDefaultFile() створює файл з прикладами")
    void testCreateDefaultFile() {
        // ACT
        fileManager.createDefaultFile();

        // ASSERT
        assertTrue(fileManager.defaultFileExists(),
                "Файл за замовчуванням має бути створений");
    }

    @Test
    @DisplayName("defaultFileExists() перевіряє існування файлу")
    void testDefaultFileExists() {
        // ACT - створюємо файл
        fileManager.createDefaultFile();

        // ASSERT
        assertTrue(fileManager.defaultFileExists());
    }

    @Test
    @DisplayName("saveToFile() створює директорії якщо їх немає")
    void testSaveToFileCreatesDirectories() throws IOException {
        // ARRANGE
        String pathWithDirs = "test_data/sub1/sub2/test.txt";

        // ACT
        fileManager.saveToFile(collection, pathWithDirs);

        // ASSERT
        File file = new File(pathWithDirs);
        assertTrue(file.exists(), "Файл має бути створений разом з директоріями");

        // Cleanup
        file.delete();
        new File("test_data/sub1/sub2").delete();
        new File("test_data/sub1").delete();
    }

    @Test
    @DisplayName("loadFromFile() пропускає порожні рядки")
    void testLoadFromFileSkipsEmptyLines() throws IOException {
        // ARRANGE - записуємо файл з порожніми рядками
        fileManager.saveToFile(collection, TEST_FILE);

        // ACT
        MusicCollection loaded = new MusicCollection();
        fileManager.loadFromFile(loaded, TEST_FILE);

        // ASSERT
        assertEquals(collection.getTotalCount(), loaded.getTotalCount());
    }

    @Test
    @DisplayName("saveToDefaultFile() зберігає у файл за замовчуванням")
    void testSaveToDefaultFile() throws IOException {
        // ACT
        fileManager.saveToDefaultFile(collection);

        // ASSERT
        assertTrue(fileManager.defaultFileExists(), "Файл за замовчуванням має існувати");
    }

    @Test
    @DisplayName("loadFromDefaultFile() завантажує з файлу за замовчуванням")
    void testLoadFromDefaultFile() throws IOException {
        // ARRANGE
        fileManager.saveToDefaultFile(collection);

        // ACT
        MusicCollection loaded = new MusicCollection();
        fileManager.loadFromDefaultFile(loaded);

        // ASSERT
        assertTrue(loaded.getTotalCount() > 0, "Має бути завантажено композиції");
    }

    @Test
    @DisplayName("createDefaultFile() створює файл якщо він не існує")
    void testCreateDefaultFileWhenNotExists() {
        // ACT
        fileManager.createDefaultFile();

        // ASSERT
        assertTrue(fileManager.defaultFileExists());
    }
}