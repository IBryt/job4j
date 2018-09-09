package tree;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Petr Arsentev (mailto:parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class TreeTest {
    private Tree<Integer> tree;

    @Before
    public void init() {
        tree = new Tree<>(1);
    }

    @Test
    public void when6ElFindLastThen6() {
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(
                tree.findBy(6).isPresent(),
                is(true)
        );
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        tree.add(1, 2);
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
    }

    @Test
    public void whenTreeIsBinaryThenReturnTrue() {
        tree.add(1, 2);
        tree.add(1, 3);
        assertThat(
                tree.isBinary(),
                is(true)
        );
    }

    @Test
    public void whenTreeNotIsBinaryThenReturnFalse() {
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        assertThat(
                tree.isBinary(),
                is(false)
        );
    }
}