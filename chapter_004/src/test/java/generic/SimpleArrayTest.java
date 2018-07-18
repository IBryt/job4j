package generic;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleArrayTest {

    private SimpleArray<Number> simpleArray;
    private Iterator<Number> it;


    @Before
    public void start() {
        this.simpleArray = new SimpleArray(3);
        it = this.simpleArray.iterator();
    }

    @Test
    public void whenAddValueThenFirstValueHas3() {
        this.simpleArray.add(3);
        assertThat(this.simpleArray.get(0), is(3));
    }

    @Test
    public void whenSetValueThenFirstValueHas3() {
        this.simpleArray.add(2);
        this.simpleArray.set(0, 3);
        assertThat(this.simpleArray.get(0), is(3));
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void whenShouldThrowIndexOutOfBoundsException() {
        this.simpleArray.set(0, 3);
    }

    @Test
    public void whenDeleteFromArrayItemsThenArrayIsOneLess() {
        this.simpleArray.add(2);
        this.simpleArray.add(3);
        this.simpleArray.delete(0);
        assertThat(this.simpleArray.size(), is(1));
        assertThat(this.simpleArray.get(0), is(3));
    }

    @Test
    public void hasNextNextSequentialInvocation() {
        this.simpleArray.add(1);
        this.simpleArray.add(2);
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
    }

    @Test
    public void testsThatNextMethodDoesntDependsOnPriorHasNextInvocation() {
        this.simpleArray.add(1);
        this.simpleArray.add(2);
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
    }

    @Test
    public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
        this.simpleArray.add(1);
        this.simpleArray.add(2);
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementException() {
        it.next();
    }
}