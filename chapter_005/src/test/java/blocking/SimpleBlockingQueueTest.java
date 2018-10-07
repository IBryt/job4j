package blocking;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

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
                queue.offer(1);
                queue.offer(2);
                queue.offer(3);
                queue.offer(4);
            }
        };

        customer = new Thread() {
            @Override
            public void run() {
                queue.poll();
                queue.poll();
                queue.poll();
            }
        };
    }

    @Test
    public void whenSuccessivelyOfferAndPollEntry() throws InterruptedException {
        producer.start();
        customer.start();
        producer.join();
        customer.join();
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.size(), is(1));
        assertThat(queue.poll(), is(4));
        assertThat(queue.isEmpty(), is(true));
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            queue::offer
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        buffer.add(queue.poll());
                        if (!Thread.currentThread().isInterrupted()) {
                            break;
                        }
                        Thread.currentThread().interrupt();
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}