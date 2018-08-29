package map;

import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserTest {

    private User u1;
    private User u2;

    @Before
    public void init() {
        u1 = new User("name", 1, new GregorianCalendar());
        u2 = new User("name", 1, new GregorianCalendar());
    }

    @Test
    public void whenCallMapUsersReturnMessage() {
        new User().mapUsers(u1, u2);
    }

    @Test
    public void whenChangeFieldKeyAndFindByTheKeyReturnNull() {
        Map map = new HashMap<User, String>();
        map.put(u1, "test");
        u1.setChildren(2);
        assertThat(map.get(u1), is(nullValue()));
    }
}