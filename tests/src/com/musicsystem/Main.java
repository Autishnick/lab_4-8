package com.musicsystem;

import com.musicsystem.ui.Menu;

public class Main {
    public static void main(String[] args) {
        try {
            Menu menu = new Menu();
            menu.run();
        } catch (Exception e) {
            System.err.println("Критична помилка програми: " + e.getMessage());
            e.printStackTrace();
        }
    }
}