package ru.job4j.chat.io;

import ru.job4j.chat.logic.Logic;

/**
 * This class implements interface <code>Output</code>.
 * Used console output.
 */
public class ConsoleOutput implements Output {
    /**
     * Field for checking logic.
     */
    private Logic logic;

    public ConsoleOutput(Logic logic) {
        this.logic = logic;
    }

    @Override
    public boolean display(String msg) {
        boolean res = logic.showMessage(msg);
        if (res) {
            System.out.println(msg);
        }
        return res;
    }

    @Override
    public String getResponse(String send) {
        return logic.showMessage(send) && send.intern() != Logic.CLOSE ? logic.getRandomMsg(send) : "";
    }
}