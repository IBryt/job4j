package ru.job4j.persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.model.Countries;
import ru.job4j.model.Role;
import ru.job4j.model.Towns;
import ru.job4j.model.User;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class DbStore implements Store<User> {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DbStore INSTANCE = new DbStore();
    private static final String CONFIG = "application.properties";
    private static final Logger LOG = LogManager.getLogger(DbStore.class.getName());

    public static Store<User> getInstance() {
        return INSTANCE;
    }
    private DbStore() {
        try (InputStream in = DbStore.class.getClassLoader().getResourceAsStream(CONFIG)) {
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
            try (PreparedStatement st = connection.prepareStatement("INSERT INTO users(name, login, email, date, password, role_id) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, user.getName());
                st.setString(2, user.getLogin());
                st.setString(3, user.getEmail());
                st.setTimestamp(4, user.getCreateDate());
                st.setString(5, user.getPassword());
                st.setInt(6, user.getRole().getId());
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
            try (PreparedStatement st = connection.prepareStatement("UPDATE users SET name = ?, login = ?, email = ?, date = ?, password = ?, role_id = ? WHERE id = ?")) {
                st.setString(1, user.getName());
                st.setString(2, user.getLogin());
                st.setString(3, user.getEmail());
                st.setTimestamp(4, user.getCreateDate());
                st.setString(5, user.getPassword());
                st.setInt(6, user.getRole().getId());
                st.setInt(7, user.getId());
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
                                    rs.getTimestamp("date"),
                                    rs.getString("password"),
                                    getRoleById(rs.getInt("role_id"))
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
                            rs.getTimestamp("date"),
                            rs.getString("password"),
                            getRoleById(rs.getInt("role_id"))
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public User isCredentional(String login, String password) {
        User user = null;
        try (Connection connection = SOURCE.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement("SELECT * FROM Users WHERE login = ? AND password = ?")) {
                st.setString(1, login);
                st.setString(2, password);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    user = new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("login"),
                            rs.getString("email"),
                            rs.getTimestamp("date"),
                            rs.getString("password"),
                            getRoleById(rs.getInt("role_id"))
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        try (Connection connection = SOURCE.getConnection()) {
            try (Statement st = connection.createStatement()) {
                ResultSet rs = st.executeQuery("SELECT * FROM Role");
                while (rs.next()) {
                    roles.add(new Role(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getBoolean("editAll")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return roles;
    }

    @Override
    public Role getRoleById(int id) {
        Role role = null;
        try (Connection connection = SOURCE.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement("SELECT * FROM role WHERE id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    role = new Role(rs.getInt("id"), rs.getString("name"), rs.getBoolean("editAll"));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return role;
    }

    @Override
    public User findByLogin(String login) {
        User user = null;
        try (Connection connection = SOURCE.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement("SELECT * FROM Users WHERE login = ?")) {
                st.setString(1, login);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    user = new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("login"),
                            rs.getString("email"),
                            rs.getTimestamp("date"),
                            rs.getString("password"),
                            getRoleById(rs.getInt("role_id"))
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public Map<Integer, Countries> getCountries() {
        Map<Integer, Countries> countries = new HashMap<>();
        try (Connection connection = SOURCE.getConnection()) {
            try (Statement st = connection.createStatement()) {
                ResultSet rs = st.executeQuery("SELECT * FROM Countries");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    countries.put(id,
                            new Countries(
                                    id,
                                    rs.getString("name")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return countries;
    }

    @Override
    public Map<Integer, Towns> getTowns(String country) {
        Map<Integer, Towns> towns = new HashMap<>();
        try (Connection connection = SOURCE.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement("SELECT \n"
                    + "\ttowns.id,\n"
                    + "\ttowns.name\n"
                    + "FROM towns AS towns\n"
                    + "\tLEFT JOIN countries AS countries ON towns.countries_id = countries.id\n"
                    + "WHERE \n"
                    + "\tcountries.name = ?")) {
                st.setString(1, country);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    towns.put(id, new Towns(id, rs.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return towns;
    }

    @Override
    public Countries getCountry(String country) {
        Countries res = null;
        try (Connection connection = SOURCE.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement("SELECT * FROM countries WHERE name = ?")) {
                st.setString(1, country);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    res = new Countries(
                            rs.getInt("id"),
                            rs.getString("name")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return res;
    }

    @Override
    public Towns getTown(String town, Countries country) {
        Towns res = null;
        try (Connection connection = SOURCE.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement("SELECT * FROM towns WHERE name = ? AND countries_id = ?")) {
                st.setString(1, town);
                st.setInt(2, country.getId());
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    res = new Towns(
                            rs.getInt("id"),
                            rs.getString("name"),
                            country
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return res;
    }
}
