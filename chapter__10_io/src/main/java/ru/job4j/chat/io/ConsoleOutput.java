package ru.job4j.chat.io;

import ru.job4j.chat.logic.Logic;

public class ConsoleOutput implements Output {
    private Logic logic;
    public ConsoleOutput(Logic logic) {
        this.logic = logic;
    }

    @Override
    public void display(String msg) {
        System.out.println(msg);
    }

    @Override
    public String getResponse() {
        return logic.getRandomMsg();
    }
}