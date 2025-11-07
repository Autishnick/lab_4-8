package com.musicsystem.command.impl;

import com.musicsystem.command.Command;
import com.musicsystem.model.Compilation;
import com.musicsystem.service.CompilationManager;
import com.musicsystem.util.InputValidator;

import java.util.List;

public class DeleteCompilationCommand implements Command {
    private CompilationManager compilationManager;
    private InputValidator validator;

    public DeleteCompilationCommand(CompilationManager compilationManager, InputValidator validator) {
        this.compilationManager = compilationManager;
        this.validator = validator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== ВИДАЛЕННЯ ЗБІРКИ ===\n");

        if (compilationManager.isEmpty()) {
            System.out.println("Немає створених збірок.");
            return;
        }

        List<Compilation> compilations = compilationManager.getAll();
        System.out.println("Збірки:\n");
        for (int i = 0; i < compilations.size(); i++) {
            System.out.println((i + 1) + ". " + compilations.get(i));
        }

        int choice = validator.readInt("\nОберіть збірку для видалення (0 для скасування): ",
                0, compilations.size());

        if (choice == 0) {
            System.out.println("Видалення скасовано.");
            return;
        }

        Compilation toDelete = compilations.get(choice - 1);
        boolean confirm = validator.readBoolean("\nВидалити збірку \"" + toDelete.getName() + "\"?");

        if (confirm) {
            compilationManager.delete(toDelete.getId());
            System.out.println("\n✓ Збірку успішно видалено!");
        } else {
            System.out.println("\nВидалення скасовано.");
        }
    }
}