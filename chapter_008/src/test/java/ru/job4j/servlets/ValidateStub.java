package ru.job4j.servlets;

import ru.job4j.logic.Validate;
import ru.job4j.model.Role;
import ru.job4j.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateStub implements Validate{
    private int pkey = 1;
    private final Map<Integer, User> store = new HashMap<>();

    @Override
    public boolean add(User user) {
        user.setId(this.pkey++);
        this.store.put(user.getId(), user);
        return user != null;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Map<Integer, User> findAll() {
        return null;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public User isCredentional(String login, String password) {
        return null;
    }

    @Override
    public List<Role> getRoles() {
        return null;
    }

    @Override
    public Role getRoleById(int id) {
        return null;
    }

    @Override
    public User findByLogin(String login) {
        return null;
    }
}
