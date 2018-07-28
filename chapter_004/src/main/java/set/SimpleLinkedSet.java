package set;

import list.LinkedContainer;
import java.util.Iterator;

public class SimpleLinkedSet<E> implements Iterable<E> {
    private LinkedContainer<E> link = new LinkedContainer<>();

    public boolean add(E e) {
        boolean found = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (it.next().equals(e)) {
                found = true;
                break;
            }
        }
        return found ? !found : link.add(e);
    }

    @Override
    public Iterator<E> iterator() {
        return link.iterator();
    }
}
