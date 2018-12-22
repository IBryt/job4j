package ru.job4j.persistent;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.model.User;

import java.io.Closeable;
import java.io.InputStream;
import java.sql.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MemoryStore implements Store<User>, AutoCloseable, Closeable {
    private static final MemoryStore INSTANCE = new MemoryStore();
    private static final String CONFIG = "application.properties";
    private static final Logger LOG = LogManager.getLogger(MemoryStore.class.getName());
    private Connection connection;

    private MemoryStore(Connection connection) {
        this.connection = connection;
    }

    private MemoryStore() { }

    public static MemoryStore getInstance() {
        return INSTANCE;
    }

    private Connection getConnection() {
        try (InputStream in = MemoryStore.class.getClassLoader().getResourceAsStream(CONFIG)) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("db.driver"));
            connection = DriverManager.getConnection(
                    config.getProperty("db.url"),
                    config.getProperty("db.username"),
                    config.getProperty("db.password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return this.connection;
    }

    @Override
    public synchronized boolean add(User user) {
        boolean res = false;
        try (MemoryStore memoryStore = new MemoryStore(getConnection())) {
            try (PreparedStatement st = connection.prepareStatement("INSERT INTO users(name, login, email, date) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, user.getName());
                st.setString(2, user.getLogin());
                st.setString(3, user.getEmail());
                st.setTimestamp(4, user.getCreateDate());
                st.executeUpdate();
                ResultSet generatedKeys = st.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
                res = true;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return res;
    }

    @Override
    public synchronized boolean update(User user) {
        try (MemoryStore memoryStore = new MemoryStore(getConnection())) {
            try (PreparedStatement st = connection.prepareStatement("UPDATE users SET name = ?, login = ?, email = ?, date = ? WHERE id = ?")) {
                st.setString(1, user.getName());
                st.setString(2, user.getLogin());
                st.setString(3, user.getEmail());
                st.setTimestamp(4, user.getCreateDate());
                st.setInt(5, user.getId());
                st.executeUpdate();
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public synchronized boolean delete(User user) {
        boolean res = false;
        try (MemoryStore memoryStore = new MemoryStore(getConnection())) {
            try (PreparedStatement st = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
                st.setInt(1, user.getId());
                st.executeUpdate();
                res = true;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return res;
    }

    @Override
    public Map<Integer, User> findAll() {
        Map<Integer, User> users = new HashMap<>();
        try (MemoryStore memoryStore = new MemoryStore(getConnection())) {
            try (Statement st = connection.createStatement()) {
                ResultSet rs = st.executeQuery("SELECT * FROM Users");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    users.put(id,
                            new User(
                                    id,
                                    rs.getString("name"),
                                    rs.getString("login"),
                                    rs.getString("email"),
                                    rs.getTimestamp("date")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return users;
    }

    @Override
    public User findById(int id) {
        User user = null;
        try (MemoryStore memoryStore = new MemoryStore(getConnection())) {
            try (PreparedStatement st = connection.prepareStatement("SELECT * FROM Users WHERE id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    user = new User(
                            id,
                            rs.getString("name"),
                            rs.getString("login"),
                            rs.getString("email"),
                            rs.getTimestamp("date")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
