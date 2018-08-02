package list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractUnidirectionalList<T> implements Iterable<T> {
    Node<T> first;
    int size = 0;

    public T poll() {
        final Node<T> f = first;
        return (f == null) ? null : unlinkFirst(f);
    }

    private T unlinkFirst(Node<T> f) {
        final T element = f.value;
        f.value = null;
        this.first = f.next;
        size--;
        return element;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public abstract void push(T value);

    @Override
    public Iterator<T> iterator() {

        return new Iterator<T>() {
            private Node<T> current = first;
            private int index = 0;
            private int expectedModCount = size;

            @Override
            public boolean hasNext() {
                return index != size;
            }

            @Override
            public T next() {
                if (index >= size) {
                    throw new NoSuchElementException();
                }
                if (expectedModCount != size) {
                    throw new ConcurrentModificationException();
                }
                Node<T> old = current;
                current = current.next;
                index++;
                return old.value;
            }
        };
    }
}
