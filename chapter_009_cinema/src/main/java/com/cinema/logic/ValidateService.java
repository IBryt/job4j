package com.cinema.logic;


import com.cinema.model.Hall;
import com.cinema.persistent.DbStore;
import com.cinema.persistent.Store;

import java.util.List;

public class ValidateService implements Validate {
    private static final Validate INSTANCE = new ValidateService();
    private final Store<Hall> store = DbStore.getInstance();
    private ValidateService() { }
    public static Validate getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Hall> getPlaceHall() {
        return store.getPlaceHall();
    }
}
