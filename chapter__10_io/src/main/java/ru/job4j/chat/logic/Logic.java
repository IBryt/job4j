package ru.job4j.chat.logic;

import ru.job4j.chat.storage.Storage;

import java.io.IOException;
import java.util.*;

public class Logic implements BasicLogic {
    private boolean show = true;
    private Storage storage;
    private List<String> randomMsg;
    public Logic(Storage storage) throws IOException {
        this.storage = storage;
        randomMsg = storage.getRandomMsg();
    }


    public boolean showResponse(String value) {
        if (BasicLogic.STOP == value.intern()) {
            show = false;
        } else if (BasicLogic.CONTINUE == value.intern()) {
            show = true;
        }
        return show;
    }

    @Override
    public void add(String msg) throws IOException {
         storage.add(msg);
    }

    @Override
    public String getRandomMsg() {
        return randomMsg.get(new Random().nextInt(randomMsg.size()));
    }

}
