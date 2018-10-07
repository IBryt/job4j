package buffer;

import blocking.SimpleBlockingQueue;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class ParallelSearch {
    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(queue.poll());
                        if (!Thread.currentThread().isInterrupted()) {
                            break;
                        }
                    }
                }
        );

        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    consumer.interrupt();
                }

        ).start();
        consumer.start();

    }
}