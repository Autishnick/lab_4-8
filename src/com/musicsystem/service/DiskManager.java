package com.musicsystem.service;

import com.musicsystem.model.Compilation;
import com.musicsystem.model.Disk;
import com.musicsystem.model.DiskType;
import com.musicsystem.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class DiskManager {
    private static final String CLASS_NAME = "DiskManager";
    private static final Logger logger = Logger.getInstance();

    private List<Disk> disks;

    public DiskManager() {
        this.disks = new ArrayList<>();
    }

    public Disk burnCompilation(Compilation compilation, DiskType diskType) {
        logger.info(CLASS_NAME, "Початок запису збірки '" + (compilation != null ? compilation.getName() : "null") + 
                    "' на диск типу: " + diskType);
        
        if (compilation == null) {
            logger.error(CLASS_NAME, "Спроба записати null збірку на диск");
            throw new IllegalArgumentException("Збірка не може бути null");
        }

        if (compilation.getTrackCount() == 0) {
            logger.error(CLASS_NAME, "Спроба записати порожню збірку на диск");
            throw new IllegalArgumentException("Збірка порожня, неможливо записати на диск");
        }

        Disk disk = new Disk(diskType, compilation);
        logger.debug(CLASS_NAME, "Створено диск. Потрібно: " + disk.getUsedSpaceMB() + " MB, Доступно: " + disk.getCapacityMB() + " MB");

        if (!disk.checkCapacity()) {
            String errorMsg = String.format("Збірка не вміщується на диск %s. Потрібно: %d MB, Доступно: %d MB",
                            diskType, disk.getUsedSpaceMB(), disk.getCapacityMB());
            logger.error(CLASS_NAME, errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        disks.add(disk);
        logger.info(CLASS_NAME, "✓ Збірку успішно записано на диск (ID: " + disk.getId() + ", Тип: " + diskType + ")");
        return disk;
    }

    public boolean deleteDisk(int id) {
        logger.info(CLASS_NAME, "Видалення диска з ID: " + id);
        boolean deleted = disks.removeIf(d -> d.getId() == id);
        
        if (deleted) {
            logger.info(CLASS_NAME, "✓ Диск успішно видалено (ID: " + id + ")");
        } else {
            logger.warn(CLASS_NAME, "Диск з ID " + id + " не знайдено");
        }
        
        return deleted;
    }

    public Disk getById(int id) {
        return disks.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Disk> getAll() {
        return new ArrayList<>(disks);
    }

    public List<Disk> getDisksByType(DiskType type) {
        return disks.stream()
                .filter(d -> d.getType() == type)
                .toList();
    }

    public int getTotalCount() {
        return disks.size();
    }

    public void clear() {
        int count = disks.size();
        disks.clear();
        logger.info(CLASS_NAME, "Очищено список дисків (видалено " + count + " дисків)");
    }

    public boolean isEmpty() {
        return disks.isEmpty();
    }

    public boolean canFitOnDisk(Compilation compilation, DiskType diskType) {
        int requiredSpace = compilation.estimateSizeMB();
        int availableSpace = diskType.getCapacityMB();
        return requiredSpace <= availableSpace;
    }

    public DiskType recommendDiskType(Compilation compilation) {
        int requiredSpace = compilation.estimateSizeMB();
        logger.debug(CLASS_NAME, "Визначення рекомендованого типу диска для збірки '" + compilation.getName() + 
                     "' (потрібно: " + requiredSpace + " MB)");

        DiskType recommended;
        if (requiredSpace <= DiskType.CD.getCapacityMB()) {
            recommended = DiskType.CD;
        } else if (requiredSpace <= DiskType.DVD.getCapacityMB()) {
            recommended = DiskType.DVD;
        } else {
            recommended = DiskType.BLURAY;
        }
        
        logger.info(CLASS_NAME, "Рекомендовано тип диска: " + recommended);
        return recommended;
    }
}