package blocking;

public class ProducerCustomer {

    private final Object lock = new Object();
    private boolean blockCustomer = true;

    public void doSomething()  throws InterruptedException {
        synchronized (this.lock) {
            while (this.blockCustomer) {
                System.out.println("wait");
                lock.wait();
                //Thread.sleep(11);
            }
            System.out.println("useful work");
        }
    }

    public void changeBlock(final boolean enable) {
        synchronized (this.lock) {
            System.out.println("enable");
            this.blockCustomer = enable;
            this.lock.notify();
        }
    }

    public static void main(String[] args) {
        ProducerCustomer blockingWork = new ProducerCustomer();

        Thread producer = new Thread() {
            @Override
            public void run() {
                blockingWork.changeBlock(false);
            }
        };

        Thread customer = new Thread() {
            @Override
            public void run() {
                try {
                    blockingWork.doSomething();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        producer.start();
        customer.start();
    }
}
