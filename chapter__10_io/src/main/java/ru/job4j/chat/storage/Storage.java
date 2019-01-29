package ru.job4j.chat.storage;

import java.io.IOException;
import java.util.List;

/**
 * Basic implementation of storage.
 */
public interface Storage {

    /**
     * Added element in storage.
     *
     * @param value Appends the specified element.
     * @return Value added.
     * @throws IOException If an I/O error occurs.
     */
    String add(String value) throws IOException;

    /**
     * In the saved list of messages, a random value is searched for to be sent to the client.
     *
     * @return Random value from list messages.
     */
    String getRandomResp();

    /**
     * @return list of chat messages.
     * @throws IOException If an I/O error occurs.
     */
    List<String> getAllMessages() throws IOException;
}
