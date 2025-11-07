package com.musicsystem;

import com.musicsystem.model.*;
import com.musicsystem.service.*;

/**
 * Simple class for quick functional verification
 * without running JUnit tests
 */
public class TestRunner {

    public static void main(String[] args) {
        System.out.println("=== QUICK FUNCTIONAL CHECK ===\n");

        int passed = 0;
        int failed = 0;

        // Test 1: Song creation
        try {
            Song song = new Song("Test Song", "Artist", MusicStyle.ROCK, 180, 2020);
            assert song.getTitle().equals("Test Song");
            assert song.validate();
            System.out.println("✓ Test 1: Song creation - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 1: Song creation - FAILED: " + e.getMessage());
            failed++;
        }

        // Test 2: Song validation
        try {
            Song invalidSong = new Song("", "", MusicStyle.ROCK, -10, 2020);
            assert !invalidSong.validate();
            System.out.println("✓ Test 2: Song validation - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 2: Song validation - FAILED: " + e.getMessage());
            failed++;
        }

        // Test 3: MusicCollection
        try {
            MusicCollection collection = new MusicCollection();
            Song song1 = new Song("Song1", "Artist", MusicStyle.ROCK, 180, 2020);
            Song song2 = new Song("Song2", "Artist", MusicStyle.POP, 200, 2021);

            collection.add(song1);
            collection.add(song2);

            assert collection.getTotalCount() == 2;
            assert collection.getTotalDuration() == 380;
            System.out.println("✓ Test 3: MusicCollection - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 3: MusicCollection - FAILED: " + e.getMessage());
            failed++;
        }

        // Test 4: Compilation
        try {
            Compilation compilation = new Compilation("Test", "Desc");
            Song song = new Song("Song", "Artist", MusicStyle.ROCK, 300, 2020);

            compilation.addTrack(song);
            assert compilation.getTrackCount() == 1;
            assert compilation.calculateDuration() == 300;
            System.out.println("✓ Test 4: Compilation - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 4: Compilation - FAILED: " + e.getMessage());
            failed++;
        }

        // Test 5: Compilation sorting
        try {
            Compilation compilation = new Compilation("Test", "Desc");
            Song rock = new Song("Rock", "Artist", MusicStyle.ROCK, 180, 2020);
            Song pop = new Song("Pop", "Artist", MusicStyle.POP, 200, 2021);

            compilation.addTrack(rock);
            compilation.addTrack(pop);
            compilation.sortByStyle();

            assert compilation.getTracks().get(0).getStyle() == MusicStyle.POP;
            System.out.println("✓ Test 5: Compilation sorting - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 5: Compilation sorting - FAILED: " + e.getMessage());
            failed++;
        }

        // Test 6: Disk
        try {
            Compilation compilation = new Compilation("Test", "Desc");
            Song song = new Song("Song", "Artist", MusicStyle.ROCK, 300, 2020);
            compilation.addTrack(song);

            Disk disk = new Disk(DiskType.CD, compilation);
            assert disk.checkCapacity();
            assert disk.getType() == DiskType.CD;
            System.out.println("✓ Test 6: Disk - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 6: Disk - FAILED: " + e.getMessage());
            failed++;
        }

        // Test 7: CompilationManager
        try {
            CompilationManager manager = new CompilationManager();
            Compilation comp = manager.create("Test", "Desc");

            assert manager.getTotalCount() == 1;
            assert manager.getById(comp.getId()) != null;
            System.out.println("✓ Test 7: CompilationManager - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 7: CompilationManager - FAILED: " + e.getMessage());
            failed++;
        }

        // Test 8: DiskManager
        try {
            DiskManager manager = new DiskManager();
            Compilation compilation = new Compilation("Test", "Desc");
            Song song = new Song("Song", "Artist", MusicStyle.ROCK, 300, 2020);
            compilation.addTrack(song);

            Disk disk = manager.burnCompilation(compilation, DiskType.CD);
            assert manager.getTotalCount() == 1;
            assert disk != null;
            System.out.println("✓ Test 8: DiskManager - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 8: DiskManager - FAILED: " + e.getMessage());
            failed++;
        }

        // Test 9: Statistics
        try {
            MusicCollection collection = new MusicCollection();
            Song song1 = new Song("Song1", "Artist", MusicStyle.ROCK, 180, 2020);
            Song song2 = new Song("Song2", "Artist", MusicStyle.ROCK, 200, 2021);
            collection.add(song1);
            collection.add(song2);

            Statistics stats = new Statistics(collection);
            assert stats.getTotalCount() == 2;
            assert stats.getTotalDuration() == 380;
            System.out.println("✓ Test 9: Statistics - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 9: Statistics - FAILED: " + e.getMessage());
            failed++;
        }

        // Test 10: MusicStyle enum
        try {
            MusicStyle rock = MusicStyle.fromString("Rock");
            assert rock == MusicStyle.ROCK;

            MusicStyle unknown = MusicStyle.fromString("Unknown");
            assert unknown == MusicStyle.OTHER;
            System.out.println("✓ Test 10: MusicStyle enum - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 10: MusicStyle enum - FAILED: " + e.getMessage());
            failed++;
        }

        // Test 11: DiskType enum
        try {
            assert DiskType.CD.getCapacityMB() == 700;
            assert DiskType.DVD.getCapacityMB() == 4700;
            assert DiskType.BLURAY.getCapacityMB() == 25000;
            System.out.println("✓ Test 11: DiskType enum - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 11: DiskType enum - FAILED: " + e.getMessage());
            failed++;
        }

        // Test 12: Instrumental
        try {
            Instrumental inst = new Instrumental("Title", "Composer",
                    MusicStyle.CLASSICAL, 300, 1800);
            inst.addInstrument("Piano");
            inst.addInstrument("Violin");

            assert inst.getInstruments().size() == 2;
            assert inst.getType().equals("INSTRUMENTAL");
            System.out.println("✓ Test 12: Instrumental - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 12: Instrumental - FAILED: " + e.getMessage());
            failed++;
        }

        // Test 13: Remix
        try {
            Remix remix = new Remix("Title", "Artist", MusicStyle.ELECTRONIC,
                    240, 2010, "Original", "Remixer");

            assert remix.getOriginalArtist().equals("Original");
            assert remix.getRemixer().equals("Remixer");
            assert remix.getType().equals("REMIX");
            System.out.println("✓ Test 13: Remix - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 13: Remix - FAILED: " + e.getMessage());
            failed++;
        }

        // Test 14: LiveVersion
        try {
            LiveVersion live = new LiveVersion("Title", "Band", MusicStyle.ROCK,
                    400, 2015);
            live.setVenue("Stadium");

            assert live.getVenue().equals("Stadium");
            assert live.getType().equals("LIVE");
            System.out.println("✓ Test 14: LiveVersion - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 14: LiveVersion - FAILED: " + e.getMessage());
            failed++;
        }

        // Test 15: MusicCollection filtering
        try {
            MusicCollection collection = new MusicCollection();
            Song rock = new Song("Rock Song", "Artist", MusicStyle.ROCK, 180, 2020);
            Song pop = new Song("Pop Song", "Artist", MusicStyle.POP, 200, 2021);
            collection.add(rock);
            collection.add(pop);

            var rockSongs = collection.filterByStyle(MusicStyle.ROCK);
            assert rockSongs.size() == 1;
            assert rockSongs.get(0).getStyle() == MusicStyle.ROCK;
            System.out.println("✓ Test 15: MusicCollection filtering - PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("✗ Test 15: MusicCollection filtering - FAILED: " + e.getMessage());
            failed++;
        }

        // Summary
        System.out.println("\n=== RESULTS ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total: " + (passed + failed));
        System.out.printf("Success rate: %.2f%%\n", (passed * 100.0 / (passed + failed)));

        if (failed == 0) {
            System.out.println("\n✓✓✓ ALL TESTS PASSED! ✓✓✓");
        } else {
            System.out.println("\n✗ SOME TESTS FAILED");
        }
    }
}
