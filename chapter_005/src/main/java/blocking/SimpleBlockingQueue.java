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
    private final Queue<T> queue = new LinkedList<>();

    public synchronized T poll() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        T res = queue.poll();
        notify();
        return res;
    }

    public synchronized void offer(T value) {
        while (size() == MAX_SIZE) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
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