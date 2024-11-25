package com.salvai.eldar;

import com.salvai.eldar.controllers.ConsoleMenuController;

public class Main {
    public static void main(String[] args) {
        final var consoleMenuController = new ConsoleMenuController();
        consoleMenuController.run();
    }
}