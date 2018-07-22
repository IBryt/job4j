package list;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class LinkedContainerTest {
    private LinkedContainer<Integer> list;

    @Before
    public void init() {
        list = new LinkedContainer<>();
        list.add(1);
        list.add(2);
        list.add(3);
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