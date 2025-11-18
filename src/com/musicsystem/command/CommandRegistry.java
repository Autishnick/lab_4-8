package com.musicsystem.command;

import com.musicsystem.command.impl.*;
import com.musicsystem.service.CompilationManager;
import com.musicsystem.service.DiskManager;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.FileManager;
import com.musicsystem.util.InputValidator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandRegistry {
    private final Map<Integer, MenuOption> menuOptions;

    public static class MenuOption {
        private final int number;
        private final String description;
        private final String category;
        private final Function<CommandContext, Command> commandFactory;

        public MenuOption(int number, String description, String category, 
                         Function<CommandContext, Command> commandFactory) {
            this.number = number;
            this.description = description;
            this.category = category;
            this.commandFactory = commandFactory;
        }

        public int getNumber() {
            return number;
        }

        public String getDescription() {
            return description;
        }

        public String getCategory() {
            return category;
        }

        public Function<CommandContext, Command> getCommandFactory() {
            return commandFactory;
        }
    }

    public static class CommandContext {
        private final MusicCollection collection;
        private final CompilationManager compilationManager;
        private final DiskManager diskManager;
        private final FileManager fileManager;
        private final InputValidator validator;

        public CommandContext(MusicCollection collection,
                             CompilationManager compilationManager,
                             DiskManager diskManager,
                             FileManager fileManager,
                             InputValidator validator) {
            this.collection = collection;
            this.compilationManager = compilationManager;
            this.diskManager = diskManager;
            this.fileManager = fileManager;
            this.validator = validator;
        }

        public MusicCollection getCollection() {
            return collection;
        }

        public CompilationManager getCompilationManager() {
            return compilationManager;
        }

        public DiskManager getDiskManager() {
            return diskManager;
        }

        public FileManager getFileManager() {
            return fileManager;
        }

        public InputValidator getValidator() {
            return validator;
        }
    }

    public CommandRegistry() {
        this.menuOptions = new LinkedHashMap<>();
        initializeCommands();
    }

    private void initializeCommands() {
        // Управління колекцією
        menuOptions.put(1, new MenuOption(1, "Додати композицію", "УПРАВЛІННЯ КОЛЕКЦІЄЮ",
                ctx -> new AddCompositionCommand(ctx.getCollection(), ctx.getValidator())));
        menuOptions.put(2, new MenuOption(2, "Видалити композицію", "УПРАВЛІННЯ КОЛЕКЦІЄЮ",
                ctx -> new DeleteCompositionCommand(ctx.getCollection(), ctx.getValidator())));
        menuOptions.put(3, new MenuOption(3, "Редагувати композицію", "УПРАВЛІННЯ КОЛЕКЦІЄЮ",
                ctx -> new EditCompositionCommand(ctx.getCollection(), ctx.getValidator())));
        menuOptions.put(4, new MenuOption(4, "Переглянути колекцію", "УПРАВЛІННЯ КОЛЕКЦІЄЮ",
                ctx -> new ViewCollectionCommand(ctx.getCollection(), ctx.getValidator())));
        menuOptions.put(5, new MenuOption(5, "Знайти композицію за тривалістю", "УПРАВЛІННЯ КОЛЕКЦІЄЮ",
                ctx -> new FindByDurationCommand(ctx.getCollection(), ctx.getValidator())));
        menuOptions.put(6, new MenuOption(6, "Фільтрувати за стилем", "УПРАВЛІННЯ КОЛЕКЦІЄЮ",
                ctx -> new FilterByStyleCommand(ctx.getCollection(), ctx.getValidator())));
        menuOptions.put(7, new MenuOption(7, "Фільтрувати за виконавцем", "УПРАВЛІННЯ КОЛЕКЦІЄЮ",
                ctx -> new FilterByArtistCommand(ctx.getCollection(), ctx.getValidator())));

        // Робота зі збірками
        menuOptions.put(8, new MenuOption(8, "Створити збірку", "РОБОТА ЗІ ЗБІРКАМИ",
                ctx -> new CreateCompilationCommand(ctx.getCollection(), ctx.getCompilationManager(), ctx.getValidator())));
        menuOptions.put(9, new MenuOption(9, "Редагувати збірку", "РОБОТА ЗІ ЗБІРКАМИ",
                ctx -> new EditCompilationCommand(ctx.getCollection(), ctx.getCompilationManager(), ctx.getValidator())));
        menuOptions.put(10, new MenuOption(10, "Видалити збірку", "РОБОТА ЗІ ЗБІРКАМИ",
                ctx -> new DeleteCompilationCommand(ctx.getCompilationManager(), ctx.getValidator())));
        menuOptions.put(11, new MenuOption(11, "Сортувати збірку за стилем", "РОБОТА ЗІ ЗБІРКАМИ",
                ctx -> new SortByStyleCommand(ctx.getCompilationManager(), ctx.getValidator())));

        // Запис на диск
        menuOptions.put(12, new MenuOption(12, "Записати збірку на диск", "ЗАПИС НА ДИСК",
                ctx -> new BurnToDiskCommand(ctx.getCompilationManager(), ctx.getDiskManager(), ctx.getValidator())));
        menuOptions.put(13, new MenuOption(13, "Переглянути записані диски", "ЗАПИС НА ДИСК",
                ctx -> new ViewDisksCommand(ctx.getDiskManager(), ctx.getValidator())));

        // Аналіз та файли
        menuOptions.put(14, new MenuOption(14, "Показати статистику колекції", "АНАЛІЗ ТА ФАЙЛИ",
                ctx -> new ShowStatisticsCommand(ctx.getCollection(), ctx.getValidator())));
        menuOptions.put(15, new MenuOption(15, "Завантажити з файлу", "АНАЛІЗ ТА ФАЙЛИ",
                ctx -> new LoadFromFileCommand(ctx.getCollection(), ctx.getFileManager(), ctx.getValidator())));
        menuOptions.put(16, new MenuOption(16, "Зберегти у файл", "АНАЛІЗ ТА ФАЙЛИ",
                ctx -> new SaveToFileCommand(ctx.getCollection(), ctx.getFileManager(), ctx.getValidator())));

        // Тестування
        menuOptions.put(17, new MenuOption(17, "📧 Тест email-розсилки", "ТЕСТУВАННЯ",
                ctx -> new TestEmailCommand(ctx.getValidator())));
    }

    public Command getCommand(int choice, CommandContext context) {
        MenuOption option = menuOptions.get(choice);
        if (option != null) {
            return option.getCommandFactory().apply(context);
        }
        return null;
    }

    public boolean hasCommand(int choice) {
        return menuOptions.containsKey(choice);
    }

    public Map<Integer, MenuOption> getAllOptions() {
        return new LinkedHashMap<>(menuOptions);
    }

    public int getMaxOptionNumber() {
        return menuOptions.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
    }
}

