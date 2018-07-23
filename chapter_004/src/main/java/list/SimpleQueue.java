package list;

public class SimpleQueue<T> extends LinkedContainer<T> {
    public T poll() {
        final Node<T> f = first;
        return (f == null) ? null : unlinkFirst(f);
    }

    protected T unlinkFirst(Node<T> f) {
        final T element = f.item;
        f.item = null;
        this.first = f.next;
        size--;
        return element;
    }

    public void push(T value) {
        add(value);
    }
}
