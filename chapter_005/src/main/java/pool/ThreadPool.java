package pool;

import blocking.SimpleBlockingQueue;

import java.util.*;
import java.util.concurrent.Executor;

public class ThreadPool implements Executor {
    private final List<Thread> threads = new LinkedList<>();
    private volatile SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private volatile boolean isRunning = true;
    private final Object lock = new Object();

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(new TaskWorker());
            thread.start();
            threads.add(thread);
        }
    }

    public void shutdown() {
        isRunning = false;
    }

    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            tasks.offer(command);
        }
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    private final class TaskWorker implements Runnable {

        @Override
        public void run() {
            lock();
            while (isRunning) {
                Runnable nextTask = tasks.poll();
                if (nextTask != null) {
                    nextTask.run();
                }
                if (!isRunning) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void lock() {
            while (tasks.isEmpty()) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}