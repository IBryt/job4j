package ru.job4j.persistent;

import ru.job4j.model.Role;
import ru.job4j.model.User;

import java.util.List;
import java.util.Map;

public interface Store<T>  {

    boolean add(T t);

    boolean update(T t);

    boolean delete(T t);

    Map<Integer, T> findAll();

    User findById(int id);

    boolean checkUnique(T t);

    User isCredentional(String login, String password);

    List<Role> getRoles();

    Role getRoleById(int id);

    User findByLogin(String login);
}
