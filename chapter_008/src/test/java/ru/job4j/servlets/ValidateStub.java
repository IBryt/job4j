package ru.job4j.servlets;

import ru.job4j.logic.Validate;
import ru.job4j.model.Role;
import ru.job4j.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateStub implements Validate {
    private int pkey = 1;
    private final Map<Integer, User> store = new HashMap<>();
    private List<Role> roles;

    public ValidateStub() {
        roles = new ArrayList<>(2);
        roles.add(new Role(1, "Admin", true));
        roles.add(new Role(1, "User", false));
    }

    @Override
    public boolean add(User user) {
        user.setId(this.pkey++);
        this.store.put(user.getId(), user);
        return user != null;
    }

    @Override
    public boolean update(User user) {
        store.put(user.getId(), user);
        return store.containsKey(user.getId());
    }

    @Override
    public boolean delete(int id) {
        return store.remove(id) != null;
    }

    @Override
    public Map<Integer, User> findAll() {
        return store;
    }

    @Override
    public User findById(int id) {
        return store.get(id);
    }

    @Override
    public User isCredentional(String login, String password) {
        return store.entrySet().
                stream().
                filter(e -> e.getValue().getLogin().equals(login)
                        && e.getValue().getPassword().equals(password)).
                findFirst().map(Map.Entry::getValue).orElse(null);
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public Role getRoleById(int id) {
        return roles.stream().filter(v -> v.getId() == id).findFirst().orElse(null);
    }

    @Override
    public User findByLogin(String login) {
        return store.entrySet().
                stream().
                filter(e -> e.getValue().getLogin().equals(login))
                .findFirst().map(Map.Entry::getValue).orElse(null);
    }
}
