package map;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ContainerMapTest {

    private ContainerMap<User, String> container;
    private User u1;
    private User u2;
    private Map.Entry<User, String> entry;

    @Before
    public void init() {
        container = new ContainerMap<>(1);
        u1 = new User("name", 1, new GregorianCalendar());
        u2 = new User("name", 2, new GregorianCalendar());
        container.insert(u1, "test1");
    }

    @Test
    public void whenInsertKeyAddedEarlierThenReturnAddedValueAndNoChangedSize() {
        container.insert(u1, "test2");
        assertThat(container.size(), is(1));
        assertThat(container.get(u1), is("test2"));
    }

    @Test
    public void whenInsertThenReturnValue() {
        assertThat(container.get(u1), is("test1"));
    }

    @Test
    public void whenGetElementExistInMapThenReturnValue() {
        assertThat(container.get(u1), is("test1"));
    }

    @Test
    public void whenGetElementNoExistInMapThenReturnNull() {
        assertThat(container.get(u2), is(nullValue()));
    }

    @Test
    public void whenGetElementExistInMapAndChangeValueFildThenReturnNull() {
        u1.setChildren(6);
        assertThat(container.get(u1), is(nullValue()));
    }

    @Test
    public void whenDeleteElementExistInMapThenReturnTrue() {
        assertThat(container.delete(u1), is(true));
    }

    @Test
    public void whenDeleteElementNoExistInMapThenReturnFalse() {
        assertThat(container.delete(u2), is(false));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenAddingTwoNoEqualsKeyAndSameHashcodeThenExpectedUnsupportedOperationException() {
        ContainerMap<String, String> con = new ContainerMap<>();
        String v1 = "FB";
        String v2 = "Ea";
        assertThat(v1.hashCode() == v2.hashCode(), is(true));
        con.insert(v1, v1);
        con.insert(v2, v2);
    }

    @Test()
    public void whenMapOneEntryReturnKeyValue() {
        Iterator<Map.Entry<User, String>> iterator = container.entrySet().iterator();
        entry = iterator.next();
        assertThat(entry.getKey(), is(u1));
        assertThat(entry.getValue(), is("test1"));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenCallMethodNextMoreSizeMapReturnNoSuchElementException() {
        Iterator<Map.Entry<User, String>> iterator = container.entrySet().iterator();
        entry = iterator.next();
        entry = iterator.next();
    }
    @Test(expected = ConcurrentModificationException.class)
    public void whenInsertEntryDuringIterationReturnConcurrentModificationException() {
        Iterator<Map.Entry<User, String>> iterator = container.entrySet().iterator();
        container.insert(u2, "test");
        iterator.next();
    }
}