package ru.job4j.storage;

import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private final Properties values = new Properties();

    public Config() {
        init();
    }

    private void init() {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("app.properties")) {
            values.load(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public String get(String key) {
        return this.values.getProperty(key);
    }
}