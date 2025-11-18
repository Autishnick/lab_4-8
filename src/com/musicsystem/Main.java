package com.musicsystem;


import com.musicsystem.ui.Menu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    static {
        com.musicsystem.util.LoggerConfig.loadSystemProperties();
    }
    
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("========================================");
        logger.info("Music System Management - Запуск програми");
        logger.info("========================================");

        try {
            logger.info("Ініціалізація головного меню...");
            Menu menu = new Menu();

            logger.info("Меню успішно ініціалізовано. Запуск інтерфейсу...");
            menu.run();

            logger.info("Програма завершена користувачем");

        } catch (OutOfMemoryError e) {
            String errorMsg = "Критична помилка: недостатньо пам'яті для виконання програми";
            logger.fatal(errorMsg, e);
            System.err.println(errorMsg);
            System.err.println("Деталі: " + e.getMessage());
            System.exit(1);

        } catch (Exception e) {
            String errorMsg = "Критична помилка програми: " + e.getMessage();
            logger.fatal(errorMsg, e);
            System.err.println(errorMsg);
            e.printStackTrace();
            System.exit(1);

        } catch (Throwable t) {
            String errorMsg = "Непередбачена критична помилка: " + t.getClass().getSimpleName();
            logger.fatal(errorMsg, t);
            System.err.println(errorMsg);
            t.printStackTrace();
            System.exit(1);
        }
    }
}