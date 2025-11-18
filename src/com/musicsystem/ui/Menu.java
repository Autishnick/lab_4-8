package com.musicsystem.ui;

import com.musicsystem.command.Command;
import com.musicsystem.command.CommandRegistry;
import com.musicsystem.command.MenuInvoker;
import com.musicsystem.service.CompilationManager;
import com.musicsystem.service.DiskManager;
import com.musicsystem.service.MusicCollection;
import com.musicsystem.util.FileManager;
import com.musicsystem.util.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Scanner;

public class Menu {
    private static final Logger logger = LogManager.getLogger(Menu.class);
    
    private Scanner scanner;
    private InputValidator validator;
    private MusicCollection collection;
    private CompilationManager compilationManager;
    private DiskManager diskManager;
    private FileManager fileManager;
    private MenuInvoker invoker;
    private CommandRegistry commandRegistry;

    public Menu() {
        logger.info("Ініціалізація головного меню...");
        
        this.scanner = new Scanner(System.in);
        this.validator = new InputValidator(scanner);
        this.collection = new MusicCollection();
        this.compilationManager = new CompilationManager();
        this.diskManager = new DiskManager();
        this.fileManager = new FileManager();
        this.invoker = new MenuInvoker();
        this.commandRegistry = new CommandRegistry();
        
        logger.info("Головне меню успішно ініціалізовано");
    }

    public void run() {
        logger.info("Запуск головного меню інтерфейсу");
        showWelcome();

        boolean running = true;

        while (running) {
            displayMenu();

            int maxOption = commandRegistry.getMaxOptionNumber();
            int choice = validator.readInt("\nВиберіть опцію: ", 0, maxOption);
            logger.debug("Користувач обрав опцію меню: " + choice);

            CommandRegistry.CommandContext context = new CommandRegistry.CommandContext(
                    collection, compilationManager, diskManager, fileManager, validator);
            Command command = commandRegistry.getCommand(choice, context);

            if (choice == 0) {
                running = handleExit();
            } else if (command != null) {
                try {
                    logger.debug("Виконання команди: " + command.getClass().getSimpleName());
                    invoker.setCommand(command);
                    invoker.executeCommand();
                } catch (Exception e) {
                    logger.error("Помилка виконання команди: " + command.getClass().getSimpleName(), e);
                    System.out.println("\n✗ Помилка виконання команди: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                logger.debug("Невірний вибір опції меню: " + choice);
                System.out.println("\n✗ Невірний вибір. Спробуйте ще раз.");
            }
        }

        logger.info("Закриття меню інтерфейсу");
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


    private void displayMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    ГОЛОВНЕ МЕНЮ");
        System.out.println("=".repeat(60));

        Map<Integer, CommandRegistry.MenuOption> options = commandRegistry.getAllOptions();
        String currentCategory = null;

        for (CommandRegistry.MenuOption option : options.values()) {
            if (!option.getCategory().equals(currentCategory)) {
                if (currentCategory != null) {
                    System.out.println("│");
                }
                System.out.println("┌─ " + option.getCategory());
                currentCategory = option.getCategory();
            }
            System.out.printf("│  %-2d. %s%n", option.getNumber(), option.getDescription());
        }

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
        logger.debug("Користувач ініціював вихід з програми");
        System.out.println("\n=== ВИХІД З ПРОГРАМИ ===\n");

        if (!collection.isEmpty()) {
            boolean save = validator.readBoolean("Зберегти зміни у колекції перед виходом?");
            if (save) {
                try {
                    logger.debug("Збереження даних перед виходом...");
                    fileManager.saveToDefaultFile(collection);
                    System.out.println("✓ Дані збережено.");
                } catch (Exception e) {
                    logger.error("Помилка збереження при виході", e);
                    System.out.println("✗ Помилка збереження: " + e.getMessage());
                }
            } else {
                logger.debug("Користувач відмовився від збереження перед виходом");
            }
        }

        System.out.println("\nДякуємо за використання системи!");
        System.out.println("До побачення!\n");
        logger.debug("Програму завершено користувачем");
        return false;
    }
}