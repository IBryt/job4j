package pool;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import static org.hamcrest.Matchers.containsInAnyOrder;

import static org.junit.Assert.*;
public class ThreadPoolTest {
    private ThreadPool pool;
    private Queue<Integer> queue =  new ArrayBlockingQueue(100);

    @Before
    public void init() {
        pool = new ThreadPool();
    }

    @Test
    public void whenAdded4ValueInPoolThenQueueContainsThisValue() throws InterruptedException {
        addExecute(1);
        addExecute(2);
        addExecute(3);
        addExecute(4);
        pool.shutdown();
        assertThat(Arrays.asList(1, 2, 3, 4), containsInAnyOrder(queue.toArray()));
    }
    @Test
    public void whenAfterShutdownValueNotAdded() throws InterruptedException {
        addExecute(1);
        addExecute(2);
        pool.shutdown();
        Thread.yield();
        addExecute(3);
        addExecute(4);
        assertThat(Arrays.asList(1, 2), containsInAnyOrder(queue.toArray()));
    }

    private void addExecute(int value) {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                queue.add(value);
            }
        });
    }
}