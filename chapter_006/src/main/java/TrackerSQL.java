
import org.slf4j.*;
import ru.job4j.models.Item;
import ru.job4j.start.ITracker;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class TrackerSQL implements ITracker, Cloneable {
    private Connection connection;
    private static final Logger LOG = LoggerFactory.getLogger(TrackerSQL.class);
    private static final Random RN = new Random();
    private List<Item> items = new ArrayList<>();
    private static final String CONFIG = "bd/app.properties";

    public Item add(Item item) {
        try (TrackerSQL.SQLConn sqlConn = new TrackerSQL.SQLConn()) {
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
        try (TrackerSQL.SQLConn sqlConn = new TrackerSQL.SQLConn()) {
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
        try (TrackerSQL.SQLConn sqlConn = new TrackerSQL.SQLConn()) {
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
        try (TrackerSQL.SQLConn sqlConn = new TrackerSQL.SQLConn()) {
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

    public Item findById(String id) {
        Item item = Item.EMPTY;
        try (TrackerSQL.SQLConn sqlConn = new TrackerSQL.SQLConn()) {
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

    public Item findByName(String name) {
        Item item = Item.EMPTY;
        try (TrackerSQL.SQLConn sqlConn = new TrackerSQL.SQLConn()) {
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
        public SQLConn() {
            try (InputStream in = TrackerSQL.SQLConn.class.getClassLoader().getResourceAsStream(CONFIG)) {
                Properties config = new Properties();
                config.load(in);
                Class.forName(config.getProperty("driver-class-name"));
                connection = DriverManager.getConnection(
                        config.getProperty("url"),
                        config.getProperty("user"),
                        config.getProperty("password")
                );
            } catch (Exception e) {
                throw new IllegalStateException(e);
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
