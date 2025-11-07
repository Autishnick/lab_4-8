package com.musicsystem.command.impl;

import com.musicsystem.command.Command;

class MenuInvoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        if (command != null) {
            command.execute();
        }
    }
}
