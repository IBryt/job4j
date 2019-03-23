package ru.job4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.job4j.mapper.UserMapper;

import java.util.List;

@Component(value = "jdbcStorage")
public class JdbcStorage implements Storage {

    private final JdbcTemplate storage;

    @Autowired
    public JdbcStorage(JdbcTemplate jdbcTemplate) {
        this.storage = jdbcTemplate;
    }

    public void add(User user) {
        String sql = "INSERT INTO users (id, name) VALUES (?, ?)";
        final int update = this.storage.update(sql, user.getId(), user.getName());
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        return storage.query(sql, new UserMapper());
    }

    public JdbcTemplate getStorage() {
        return storage;
    }
}
