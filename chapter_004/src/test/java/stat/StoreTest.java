package stat;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StoreTest {
    private Store store;
    private List<User> previous;
    private List<User>  current;
    private Map<String, Integer> diff;

    @Before
    public void init() {
        store = new Store();
        previous = new ArrayList<>();
        previous.add(new User(1, "1")); //unchanged
        previous.add(new User(2, "1")); //changed
        previous.add(new User(3, "2")); //changed
        previous.add(new User(4, "1")); //delete
        current = new ArrayList<>();
        current.add(new User(1, "1")); //unchanged
        current.add(new User(2, "3")); //changed
        current.add(new User(3, "3")); //changed
        current.add(new User(5, "3")); //added
        current.add(new User(6, "3")); //added
        diff = store.mapDiff(previous, current);
    }

    @Test
    public void test() {
        //System.out.println(diff);
        Map<String, Integer> map = new HashMap<>(3, 1);
        map.put("changed",   2);
        map.put("deleted",   1);
        map.put("added",     2);
        assertThat(diff.equals(map), is(true));
    }

}