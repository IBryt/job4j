package ru.job4j;

import org.springframework.stereotype.Component;

@Component
public class MemoryStorage implements Storage {

    public void add(User user) {
        System.out.println("store to memory");
    }
}
