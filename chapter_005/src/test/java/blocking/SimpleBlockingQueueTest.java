package blocking;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {
    private SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue();
    private Thread producer;
    private Thread customer;

    @Before
    public void init() {
        producer = new Thread() {
            @Override
            public void run() {
                try {
                    queue.offer(1);
                    queue.offer(2);
                    queue.offer(3);
                    queue.offer(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        customer = new Thread() {
            @Override
            public void run() {
                try {
                    queue.poll();
                    queue.poll();
                    queue.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Test
    public void test() throws InterruptedException {
        producer.start();
        customer.start();
        producer.join();
        customer.join();
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.size(), is(1));
        assertThat(queue.poll(), is(4));
        assertThat(queue.isEmpty(), is(true));
    }
}