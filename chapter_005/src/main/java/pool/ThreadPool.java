package pool;

import blocking.SimpleBlockingQueue;

import java.util.*;
import java.util.concurrent.Executor;

public class ThreadPool implements Executor {
    private volatile SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();
    private volatile boolean isRunning = true;

    public ThreadPool() {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            new Thread(new TaskWorker()).start();
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
    }

    private final class TaskWorker implements Runnable {

        @Override
        public void run() {
            while (isRunning) {
                Runnable nextTask = null;
                try {
                    nextTask = tasks.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (nextTask != null) {
                    nextTask.run();
                }
                if (!isRunning) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}