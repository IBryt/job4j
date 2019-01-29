package ru.job4j.chat.io;

/**
 * Main input implementation in chat.
 */
public interface Input {

    /**
     * Adds message if validate by logic.
     *
     * @return value for display.
     */
    String getSend();
}