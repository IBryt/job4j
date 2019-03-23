package ru.job4j;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(value = "memoryStorage")
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

    public Map<Integer, User> getStorage() {
        return storage;
    }

    public void setStorage(Map<Integer, User> storage) {
        this.storage = storage;
    }
}
