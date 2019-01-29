package ru.job4j.chat.io;

import java.io.IOException;

/**
 * Main output implementation in chat.
 */
public interface Output {

    /**
     * Show chat message.
     *
     * @param msg value for display.
     * @return true if show message.
     */
    boolean display(String msg);

    /**
     * Show chat message if it is validate value <code>send</code> by logic.
     *
     * @param send value for validate.
     * @return response value.
     * @throws IOException If an I/O error occurs.
     */
    String getResponse(String send) throws IOException;
}