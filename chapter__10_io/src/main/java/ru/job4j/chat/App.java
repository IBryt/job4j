package ru.job4j.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.chat.io.*;
import ru.job4j.chat.logic.*;
import ru.job4j.chat.storage.*;

import java.io.IOException;

/**
 * The startup class and initializes the data for the chat.
 */
public class App {
    /**
     * Logger.
     */
    private static final Logger LOG = LogManager.getLogger(App.class.getName());
    /**
     * Enter data in chat
     */
    private Input input;
    /**
     * Chat logic.
     */
    private Logic logic;
    /**
     * Data output from chat
     */
    private Output output;

    public App(final Input input, final Logic logic, final Output output) {
        this.input = input;
        this.logic = logic;
        this.output = output;
    }

    /**
     * Starting work the chat.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void start() throws IOException {
        String response = "";
        String send = "";
        do {
            send = input.getSend();
            logic.add(send);
            response = output.getResponse(send);
            output.display(response);
            logic.add(response);
        } while (send.intern() != Logic.CLOSE);
    }

    public static void main(String[] args) {
        try {
            StorageFile storage = new StorageFile();
            Logic logic = new Logic(storage);
            Input input = new ConsoleInput(logic);
            Output output = new ConsoleOutput(logic);
            App app = new App(input, logic, output);
            app.start();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
