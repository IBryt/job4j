package ru.job4j;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Storage {

    void add(User user);
    List<User> getAll();
}
