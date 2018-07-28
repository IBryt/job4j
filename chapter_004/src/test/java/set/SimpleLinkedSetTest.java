package set;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleLinkedSetTest {

    private SimpleLinkedSet<String> set;

    @Before
    public void init() {
        set = new SimpleLinkedSet<>();
        set.add("1");
        set.add("2");
    }

    @Test
    public void whenAddUniqueThenReturnTrue() {

        assertThat(set.add("3"), is(true));
    }

    @Test
    public void whenAddNotUniqueThenReturnTrue() {
        assertThat(set.add("2"), is(false));
    }

}