package generic;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class SimpleArray<T> implements Iterable<T> {

    private T[] objects;
    private int position = 0;

    public SimpleArray(int capacity) {
        this.objects = (T[]) new Object[capacity];
    }

    public void add(T model) {
        this.objects[position++] = model;
    }

    public boolean set(int index, T model) {
        rangeCheck(index);
        T old = objects[index];
        this.objects[index] = model;
        return !old.equals(model);
    }

    private void rangeCheck(int index) {
        if (index >= position) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + position;
    }


    public boolean delete(int index) {
        boolean result = false;
        if (position >= index) {
            System.arraycopy(this.objects, index + 1, this.objects, index, this.objects.length - 1 - index);
            position--;
            result = true;
        }
        return result;
    }

    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < position; i++) {
                if (objects[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < position; i++) {
                if (o.equals(objects[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    public T get(int index) {
        return objects[index];
    }

    public int size() {
        return position;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor != position;
            }

            @Override
            public T next() {
                if (cursor >= position) {
                    throw new NoSuchElementException();
                }
                return objects[cursor++];
            }

            @Override
            public void forEachRemaining(Consumer<? super T> action)  {
                while (hasNext()) {
                    action.accept(next());
                }
            }
        };
    }
}
