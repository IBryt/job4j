package iterator;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;
@ThreadSafe
public class DynamicList<E> implements Iterable<E> {
    @GuardedBy("this")
    private final ArrayList<E> array;

    public DynamicList(ArrayList<E> array) {
        this.array = array;
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return copy(this.array).iterator();
    }

    private List<E> copy(final List<E> list) {
       return Collections.synchronizedList(list);
    }
}
