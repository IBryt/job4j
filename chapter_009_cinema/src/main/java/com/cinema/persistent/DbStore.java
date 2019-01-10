package com.cinema.persistent;

import com.cinema.model.Hall;
import com.cinema.model.Tickets;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.InputStream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class DbStore implements Store<Hall> {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DbStore INSTANCE = new DbStore();
    private static final String CONFIG = "application.properties";
    private static final Logger LOG = LogManager.getLogger(DbStore.class.getName());

    public static Store<Hall> getInstance() {
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
    public List<Hall> getPlaceHall() {
        List<Hall> halls = new ArrayList<>();
        try (Connection connection = SOURCE.getConnection()) {
            try (Statement st = connection.createStatement()) {
                String query = "SELECT \n"
                        + "\thall.id,\n"
                        + "\thall.name,\n"
                        + "\thall.row,\n"
                        + "\thall.place,\n"
                        + "\tCASE WHEN tickets.hall_id is null THEN false\n"
                        + "\t\tELSE true\n"
                        + "\tEND as occupied\n"
                        + "\tFROM hall as hall\n"
                        + "\tleft join tickets as tickets on hall.id = tickets.hall_id\n"
                        + "ORDER BY\n"
                        + "\trow,\n"
                        + "\tplace";
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    halls.add(
                            new Hall(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getInt("row"),
                                    rs.getInt("place"),
                                    rs.getBoolean("occupied")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return halls;
    }

    @Override
    public Tickets addTicket(int idHall, String name, String phone) {
        return null;
    }
}
