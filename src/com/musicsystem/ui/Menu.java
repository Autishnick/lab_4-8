package com.musicsystem.ui;

import com.musicsystem.command.Command;
import com.musicsystem.command.MenuInvoker;
import com.musicsystem.command.impl.*;
import com.musicsystem.service.CompilationManager;
import com.musicsystem.service.DiskManager;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.FileManager;
import com.musicsystem.util.InputValidator;
import com.musicsystem.util.Logger;

import java.util.Scanner;

public class Menu {
    private static final String CLASS_NAME = "Menu";
    private static final Logger logger = Logger.getInstance();
    
    private Scanner scanner;
    private InputValidator validator;
    private MusicCollection collection;
    private CompilationManager compilationManager;
    private DiskManager diskManager;
    private FileManager fileManager;
    private MenuInvoker invoker;

    public Menu() {
        logger.info(CLASS_NAME, "Ініціалізація головного меню...");
        
        this.scanner = new Scanner(System.in);
        this.validator = new InputValidator(scanner);
        this.collection = new MusicCollection();
        this.compilationManager = new CompilationManager();
        this.diskManager = new DiskManager();
        this.fileManager = new FileManager();
        this.invoker = new MenuInvoker();
        
        logger.info(CLASS_NAME, "Головне меню успішно ініціалізовано");
    }

    public void run() {
        logger.info(CLASS_NAME, "Запуск головного меню інтерфейсу");
        showWelcome();

        boolean running = true;

        while (running) {
            displayMenu();

            int choice = validator.readInt("\nВиберіть опцію: ", 0, 16);
            logger.debug(CLASS_NAME, "Користувач обрав опцію меню: " + choice);

            Command command = getCommand(choice);

            if (choice == 0) {
                running = handleExit();
            } else if (command != null) {
                try {
                    logger.info(CLASS_NAME, "Виконання команди: " + command.getClass().getSimpleName());
                    invoker.setCommand(command);
                    invoker.executeCommand();
                    logger.debug(CLASS_NAME, "Команда успішно виконана: " + command.getClass().getSimpleName());
                } catch (Exception e) {
                    logger.error(CLASS_NAME, "Помилка виконання команди: " + e.getMessage(), e);
                    System.out.println("\n✗ Помилка виконання команди: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                logger.warn(CLASS_NAME, "Невірний вибір опції меню: " + choice);
                System.out.println("\n✗ Невірний вибір. Спробуйте ще раз.");
            }
        }

        logger.info(CLASS_NAME, "Закриття меню інтерфейсу");
        scanner.close();
    }

    private void showWelcome() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("     СИСТЕМА УПРАВЛІННЯ ЗВУКОЗАПИСОМ");
        System.out.println("     Версія 1.0");
        System.out.println("=".repeat(60));
        System.out.println("\nВітаємо у системі управління музичною колекцією!");
        System.out.println("Для початку роботи оберіть опцію з меню.\n");

        validator.waitForEnter();
    }

    private Command getCommand(int choice) {
        switch (choice) {
            case 1:
                return new AddCompositionCommand(collection, validator);
            case 2:
                return new DeleteCompositionCommand(collection, validator);
            case 3:
                return new EditCompositionCommand(collection, validator);
            case 4:
                return new ViewCollectionCommand(collection, validator);
            case 5:
                return new FindByDurationCommand(collection, validator);
            case 6:
                return new FilterByStyleCommand(collection, validator);
            case 7:
                return new FilterByArtistCommand(collection, validator);
            case 8:
                return new CreateCompilationCommand(collection, compilationManager, validator);
            case 9:
                return new EditCompilationCommand(collection, compilationManager, validator);
            case 10:
                return new DeleteCompilationCommand(compilationManager, validator);
            case 11:
                return new SortByStyleCommand(compilationManager, validator);
            case 12:
                return new BurnToDiskCommand(compilationManager, diskManager, validator);
            case 13:
                return new ViewDisksCommand(diskManager, validator);
            case 14:
                return new ShowStatisticsCommand(collection, validator);
            case 15:
                return new LoadFromFileCommand(collection, fileManager, validator);
            case 16:
                return new SaveToFileCommand(collection, fileManager, validator);
            default:
                return null;
        }
    }

    private void displayMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    ГОЛОВНЕ МЕНЮ");
        System.out.println("=".repeat(60));

        System.out.println("\n┌─ УПРАВЛІННЯ КОЛЕКЦІЄЮ");
        System.out.println("│  1.  Додати композицію");
        System.out.println("│  2.  Видалити композицію");
        System.out.println("│  3.  Редагувати композицію");
        System.out.println("│  4.  Переглянути колекцію");
        System.out.println("│  5.  Знайти композицію за тривалістю");
        System.out.println("│  6.  Фільтрувати за стилем");
        System.out.println("│  7.  Фільтрувати за виконавцем");

        System.out.println("│");
        System.out.println("┌─ РОБОТА ЗІ ЗБІРКАМИ");
        System.out.println("│  8.  Створити збірку");
        System.out.println("│  9.  Редагувати збірку");
        System.out.println("│  10. Видалити збірку");
        System.out.println("│  11. Сортувати збірку за стилем");

        System.out.println("│");
        System.out.println("┌─ ЗАПИС НА ДИСК");
        System.out.println("│  12. Записати збірку на диск");
        System.out.println("│  13. Переглянути записані диски");

        System.out.println("│");
        System.out.println("┌─ АНАЛІЗ ТА ФАЙЛИ");
        System.out.println("│  14. Показати статистику колекції");
        System.out.println("│  15. Завантажити з файлу");
        System.out.println("│  16. Зберегти у файл");

        System.out.println("│");
        System.out.println("└─ 0.  Вихід");
        System.out.println("\n" + "=".repeat(60));

        showQuickInfo();
    }

    private void showQuickInfo() {
        System.out.printf("Композицій: %d | Збірок: %d | Дисків: %d\n",
                collection.getTotalCount(),
                compilationManager.getTotalCount(),
                diskManager.getTotalCount());
    }

    private boolean handleExit() {
        logger.info(CLASS_NAME, "Користувач ініціював вихід з програми");
        System.out.println("\n=== ВИХІД З ПРОГРАМИ ===\n");

        if (!collection.isEmpty()) {
            boolean save = validator.readBoolean("Зберегти зміни у колекції перед виходом?");
            if (save) {
                try {
                    logger.info(CLASS_NAME, "Збереження даних перед виходом...");
                    fileManager.saveToDefaultFile(collection);
                    System.out.println("✓ Дані збережено.");
                } catch (Exception e) {
                    logger.error(CLASS_NAME, "Помилка збереження при виході: " + e.getMessage(), e);
                    System.out.println("✗ Помилка збереження: " + e.getMessage());
                }
            } else {
                logger.info(CLASS_NAME, "Користувач відмовився від збереження перед виходом");
            }
        }

        System.out.println("\nДякуємо за використання системи!");
        System.out.println("До побачення!\n");
        logger.info(CLASS_NAME, "Програму завершено користувачем");
        return false;
    }
}