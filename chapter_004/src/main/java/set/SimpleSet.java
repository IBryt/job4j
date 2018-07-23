package set;

import generic.SimpleArray;

import java.util.Iterator;

public class SimpleSet<E> implements Iterable<E> {

    SimpleArray<E> array;

    public SimpleSet(int capacity) {
        array = new SimpleArray<>(capacity);
    }

    public void add(E e) {
        if (array.indexOf(e) == -1) {
            array.add(e);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return array.iterator();
    }
}
