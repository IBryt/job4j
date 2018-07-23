package set;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleSetTest {
    private SimpleSet<Integer> set;

    @Before
    public void init() {
        set = new SimpleSet<>(10);
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(3);
        set.add(3);
    }

    @Test
    public void whenGetElementForEachRemainingThenGetUniqueElements() {
        int count = 1;
        for (int value : set) {
            assertThat(value, is(count++));
        }
    }
}