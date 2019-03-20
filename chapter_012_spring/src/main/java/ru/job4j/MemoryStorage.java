package ru.job4j;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Primary
public class MemoryStorage implements Storage {
    private Map<Integer, User> storage = new HashMap();

    public void add(User user) {
        if (storage.get(user.getId()) != null) {
            throw new IllegalArgumentException("Can't add this object because this id exists in map");
        }
        storage.put(user.getId(), user);
    }

    @Override
    public List<User> getAll() {
        return storage.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }
}
