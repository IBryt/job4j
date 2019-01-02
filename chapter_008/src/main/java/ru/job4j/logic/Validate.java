package ru.job4j.logic;

import ru.job4j.model.Role;
import ru.job4j.model.User;

import java.util.List;
import java.util.Map;

public interface Validate {

    boolean add(final User user);

    boolean update(final User user);

    boolean delete(final int id);

    Map<Integer, User> findAll();

    User findById(final int id);

    User isCredentional(String login, String password);

    List<Role> getRoles();

    Role getRoleById(int id);

    User findByLogin(final String login);
}
