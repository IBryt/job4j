import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.models.Item;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TrackerSQLTest {
    private Item item;
    private Connection connection;

    public Connection init() throws Exception {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("db/app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("db.driver"));
            return DriverManager.getConnection(
                    config.getProperty("db.url"),
                    config.getProperty("db.username"),
                    config.getProperty("db.password")
            );
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    @Before
    public void before() throws Exception {
        item = new Item("name", "desc", "author");
        connection = ConnectionRollback.create(this.init());
    };

    @Test
    public void createItem() throws IOException {
        try (TrackerSQL tracker = new TrackerSQL(connection)) {
            tracker.add(item);
            assertThat(tracker.findByName("name"), is(item));
        }
    }

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() throws IOException {
        try (TrackerSQL tracker = new TrackerSQL(connection)) {
            tracker.add(item);
            assertThat(tracker.getAll().get(0), Is.is(item));
        }
    }

    @Test
    public void whenEditNameThenReturnNewName() throws IOException {
        try (TrackerSQL tracker = new TrackerSQL(connection)) {
            tracker.add(item);
            Item next = new Item("test2", "testDescription2");
            next.setId(item.getId());
            tracker.edit(next);
            assertThat(tracker.findById(item.getId()).getName(), Is.is("test2"));
        }
    }
    @Test
    public void whenGetAllItemsThemReturnArrayWithItemsNotEqualsNull() throws IOException {
        try (TrackerSQL tracker = new TrackerSQL(connection)) {
            tracker.add(item);
            List<Item> expected = new ArrayList<>(Arrays.asList(item));
            assertThat(tracker.getAll(), Is.is(expected));
        }
    }
    @Test
    public void whenDeleteFromArrayItemsThenArrayIsOneLess() throws IOException {
        try (TrackerSQL tracker = new TrackerSQL(connection)) {
            tracker.add(item);
            Item delete = new Item("test2", "testDescription");
            tracker.add(delete);
            tracker.delete(delete.getId());
            List<Item> expected = new ArrayList<>(Arrays.asList(item));
            assertThat(tracker.getAll(), Is.is(expected));
        }
    }

    @Test
    public void whenFindExistItemByIdThemReturnFoundItem() throws IOException {
        try (TrackerSQL tracker = new TrackerSQL(connection)) {
            tracker.add(item);
            assertThat(tracker.findById(item.getId()), Is.is(item));
        }
    }
    @Test
    public void whenFindNoExistItemByIdThemReturnEmptyItem() throws IOException {
        try (TrackerSQL tracker = new TrackerSQL(connection)) {
            assertThat(tracker.findById("-1"), Is.is(Item.EMPTY));
        }
    }
    @Test
    public void whenFindExistItemByNameThemReturnFoundItem() throws IOException {
        try (TrackerSQL tracker = new TrackerSQL(connection)) {
            tracker.add(item);
            assertThat(tracker.findByName(item.getName()), Is.is(item));
        }
    }
    @Test
    public void whenFindNoExistItemByNameThemReturnEmptyItem() throws IOException {
        try (TrackerSQL tracker = new TrackerSQL(connection)) {
            tracker.add(item);
            assertThat(tracker.findByName("EmptyValue"), Is.is(Item.EMPTY));
        }
    }
}

