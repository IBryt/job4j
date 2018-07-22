package list;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DynamicListTest {

    private DynamicList<Integer> list;
    @Before
    public void init() {
        this.list = new DynamicList<>(2);
        this.list.add(1);
        this.list.add(2);
        this.list.add(3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenGetNoExistElementThenShouldThrowIndexOutOfBoundsException() {
        this.list.get(46);
    }

    @Test
    public void  whenGetThirdElementThenReturnValueThree() {
        assertThat(this.list.get(2), is(3));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void shouldThrowConcurrentModificationException() {
        for (Integer i : this.list) {
            this.list.add(5);
        }
    }

    @Test
    public void whenGetElementForEachRemainingThenConsistentlyGetElements() {
        int count = 1;
        for (Integer i : this.list) {
            assertThat(i, is(count++));
        }
    }
}