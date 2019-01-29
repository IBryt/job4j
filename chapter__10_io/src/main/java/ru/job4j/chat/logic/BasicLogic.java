package ru.job4j.chat.logic;

import java.io.IOException;
import java.util.List;

/**
 * Interface contains constants for management chat and methods for implementations.
 */
public interface BasicLogic {
    /**
     * Value by which closing chat.
     */
    String CLOSE = "закончить";

    /**
     * Terminates to display random messages
     */
    String STOP = "стоп";

    /**
     * Continue to display random messages
     */
    String CONTINUE = "продолжить";

    /**
     * Adds message after validation by logic.
     * if <code>msg</code> is empty then it is not added.
     *
     * @param msg Appends the specified element.
     * @return true if added else false.
     * @throws IOException If an I/O error occurs.
     */
    boolean add(String msg) throws IOException;

    /**
     * Search message after verification.
     * @param send value for validate.
     * @return Random value from list messages.
     */
    String getRandomMsg(String send);

    /**
     * Checking <code>msg</code>.
     *
     * @param msg value for validate.
     * @return false if <code>msg</code> is empty else true.
     */
    default boolean showMessage(String msg) {
        return !msg.isEmpty();
    }

    /**
     * @return list of chat messages.
     * @throws IOException If an I/O error occurs.
     */
    List<String> getAllMessages() throws IOException;
}
