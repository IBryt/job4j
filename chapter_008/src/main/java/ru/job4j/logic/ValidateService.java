package ru.job4j.logic;

import ru.job4j.model.Role;
import ru.job4j.model.User;

import java.util.List;
import java.util.Map;

import ru.job4j.persistent.DbStore;
import ru.job4j.persistent.Store;

public class ValidateService {
    private static final ValidateService INSTANCE = new ValidateService();
    private final Store<User> store = DbStore.getInstance();
    private ValidateService() { }
    public static ValidateService getInstance() {
        return INSTANCE;
    }

    public boolean add(final User user) {
        boolean res = false;
        if (checkUserNameLogin(user)) {
            res = store.add(user);
        }
        return res;
    }

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

    public boolean delete(final int id) {
        User user = store.findById(id);
        return user == null ? false : store.delete(user);
    }

    public Map<Integer, User> findAll() {
        return store.findAll();
    }

    public User findById(final int id) {
        return store.findById(id);
    }

    public User isCredentional(String login, String password) {
        return store.isCredentional(login, password);
    }

    public List<Role> getRoles() {
        return store.getRoles();
    }
    public Role getRoleById(int id) {
        return store.getRoleById(id);
    }

    public User findByLogin(final String login) {
        return store.findByLogin(login);
    }
}
