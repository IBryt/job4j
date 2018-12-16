package magnit;

import magnit.models.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class StoreSQL implements AutoCloseable, Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(StoreSQL.class);
    private Config config;
    private Connection connection;

    public StoreSQL(Config config) throws SQLException {
        this.config = config;
        if (!init()) {
            throw new SQLException("Connection is not created!");
        }
    }

    private void createConnection() throws SQLException {
        DriverManager.getDrivers();
        connection = DriverManager.getConnection(
                config.get("url"),
                config.get("username"),
                config.get("password")
        );
    }

    private boolean init() throws SQLException {
        String url  = config.get("url");
        try (Connection connection = DriverManager.getConnection(url)) {
           try (Statement statement = connection.createStatement()) {
               statement.execute("CREATE TABLE IF NOT EXISTS entry (field INTEGER)");
           }
        }
        createConnection();
        return this.connection != null;
    }

    public void generate(int n) throws SQLException {
        clearTable();
        fillTable(n);
    }

    public List<Entry> getListEntry() throws SQLException {
        List<Entry> list = new LinkedList<>();
        try (Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery("SELECT * FROM entry")) {
                while (rs.next()) {
                    int value = rs.getInt("field");
                    list.add(new Entry(value));
                }
            }
        }
        return list;
    }

    private void fillTable(int n) throws SQLException {
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO entry(field) VALUES (?)")) {
            //connection.getMetaData().supportsSavepoints();
            //connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            for (int i = 1; i <= n; i++) {
                statement.setInt(1, i);
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            LOG.error(e.getMessage(), e);
        }
        connection.setAutoCommit(true);
    }

    private void clearTable() throws SQLException {
        connection.setAutoCommit(false);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM entry");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            LOG.error(e.getMessage(), e);
        }
        connection.setAutoCommit(true);
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
