package com.musicsystem.command.impl;

import java.util.List;

import com.musicsystem.command.Command;
import com.musicsystem.model.Compilation;
import com.musicsystem.model.Disk;
import com.musicsystem.model.DiskType;
import com.musicsystem.service.CompilationManager;
import com.musicsystem.service.DiskManager;
import com.musicsystem.util.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BurnToDiskCommand implements Command {
    private static final Logger logger = LogManager.getLogger(BurnToDiskCommand.class);

    private CompilationManager compilationManager;
    private DiskManager diskManager;
    private InputValidator validator;

    public BurnToDiskCommand(CompilationManager compilationManager,
            DiskManager diskManager,
            InputValidator validator) {
        this.compilationManager = compilationManager;
        this.diskManager = diskManager;
        this.validator = validator;
    }

    @Override
    public void execute() {
        logger.debug("Виконання команди запису збірки на диск");
        System.out.println("\n=== ЗАПИС ЗБІРКИ НА ДИСК ===\n");

        if (compilationManager.isEmpty()) {
            logger.debug("Спроба записати на диск при відсутності збірок");
            System.out.println("Немає створених збірок.");
            return;
        }

        List<Compilation> compilations = compilationManager.getAll();
        System.out.println("Доступні збірки:\n");
        for (int i = 0; i < compilations.size(); i++) {
            Compilation comp = compilations.get(i);
            System.out.println((i + 1) + ". " + comp.toString() +
                    " (~" + comp.estimateSizeMB() + " MB)");
        }

        int compChoice = validator.readInt("\nОберіть збірку для запису (0 для скасування): ",
                0, compilations.size());

        if (compChoice == 0) {
            logger.debug("Користувач скасував запис на диск");
            System.out.println("Запис скасовано.");
            return;
        }

        Compilation compilation = compilations.get(compChoice - 1);
        logger.debug("Обрано збірку для запису: " + compilation.getName());

        System.out.println("\nОберіть тип диску:");
        DiskType[] diskTypes = DiskType.values();
        for (int i = 0; i < diskTypes.length; i++) {
            DiskType type = diskTypes[i];
            boolean fits = diskManager.canFitOnDisk(compilation, type);
            String status = fits ? "✓ Вміщується" : "✗ Не вміщується";
            System.out.println((i + 1) + ". " + type + " - " + status);
        }

        DiskType recommended = diskManager.recommendDiskType(compilation);
        System.out.println("\nРекомендований тип: " + recommended);

        int diskChoice = validator.readInt("\nВаш вибір: ", 1, diskTypes.length);
        DiskType selectedType = diskTypes[diskChoice - 1];

        System.out.println("\n--- Інформація про запис ---");
        System.out.println("Збірка: " + compilation.getName());
        System.out.println("Кількість треків: " + compilation.getTrackCount());
        System.out.println("Тривалість: " + compilation.getFormattedDuration());
        System.out.println("Розмір: ~" + compilation.estimateSizeMB() + " MB");
        System.out.println("Тип диску: " + selectedType);
        System.out.println("Місткість диску: " + selectedType.getCapacityMB() + " MB");

        boolean confirm = validator.readBoolean("\nПродовжити запис?");

        if (!confirm) {
            logger.debug("Користувач скасував запис після підтвердження");
            System.out.println("\nЗапис скасовано.");
            return;
        }

        try {
            logger.debug("Початок запису збірки '" + compilation.getName() + "' на диск " + selectedType);
            Disk disk = diskManager.burnCompilation(compilation, selectedType);

            System.out.println("\n✓ Збірку успішно записано на диск!");
            System.out.println("\n" + disk.getInfo());
            logger.debug("Збірку успішно записано на диск ID: " + disk.getId());

        } catch (IllegalArgumentException e) {
            logger.error("Помилка запису: збірка не вміщується на диск", e);
            System.out.println("\n✗ Помилка запису: " + e.getMessage());
            System.out.println("\nСпробуйте:");
            System.out.println("- Обрати диск більшої місткості");
            System.out.println("- Видалити деякі треки зі збірки");
        } catch (Exception e) {
            logger.error("Невідома помилка при записі на диск", e);
            System.out.println("\n✗ Невідома помилка: " + e.getMessage());
        }

        validator.waitForEnter();
    }
}