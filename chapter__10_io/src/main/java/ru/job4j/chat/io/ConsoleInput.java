package ru.job4j.chat.io;

import ru.job4j.chat.logic.Logic;

import java.util.Scanner;

/**
 * This class implements interface <code>Input</code>.
 * Used console input.
 */
public class ConsoleInput implements Input {
    /**
     * The input source.
     */
    private Scanner scanner = new Scanner(System.in);
    /**
     * Field for checking logic.
     */
    private Logic logic;

    public ConsoleInput(Logic logic) {
        this.logic = logic;
    }

    @Override
    public String getSend() {
        return scanner.next();
    }
}