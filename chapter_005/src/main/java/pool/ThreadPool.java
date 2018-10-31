package pool;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class ThreadPool implements Executor {
    private final List<Thread> threads = new LinkedList<>();
    private volatile ArrayBlockingQueue<Runnable> tasks = new ArrayBlockingQueue(100);
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
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean close() {
        boolean res = true;
        for (Thread thread : threads) {
            if (thread.isAlive()) {
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
            while (isRunning) {
                Runnable nextTask = null;
                try {
                    nextTask = tasks.poll(100, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (nextTask != null) {
                    nextTask.run();
                }
            }
        }
    }
}