package notification;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.String.format;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(final User user) {

        pool.submit(new Runnable() {
            @Override
            public void run() {
                String suject = format("Notification %s to email %s", user.getUsername(), user.getEmail());
                String body =  format("Add a new event to %s", user.getUsername());
                send(suject, body, user.getEmail());
            }
        });
    }

    private void send(final String suject, final String body, final String email) {

    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Execute " + Thread.currentThread().getName());
    }
}
