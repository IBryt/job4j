package storage;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    private UserStorage storage;
    private User u1;
    private User u2;

    @Before
    public void init() {
        storage = new UserStorage();
        u1 = new User(1, 100);
        u2 = new User(2, 100);
        storage.add(u1);
        storage.add(u2);
    }

    @Test
    public void whenAddUserContainsInMapReturnFalse() {
        assertThat(storage.add(new User(1, 100)), is(false));
    }

    @Test
    public void whenAddUserNoContainsInMapReturnTrue() {
        assertThat(storage.add(new User(3, 100)), is(true));
    }

    @Test
    public void whenUpdateEntryWithDifferentValueReturnTrue() {
        assertThat(storage.update(new User(1, 1200)), is(true));
    }

    @Test
    public void whenUpdateEntryWithEqualsValueReturnFalse() {
        assertThat(storage.update(new User(1, 100)), is(false));
    }

    @Test
    public void whenDeleteNoExistUserReturnFalse() {
        assertThat(storage.delete(new User(3, 100)), is(false));
    }

    @Test
    public void whenDeleteExistUserReturnTrue() {
        assertThat(storage.delete(new User(1, 100)), is(true));
    }

    @Test
    public void whenAmountIsMoreThanAmountSrcThenTransferReturnFalse() {
        assertThat(storage.transfer(1, 2, 400), is(false));
    }

    @Test
    public void whenAmountIsLessOrEqualsThanAmountSrcThenTransferReturnTrue() {
        assertThat(storage.transfer(1, 2, 100), is(true));
    }
}