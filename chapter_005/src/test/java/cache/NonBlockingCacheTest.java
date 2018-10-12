package cache;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class NonBlockingCacheTest {
    private NonBlockingCache cache;
    private volatile Base model;
    @Before
    public void init() {
        cache = new NonBlockingCache();
        model = new Base(1);
    }
    @Test
    public void whenAddedNewModelNoExistInMapReturnTrue() {
        assertThat(cache.add(model), is(true));
    }

    @Test(expected = RuntimeException.class)
    public void whenAddedNewModelExistInMapReturnOptimisticException() {
        cache.add(model);
        cache.add(model);
    }

    @Test
    public void whenUpdateModelExistInMapReturnTrue() throws InterruptedException {
        cache.add(model);
        int version = model.getVersion();
        boolean res = cache.update(model);
        assertThat(res, is(true));
        assertThat(version + 1, is(model.getVersion()));
    }

    @Test
    public void whenDeleteModelExistInMapReturnTrue() throws InterruptedException {
        cache.add(model);
        boolean res = cache.delete(model);
        assertThat(res, is(true));
    }

/*    @Test
    public void whenThrowException() throws InterruptedException {
        AtomicReference<Exception> ex = new AtomicReference<>();
        Thread thread = new Thread(
                () -> {
                    try {
                        throw new RuntimeException("Throw Exception in Thread");
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        thread.start();
        thread.join();
        Assert.assertThat(ex.get().getMessage(), is("Throw Exception in Thread"));
    }*/

    @Test
    public void whenThrowException2() throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    throw new RuntimeException("Throw Exception in Thread");
                }
        );
        thread.start();
        thread.join();
    }

}