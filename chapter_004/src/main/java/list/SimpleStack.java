package list;

public class SimpleStack<T> extends SimpleArrayList<T> {

    public T poll() {
        final Node<T> f = first;
        return (f == null) ? null : unlinkFirst(f);
    }

    public void push(T value) {
        add(value);
    }
}
