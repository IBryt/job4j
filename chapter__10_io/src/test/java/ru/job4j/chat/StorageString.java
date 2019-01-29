package ru.job4j.chat;

import ru.job4j.chat.storage.AbstractStorage;
import ru.job4j.chat.storage.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StorageString extends AbstractStorage implements Storage {
    private List<String> messages = new ArrayList();
    @Override
    protected void initConfig() {
        this.storage = "src/main/resources/storagetest.txt";
        this.randomMsg = "src/main/resources/ListResponses.txt";
    }

    public StorageString() throws IOException {
        super();
    }

    @Override
    public String add(String value) throws IOException {
        messages.add(value);
        return storage;
    }

    @Override
    public List<String> getAllMessages() throws IOException {
        return messages;
    }
}
