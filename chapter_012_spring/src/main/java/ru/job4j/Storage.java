package ru.job4j;

import org.springframework.stereotype.Component;

@Component
public interface Storage {
    void add(User user);
}
