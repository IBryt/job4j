package ru.job4j.persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.model.User;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DbStore implements Store<User> {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DbStore INSTANCE = new DbStore();
    private static final String CONFIG = "application.properties";
    private static final Logger LOG = LogManager.getLogger(DbStore.class.getName());

    public static Store<User> getInstance() {
        return INSTANCE;
    }
    private DbStore() {
        try (InputStream in = MemoryStore.class.getClassLoader().getResourceAsStream(CONFIG)) {
            Properties config = new Properties();
            config.load(in);
            SOURCE.setDriverClassName(config.getProperty("db.driver"));
            SOURCE.setUrl(config.getProperty("db.url"));
            SOURCE.setUsername(config.getProperty("db.username"));
            SOURCE.setPassword(config.getProperty("db.password"));
            SOURCE.setMinIdle(5);
            SOURCE.setMaxIdle(10);
            SOURCE.setMaxOpenPreparedStatements(100);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    @Override
    public boolean add(User user) {
        boolean res = false;
        try (Connection connection = SOURCE.getConnection()) {
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
    public boolean update(User user) {
        try (Connection connection = SOURCE.getConnection()) {
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
    public boolean delete(User user) {
        boolean res = false;
        try (Connection connection = SOURCE.getConnection()) {
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
        try (Connection connection = SOURCE.getConnection()) {
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

    /**
     * @return true if user exists with such name or login and ignore current entry;
     */
    @Override
    public boolean checkUnique(User user) {
        boolean res = false;
        try (Connection connection = SOURCE.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement("SELECT * FROM Users WHERE (login = ? OR email = ?) AND id != ?")) {
                st.setString(1, user.getLogin());
                st.setString(2, user.getEmail());
                st.setInt(3, user.getId());
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    res = true;
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return res;
    }

    @Override
    public User findById(int id) {
        User user = null;
        try (Connection connection = SOURCE.getConnection()) {
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
}
