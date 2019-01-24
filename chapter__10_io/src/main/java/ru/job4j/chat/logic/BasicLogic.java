package ru.job4j.chat.logic;

import java.io.IOException;

public interface BasicLogic {
    String CLOSE = "закончить";
    String STOP = "стоп";
    String CONTINUE = "продолжить";

    void add(String msg) throws IOException;

    String getRandomMsg();
}
