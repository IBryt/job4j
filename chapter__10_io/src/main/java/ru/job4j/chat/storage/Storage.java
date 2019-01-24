package ru.job4j.chat.storage;

import java.io.IOException;
import java.util.List;

public interface Storage {
    void add(String value) throws IOException;

    List<String> getRandomMsg() throws IOException;
}
