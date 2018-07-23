package list;

public class Node<T> {
    T value;
    Node<T> next;

    Node(T date) {
        this.value = value;
    }

    public boolean hasCycle(Node first) {
        boolean result = false;
        if (first != null) {
            Node<T> current = first;
            while (current.next != null) {
                if (current.equals(first)) {
                    result = true;
                    break;
                }
                current = current.next;
            }
        }
        return result;
    }
}
