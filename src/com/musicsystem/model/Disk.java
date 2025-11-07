package com.musicsystem.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Disk {
    private static int idCounter = 1;

    private int id;
    private DiskType type;
    private int capacityMB;
    private int usedSpaceMB;
    private Compilation compilation;
    private LocalDateTime recordDate;

    public Disk(DiskType type, Compilation compilation) {
        this.id = idCounter++;
        this.type = type;
        this.capacityMB = type.getCapacityMB();
        this.compilation = compilation;
        this.usedSpaceMB = compilation.estimateSizeMB();
        this.recordDate = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        if (id >= idCounter) {
            idCounter = id + 1;
        }
    }

    public DiskType getType() {
        return type;
    }

    public void setType(DiskType type) {
        this.type = type;
        this.capacityMB = type.getCapacityMB();
    }

    public int getCapacityMB() {
        return capacityMB;
    }

    public int getUsedSpaceMB() {
        return usedSpaceMB;
    }

    public void setUsedSpaceMB(int usedSpaceMB) {
        this.usedSpaceMB = usedSpaceMB;
    }

    public Compilation getCompilation() {
        return compilation;
    }

    public void setCompilation(Compilation compilation) {
        this.compilation = compilation;
        this.usedSpaceMB = compilation.estimateSizeMB();
    }

    public LocalDateTime getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDateTime recordDate) {
        this.recordDate = recordDate;
    }

    public boolean checkCapacity() {
        return usedSpaceMB <= capacityMB;
    }

    public int getFreeSpaceMB() {
        return capacityMB - usedSpaceMB;
    }

    public double getUsagePercentage() {
        return (usedSpaceMB * 100.0) / capacityMB;
    }

    public String getInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return String.format("Диск #%d [%s]\nЗбірка: %s\nВикористано: %d / %d MB (%.1f%%)\nДата запису: %s",
                id, type, compilation.getName(), usedSpaceMB, capacityMB,
                getUsagePercentage(), recordDate.format(formatter));
    }

    @Override
    public String toString() {
        return String.format("#%d %s - %s (%d/%d MB)", id, type, compilation.getName(), usedSpaceMB, capacityMB);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Disk disk = (Disk) obj;
        return id == disk.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}