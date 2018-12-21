package pool;

import blocking.SimpleBlockingQueue;

import java.util.*;
import java.util.concurrent.Executor;

public class ThreadPool implements Executor {
    private final List<Thread> threads = new LinkedList<>();
    private volatile SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();
    private volatile boolean isRunning = true;

    public ThreadPool() {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            Thread thread = new Thread(new TaskWorker());
            thread.start();
            threads.add(thread);
        }
    }

    public void shutdown() {
        isRunning = false;
        while (!close()) {
            try {
                Thread.currentThread().sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean close() {
        boolean res = true;
        for (Thread thread : threads) {
            thread.interrupt();
            if (thread.isInterrupted()) {
                res = false;
                break;
            }
        }
        return res;
    }

    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            tasks.offer(command);
        }
    }

    private final class TaskWorker implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Runnable nextTask = tasks.poll();
                    nextTask.run();
                } catch (InterruptedException e) {
                   Thread.currentThread().interrupt();
                }
            }
            Thread.currentThread().interrupt();
        }
    }
}