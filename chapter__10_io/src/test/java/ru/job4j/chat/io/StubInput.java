package ru.job4j.chat.io;

import ru.job4j.chat.logic.Logic;

public class StubInput implements Input {
    private Logic logic;
    private String[] sends;
    private int position = 0;

    public void setSends(String[] sends) {
        this.sends = sends;
    }

    public StubInput(Logic logic) {
        this.logic = logic;
    }

    @Override
    public String getSend() {
        return sends[position++];
    }
}
