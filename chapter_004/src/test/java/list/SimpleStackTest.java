package list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class SimpleStackTest {

    private SimpleStack<Integer> stack;
    @Before
    public void init() {
        stack = new SimpleStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
    }

    @Test
    public void whenPollAllElementAndPollFromEmptyThenReturnTreeTwoOneAndNull() {
        assertThat(stack.poll(), is(3));
        assertThat(stack.poll(), is(2));
        assertThat(stack.poll(), is(1));
        assertThat(stack.poll(), is(nullValue()));
    }

}