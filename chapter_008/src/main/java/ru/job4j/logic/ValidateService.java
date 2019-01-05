package ru.job4j.logic;

import ru.job4j.model.Countries;
import ru.job4j.model.Role;
import ru.job4j.model.Towns;
import ru.job4j.model.User;

import java.util.List;
import java.util.Map;

import ru.job4j.persistent.DbStore;
import ru.job4j.persistent.Store;

public class ValidateService implements Validate {
    private static final Validate INSTANCE = new ValidateService();
    private final Store<User> store = DbStore.getInstance();
    private ValidateService() { }
    public static Validate getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(final User user) {
        boolean res = false;
        if (checkUserNameLogin(user)) {
            res = store.add(user);
        }
        return res;
    }

    @Override
    public boolean update(final User user) {
        boolean res = false;
        if (checkUserNameLogin(user)) {
            res = store.update(user);
        }
        return res;
    }

    private boolean checkUserNameLogin(User user) {
        return user != null
                && user.getName() != null
                && !user.getName().equals("")
                && user.getLogin() != null
                && !user.getLogin().equals("")
                && !store.checkUnique(user);
    }

    @Override
    public boolean delete(final int id) {
        User user = store.findById(id);
        return user == null ? false : store.delete(user);
    }

    @Override
    public Map<Integer, User> findAll() {
        return store.findAll();
    }

    @Override
    public User findById(final int id) {
        return store.findById(id);
    }

    @Override
    public User isCredentional(String login, String password) {
        return store.isCredentional(login, password);
    }

    @Override
    public List<Role> getRoles() {
        return store.getRoles();
    }

    @Override
    public Role getRoleById(int id) {
        return store.getRoleById(id);
    }

    @Override
    public User findByLogin(final String login) {
        return store.findByLogin(login);
    }

    @Override
    public Map<Integer, Countries> getCountries() {
        return store.getCountries();
    }

    @Override
    public Map<Integer, Towns> getTowns(String country) {
        return store.getTowns(country);
    }

    @Override
    public Countries getCountry(String country) {
        return store.getCountry(country);
    }

    @Override
    public Towns getTown(String town, Countries country) {
        return store.getTown(town, country);
    }
}
