package bomberman;

import java.util.concurrent.locks.ReentrantLock;

public final class Units {
    private final String name;
    private Cell cell;

    public Units(String name, Cell cell) {
        this.name = name;
        this.cell = cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
