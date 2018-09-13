package jmm;

public class UnpredictableBehavior {
    public void display() throws InterruptedException {
        Increment increment = new Increment(0);
        start(increment);
        start(increment);
    }

    private void start(Increment increment) {
        new Thread() {
            @Override
            public void run() {
                increment.showIncrement();
            }
        }.start();
    }


    private class Increment {
        private int value;

        public Increment(int value) {
            this.value = value;
        }

        public void showIncrement() {
            for (int i = 0; i != 5; i++) {
                value++;
                System.out.println(String.format("%s value = %d", Thread.currentThread().getName(), value));
            }
        }
    }
}
