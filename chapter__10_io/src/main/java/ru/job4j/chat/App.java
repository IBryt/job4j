package ru.job4j.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.chat.io.*;
import ru.job4j.chat.logic.*;
import ru.job4j.chat.storage.*;

import java.io.IOException;

public class App {
    private static final Logger LOG = LogManager.getLogger(App.class.getName());
    private Input input;
    private Logic logic;
    private Output output;

    public App(final Input input, final Logic logic, final Output output) {
        this.input = input;
        this.logic = logic;
        this.output = output;
    }

    private void start() throws IOException {
        String response;
        String send;
        do {
            send = input.getSend();
            logic.add(send);
            if (logic.showResponse(send)) {
                response = output.getResponse();
                output.display(response);
                logic.add(response);
            }
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
