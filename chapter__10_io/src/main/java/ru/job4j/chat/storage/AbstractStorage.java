package ru.job4j.chat.storage;

import ru.job4j.chat.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Basic implementation storage.
 */
public class AbstractStorage {
    /**
     * Variable for to record chat messages.
     */
    protected String storage;

    /**
     * File path for getting list random responses
     */
    protected String randomMsg;

    /**
     * List random responses
     */
    protected List<String> listResp;

    public AbstractStorage() throws IOException {
        initConfig();
        try {
            Files.deleteIfExists(Paths.get(storage));
        } catch (IOException e) {
            throw new IOException(e);
        }
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(randomMsg))) {
            listResp = reader.lines().filter(s -> s.intern() != "").collect(Collectors.toList());
        }
    }

    /**
     * init config.
     */
    protected void initConfig() {
        Config config = new Config();
        storage = config.get("storage");
        randomMsg = config.get("responses");
    }

    /**
     * @return random value from the list.
     */
    public String getRandomResp() {
        return listResp.get(new Random().nextInt(listResp.size()));
    }
}
