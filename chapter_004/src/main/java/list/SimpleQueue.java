package list;

public class SimpleQueue<T> extends AbstractUnidirectionalList<T> {

    private Node<T> last;

    public void push(T value) {
        Node<T> newLink = new Node(value);
        if (isEmpty()) {
            first = newLink;
        } else {
            last.next = newLink;
        }
        last = newLink;
        size++;
    }
}
