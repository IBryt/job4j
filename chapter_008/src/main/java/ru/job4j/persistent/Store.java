package ru.job4j.persistent;

import ru.job4j.model.User;

import java.util.Map;

public interface Store<T>  {

    boolean add(T t);

    boolean update(T t);

    boolean delete(T t);

    Map<Integer, T> findAll();

    User findById(int id);

}
