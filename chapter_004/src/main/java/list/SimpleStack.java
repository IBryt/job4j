package list;

public class SimpleStack<T> extends AbstractUnidirectionalList<T> {

    @Override
    public void push(T value) {
        Node<T> newLink = new Node<>(value);
        newLink.next = this.first;
        this.first = newLink;
        this.size++;
    }
}
