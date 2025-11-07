package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.model.Disk;
import com.musicsystem.model.MusicComposition;
import com.musicsystem.service.DiskManager;
import com.musicsystem.util.InputValidator;

import java.util.List;

public class ViewDisksCommand implements Command {
    private DiskManager diskManager;
    private InputValidator validator;

    public ViewDisksCommand(DiskManager diskManager, InputValidator validator) {
        this.diskManager = diskManager;
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== ЗАПИСАНІ ДИСКИ ===\n");

        if (diskManager.isEmpty()) {
            System.out.println("Немає записаних дисків.");
            return;
        }

        List<Disk> disks = diskManager.getAll();
        System.out.println("Загальна кількість дисків: " + disks.size());
        System.out.println("\n" + "=".repeat(100));

        for (int i = 0; i < disks.size(); i++) {
            Disk disk = disks.get(i);
            System.out.println("\n" + (i + 1) + ". " + disk);
            System.out.println("   Використання: " + createProgressBar(disk.getUsagePercentage()));
            System.out.println("   Вільно: " + disk.getFreeSpaceMB() + " MB");
        }

        System.out.println("\n" + "=".repeat(100));

        boolean showDetails = validator.readBoolean("\nПоказати детальну інформацію про диск?");

        if (showDetails && !disks.isEmpty()) {
            int choice = validator.readInt("Оберіть номер диску (0 для скасування): ",
                    0, disks.size());
            if (choice > 0) {
                Disk selectedDisk = disks.get(choice - 1);
                System.out.println("\n" + "=".repeat(100));
                System.out.println(selectedDisk.getInfo());
                System.out.println("\n--- Треки на диску ---");

                List<MusicComposition> tracks = selectedDisk.getCompilation().getTracks();
                for (int i = 0; i < tracks.size(); i++) {
                    System.out.println((i + 1) + ". " + tracks.get(i).getInfo());
                }
                System.out.println("=".repeat(100));
            }
        }

        validator.waitForEnter();
    }

    private String createProgressBar(double percentage) {
        int barLength = 40;
        int filled = (int) (barLength * percentage / 100);

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("] ");
        bar.append(String.format("%.1f%%", percentage));

        return bar.toString();
    }
}