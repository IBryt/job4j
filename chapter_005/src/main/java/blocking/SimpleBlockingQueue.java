package blocking;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @SuppressWarnings("StaticGuardedByInstance")
    @GuardedBy("this")
    private final static int MAX_SIZE = 2;
    private Queue<T> queue = new LinkedList<>();

    public synchronized T poll() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        T res = queue.poll();
        notify();
        return res;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (size() == MAX_SIZE) {
            wait();
        }
        queue.offer(value);
        notify();
    }

    public synchronized int size() {
        return queue.size();
    }

    public synchronized boolean isEmpty() {
        return size() == 0;
    }
}