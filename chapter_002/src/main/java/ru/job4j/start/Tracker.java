package ru.job4j.start;

import ru.job4j.models.*;

import java.io.*;
import java.util.*;
import java.sql.*;
import org.slf4j.*;

public class Tracker extends Item  {
	private Connection connection;
	private static final Logger LOG = LoggerFactory.getLogger(Tracker.class);
	private static final Random RN = new Random();
	private List<Item> items = new ArrayList<>();
    private static final String CONFIG = "bd/config.properties";

    public Item add(Item item) {
		try (SQLConn sqlConn = new SQLConn(CONFIG)) {
            PreparedStatement st = connection.prepareStatement("INSERT INTO items(name, description, author) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, item.getName());
            st.setString(2, item.getDescription());
            st.setString(3, item.getAuthor());
            st.executeUpdate();
			ResultSet generatedKeys = st.getGeneratedKeys();
			if (generatedKeys.next()) {
				item.setId(String.valueOf(generatedKeys.getInt(1)));
			}
			st.close();
		} catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
		return item;
	}

	public void replace(String id, Item item) {
		if (findById(id) != Item.EMPTY) {
			for (int index = 0; index != this.items.size(); index++) {
				if (this.items.get(index) != null && this.items.get(index).getId().equals(id)) {
					this.items.set(index, item);
					break;
				}
			}
		}
	}

	public void edit(Item fresh) {
        try (SQLConn sqlConn = new SQLConn(CONFIG)) {
            PreparedStatement st = connection.prepareStatement("UPDATE items SET name = ?, description = ? WHERE id = ?");
            st.setString(1, fresh.getName());
            st.setString(2, fresh.getDescription());
            st.setInt(3, Integer.parseInt(fresh.getId()));
            st.executeUpdate();
			st.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
	}

	public void delete(String id) {
		try (SQLConn sqlConn = new SQLConn(CONFIG)) {
		PreparedStatement st = connection.prepareStatement("DELETE FROM items WHERE id = ?");
		st.setInt(1, Integer.parseInt(id));
		st.executeUpdate();
		st.close();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public List<Item> getAll() {
		ArrayList<Item> list = new ArrayList<>();
		try (SQLConn sqlConn = new SQLConn(CONFIG)) {
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM items");
		while (rs.next())		{
			list.add(new Item(String.valueOf(rs.getInt("id")),
					rs.getString("name"),
					rs.getString("description"),
					rs.getString("author"))
			);
		}
		rs.close();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return list;
	}

	protected Item findById(String id) {
    	Item item = Item.EMPTY;
		try (SQLConn sqlConn = new SQLConn(CONFIG)) {
			PreparedStatement st = connection.prepareStatement("SELECT * FROM items WHERE id = ?");
			st.setInt(1, Integer.parseInt(id));
			ResultSet rs = st.executeQuery();
			if (rs.next())		{
				item.setId(String.valueOf(rs.getInt("id")));
				item.setName(rs.getString("name"));
				item.setDescription(rs.getString("description"));
				item.setAuthor(rs.getString("author"));
			}
			rs.close();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return item;
	}

	protected Item findByName(String name) {
		Item item = Item.EMPTY;
		try (SQLConn sqlConn = new SQLConn(CONFIG)) {
			PreparedStatement st = connection.prepareStatement("SELECT * FROM items WHERE name = ?");
			st.setString(1, name);
			ResultSet rs = st.executeQuery();
			if (rs.next())		{
				item.setId(String.valueOf(rs.getInt("id")));
				item.setName(rs.getString("name"));
				item.setDescription(rs.getString("description"));
				item.setAuthor(rs.getString("author"));
			}
			rs.close();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return item;
	}

    private class SQLConn implements AutoCloseable {
        public SQLConn(String config) {
            try {
                Properties props = new Properties();
                props.load(new FileInputStream(this.getClass().getClassLoader().getResource(config).getPath()));
                String dburl = props.getProperty("dburl");
                String user = props.getProperty("user");
                String password = props.getProperty("password");
                connection = DriverManager.getConnection(dburl, user, password);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }

        @Override
        public void close() throws Exception {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
            connection = null;
        }
    }
}