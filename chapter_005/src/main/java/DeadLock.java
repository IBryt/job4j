import java.util.concurrent.CountDownLatch;

public class DeadLock {
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(2);

    public static void main(String[] args) {
        String s1 = "1";
        String s2 = "2";
        new Test(s1, s2).start();
        new Test(s2, s1).start();
    }

    private static class Test extends Thread {
        String s1;
        String s2;

        public Test(String s1, String s2) {
            this.s1 = s1;
            this.s2 = s2;
        }

        @Override
        public void run() {
            synchronized (s1) {
                //уменьшаем счетчик на 1
                COUNT_DOWN_LATCH.countDown();
                //блокирует поток, вызвавший его, до тех пор, пока счетчик CountDownLatch не станет равен 0
                try {
                    COUNT_DOWN_LATCH.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (s2) {
                    System.out.println("завис");
                }
            }
        }
    }
}
