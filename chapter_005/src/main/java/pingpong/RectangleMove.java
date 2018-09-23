package pingpong;

import javafx.scene.shape.Rectangle;

public class RectangleMove implements Runnable {
    private final Rectangle rect;

    public RectangleMove(Rectangle rect) {
        this.rect = rect;
    }

    @Override
    public void run() {
        int step = 1;
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }
            if (rect.getX() <= 240 && rect.getX() <= 50) {
                step = 1;
            } else if (rect.getX() >= 240 && rect.getX() >= 50) {
                step = -1;
            }
            this.rect.setX(this.rect.getX() + step);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                if (!Thread.currentThread().isInterrupted()) {
                    return;
                }
                e.printStackTrace();
            }
        }
    }
}