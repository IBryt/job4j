package ru.job4j;

import java.util.List;

public interface Storage {

    void add(User user);
    List<User> getAll();
}
