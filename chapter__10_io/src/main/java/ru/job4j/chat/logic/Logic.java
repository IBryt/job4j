package ru.job4j.chat.logic;

import ru.job4j.chat.storage.Storage;

import java.io.IOException;
import java.util.List;

/**
 * Implements logic work chat.
 */
public final class Logic implements BasicLogic {
    /**
     * value to check output response
     */
    private boolean show = true;

    /**
     * Link on storage.
     */
    private Storage storage;

    public Logic(Storage storage) {
        this.storage = storage;
    }

    private boolean showResponse(String value) {
        if (BasicLogic.STOP == value.intern()) {
            show = false;
        } else if (BasicLogic.CONTINUE == value.intern()) {
            show = true;
        }
        return show;
    }

    @Override
    public boolean add(String msg) throws IOException {
        boolean res = false;
        if (showMessage(msg)) {
            storage.add(msg);
            res = true;
        }
        return res;
    }

    @Override
    public String getRandomMsg(String send) {
        return showResponse(send) ? storage.getRandomResp() : "";
    }

    @Override
    public List<String> getAllMessages() throws IOException {
        return storage.getAllMessages();
    }
}
