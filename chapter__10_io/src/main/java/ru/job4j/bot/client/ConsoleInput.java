package ru.job4j.bot.client;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private Scanner console = new Scanner(System.in);

    @Override
    public String read() {
        return console.nextLine();
    }
}
