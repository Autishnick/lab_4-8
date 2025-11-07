package com.musicsystem.util;

import java.util.Scanner;

public class InputValidator {
    private Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть ціле число.");
            }
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Помилка: число має бути від " + min + " до " + max);
        }
    }

    public int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Помилка: число має бути більше 0.");
        }
    }

    public String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Помилка: поле не може бути порожнім.");
        }
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt + " (так/ні): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("так") || input.equals("yes") || input.equals("y") || input.equals("т")) {
                return true;
            } else if (input.equals("ні") || input.equals("no") || input.equals("n") || input.equals("н")) {
                return false;
            }
            System.out.println("Помилка: введіть 'так' або 'ні'.");
        }
    }

    public int readDuration(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                String[] parts = input.split(":");
                if (parts.length == 2) {
                    int minutes = Integer.parseInt(parts[0]);
                    int seconds = Integer.parseInt(parts[1]);
                    if (minutes >= 0 && seconds >= 0 && seconds < 60) {
                        return minutes * 60 + seconds;
                    }
                } else if (parts.length == 3) {
                    int hours = Integer.parseInt(parts[0]);
                    int minutes = Integer.parseInt(parts[1]);
                    int seconds = Integer.parseInt(parts[2]);
                    if (hours >= 0 && minutes >= 0 && minutes < 60 && seconds >= 0 && seconds < 60) {
                        return hours * 3600 + minutes * 60 + seconds;
                    }
                }
            } catch (NumberFormatException e) {
                // Продовжуємо цикл
            }

            System.out.println("Помилка: введіть тривалість у форматі MM:SS або HH:MM:SS");
        }
    }

    public void waitForEnter() {
        System.out.println("\nНатисніть Enter для продовження...");
        scanner.nextLine();
    }
}