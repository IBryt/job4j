package ru.job4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.model.Entries;
import ru.job4j.model.Entry;
import ru.job4j.storage.Config;
import ru.job4j.storage.StoreSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLExecute {
    private static final Logger LOG = LoggerFactory.getLogger(SQLExecute.class);

    public void execute() {
        Entries parse = new Parsing().execute();
        try (StoreSQL sql = new StoreSQL(new Config()) {
            @Override
            public void execute() throws SQLException {
                try {
                    clear(getConnection());
                    fill(getConnection(), parse);
                } catch (SQLException ex) {
                    getConnection().rollback();
                }
            }
        }) {
            sql.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void clear(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM vacancy");
            connection.commit();
        }
        LOG.info("очистка выполнена");
    }

    private void fill(Connection connection, Entries parse) throws SQLException {
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO vacancy(id, name, text, link) VALUES ((?),(?),(?),(?))")) {
            for (int i = 0; i < parse.getEntries().size(); i++) {
                Entry e = parse.getEntries().get(i);
                statement.setInt(1, e.getId());
                statement.setString(2, e.getName());
                statement.setString(3, e.getText());
                statement.setString(4, e.getLink());
                if (i % 100 == 0) {
                    statement.executeBatch();
                    statement.clearBatch();
                }
                statement.addBatch();
                LOG.info(e.toString());
            }
            statement.executeBatch();
            connection.commit();
        }
    }
}
