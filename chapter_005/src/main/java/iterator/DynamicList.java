package iterator;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;
@ThreadSafe
public class DynamicList<E> implements Iterable<E> {
    @GuardedBy("this")
    private final ArrayList<E> array = new ArrayList<>();

    @Override
    public synchronized Iterator<E> iterator() {
        return copy(this.array).iterator();
    }

    private List<E> copy(final List<E> list) {
       return Collections.synchronizedList(list);
    }

    public synchronized boolean add(E e) {
        return array.add(e);
    }

    public synchronized E get(int index) {
        return array.get(index);
    }
}
