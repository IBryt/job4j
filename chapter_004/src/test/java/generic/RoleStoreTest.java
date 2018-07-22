package generic;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RoleStoreTest {
    private RoleStore<Role> store;
    private Role role;

    @Before
    public void start() {
        store = new RoleStore(new SimpleArray(10));
        role = new Role("1");
        store.add(role);
    }

    @Test
    public void whenAddNewElementThenArrayWillByMoreByOne() {
        //store.add(new User("2"));
        store.add(new Role("2"));
        assertThat(store.getArrayBase().size(), is(2));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenTryReplaceNonExistElementThenShouldThrowNoSuchElementException() {
        store.replace("2", new Role("3"));
   }

    @Test
    public void whenReplaceExistElementThenChangeSecondElement() {
        store.add(new Role("2"));
        Role expected = new Role("3");
        boolean result = store.replace("2", expected);
        assertThat(true, is(result));
        assertThat(store.getArrayBase().get(1), is(expected));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenDeleteNonExistIdThenShouldThrowNoSuchElementException() {
        store.delete("57");
    }

    @Test
    public void whenDeleteNonExistIdThenReturnTrue() {
        boolean result = store.delete("1");
        assertThat(result, is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenDeleteAndCantBeFindedElementThenShouldThrowNoSuchElementException() {
        store.delete("57");
    }

    @Test
    public void whenDeleteElementThenArrayIsOneLess() {
        store.add(new Role("2"));
        boolean result = store.delete("2");
        assertThat(result, is(true));
        assertThat(store.getArrayBase().size(), is(1));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenCantBeFindedElementThenShouldThrowNoSuchElementException() {
        store.findById("q");
    }

    @Test
    public void whenCanBeFindedElementThenReturnObject() {
        Role result = store.findById("1");
        assertThat(result, is(role));
    }
}