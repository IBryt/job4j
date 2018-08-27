package map;

import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;

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
}