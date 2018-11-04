package bomberman.movement;

import bomberman.Board;
import bomberman.Cell;
import bomberman.Units;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@ThreadSafe
public abstract class MovementUnits implements Movement {
    @GuardedBy("this")
    private final Board board;
    enum Directions { LEFT, RIGHT, TOP, BUTTON }
    volatile Directions direction;
    private boolean firstLock = false;
    private final Units unit;

    MovementUnits(final Board board, final Units unit) {
        this.board = board;
        this.unit = unit;
    }

    public abstract void move();

    public Units getUnit() {
        return unit;
    }

    void moveUnit() {
        if (!firstLock) {
            firstLockUnit();
        }
        while (!board.getIsClose().get()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Cell src = unit.getCell();
            Cell dst = moveTo(src);
            ReentrantLock lock = board.getCells().get(dst);
            try {
                while (!lock.tryLock(500, TimeUnit.MILLISECONDS) && !board.getIsClose().get()) {
                    changeDirection();
                    dst = moveTo(src);
                    lock = board.getCells().get(dst);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("unit = %s, src = %s, dst = %s", unit.getName(), src, dst));
            unit.setCell(dst);
            board.getCells().get(src).unlock();
        }
    }

    private void firstLockUnit() {
        this.randomDirections();
        firstLock = !firstLock;
        board.getCells().get(unit.getCell()).lock();
        checkLockAllUnits();
    }

    private void checkBounds(int x, int y) {
        if (x >= board.getWidth() - 1 && this.direction == Directions.RIGHT
                || x <= 0 &&  this.direction == Directions.LEFT
                || y <= 0 && this.direction == Directions.BUTTON
                || y >= board.getHeight() - 1 && this.direction == Directions.TOP) {
            changeDirection();
        }
    }

    void randomDirections() {
        int pick = new Random().nextInt(Directions.values().length);
        direction = Directions.values()[pick];
    }

    private Cell moveTo(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        checkBounds(x, y);
        switch (this.direction) {
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
            case TOP:
                y++;
                break;
            case BUTTON:
                y--;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return new Cell(x, y);
    }

    private void changeDirection() {
        switch (this.direction) {
            case RIGHT:
                this.direction = Directions.LEFT;
                break;
            case LEFT:
                this.direction = Directions.RIGHT;
                break;
            case BUTTON:
                this.direction = Directions.TOP;
                break;
            case TOP:
                this.direction = Directions.BUTTON;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void checkLockAllUnits() {
        boolean res = false;
        while (!res) {
            res = true;
            for (Map.Entry<String, Units> entry : board.getUnits().entrySet()) {
                Cell cell = entry.getValue().getCell();
                if (!board.getCells().get(cell).isLocked()) {
                    res = false;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
