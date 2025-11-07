// noinspection SpellCheckingInspection
package com.musicsystem;

// noinspection SpellCheckingInspection
import com.musicsystem.ui.Menu;
// noinspection SpellCheckingInspection
import com.musicsystem.util.Logger;

public class Main {
    private static final String CLASS_NAME = "Main";
    private static final Logger logger = Logger.getInstance();

    public static void main(String[] args) {
        logger.info(CLASS_NAME, "========================================");
        logger.info(CLASS_NAME, "Music System Management - Запуск програми");
        logger.info(CLASS_NAME, "========================================");

        try {
            logger.info(CLASS_NAME, "Ініціалізація головного меню...");
            Menu menu = new Menu();

            logger.info(CLASS_NAME, "Меню успішно ініціалізовано. Запуск інтерфейсу...");
            menu.run();

            logger.info(CLASS_NAME, "Програма завершена користувачем");
            logger.info(CLASS_NAME, "========================================");

        } catch (OutOfMemoryError e) {
            String errorMsg = "Критична помилка: недостатньо пам'яті для виконання програми";
            logger.fatal(CLASS_NAME, errorMsg, e);
            System.err.println(errorMsg);
            System.err.println("Деталі: " + e.getMessage());
            System.exit(1);

        } catch (Exception e) {
            String errorMsg = "Критична помилка програми: " + e.getMessage();
            logger.fatal(CLASS_NAME, errorMsg, e);
            System.err.println(errorMsg);
            e.printStackTrace();
            System.exit(1);

        } catch (Throwable t) {
            String errorMsg = "Непередбачена критична помилка: " + t.getClass().getSimpleName();
            logger.fatal(CLASS_NAME, errorMsg, t);
            System.err.println(errorMsg);
            t.printStackTrace();
            System.exit(1);
        }
    }
}