package jmm;

import org.junit.Test;

public class UnpredictableBehaviorTest {

    @Test
    public void displayUnpredictableBehavior() throws InterruptedException {
        new UnpredictableBehavior().display();
    }
}