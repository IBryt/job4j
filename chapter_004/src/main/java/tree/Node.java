package tree;

import java.util.*;

/**
 * @author Petr Arsentev (mailto:parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Node<E extends Comparable<E>> {
    private final List<Node<E>> children = new ArrayList<>();
    private final E value;
    private final E parent;

    public Node(final E value, E parent) {
        this.value = value;
        this.parent = parent;
    }

    public void add(Node<E> child) {
        this.children.add(child);
    }

    public List<Node<E>> leaves() {
        return this.children;
    }

    public boolean eqValue(E that) {
        return this.value.compareTo(that) == 0;
    }

    public E getValue() {
        return value;
    }

    public E getParent() {
        return parent;
    }
}
