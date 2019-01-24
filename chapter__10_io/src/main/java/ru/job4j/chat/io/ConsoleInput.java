package ru.job4j.chat.io;

import ru.job4j.chat.logic.Logic;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private Scanner scanner = new Scanner(System.in);
    private Logic logic;

    public ConsoleInput(Logic logic) {
        this.logic = logic;
    }

    @Override
    public String getSend() {
        return scanner.next();
    }
}