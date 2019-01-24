import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SwitcherTest {
    private static final int SIZE = 4;
    private Thread t1;
    private Thread t2;
    private Switcher switcher;
    private AtomicInteger count = new AtomicInteger(0);
    private AtomicBoolean block1;
    private AtomicBoolean block2;


    @Before
    public void init() {
        switcher = new Switcher();
        block1 = new AtomicBoolean(false);
        block2 = new AtomicBoolean(true);
    }

    @Test
    public void whenAddedTenValuesThenSwitchSecondThread() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        t1 = new Thread() {

            @Override
            public void run() {
                try {
                    test(1, lock, block1, block2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t2 = new Thread() {
            @Override
            public void run() {
                try {
                    test(2, lock, block2, block1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        String expected = "1111111111222222222211111111112222222222";
        assertThat(expected, is(switcher.getValue().toString()));
    }

    private void test(int value, ReentrantLock lock, AtomicBoolean current, AtomicBoolean second) throws InterruptedException {
        while (count.get() < SIZE) {
            while (!lock.isLocked()
                    && !current.get()
                    && lock.tryLock()) {
                count.incrementAndGet();
                if (count.get() > SIZE) {
                    break;
                }
                for (int i = 0; i < 10; i++) {
                    switcher.addValue(value);
                }
                current.set(true);
                second.set(false);
                lock.unlock();
                break;
            }
        }
    }
}