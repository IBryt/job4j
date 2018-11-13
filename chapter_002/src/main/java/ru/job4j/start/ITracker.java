package ru.job4j.start;

import ru.job4j.models.Item;

import java.util.List;

public interface ITracker {
    Item add(Item item);
    void edit(Item fresh);
    void delete(String id);
    List<Item> getAll();
    Item findByName(String key);
    Item findById(String id);
}
