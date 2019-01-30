package ru.job4j.bot;

import java.io.InputStream;
import java.util.Properties;

/**
 * This class works with resources properties.
 */

public final class Config {
    private final Properties values = new Properties();

    public Config() {
        init();
    }

    private void init() {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            values.load(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Associates the specified value with the specified key.
     *
     * @param key key with which the specified value is to be associated.
     * @return associated key with value.
     */
    public String get(String key) {
        return this.values.getProperty(key);
    }
}