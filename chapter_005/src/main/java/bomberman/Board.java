package bomberman;

import bomberman.movement.MovementHero;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public final class Board {
    private final int width = 10;
    private final int height = 10;
    private final Map<Cell, ReentrantLock> cells = new HashMap<>((int) (width * height / 0.75) + 1);
    private Units hero;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Map<Cell, ReentrantLock> getCells() {
        return cells;
    }

    public Units getHero() {
        return hero;
    }

    public void setHero(Units hero) {
        this.hero = hero;
    }

    public Board() {
        fillBoard();
        this.hero = initHero();
    }

    private Units initHero() {
        Cell cell = new Cell(0, 0);
        Units hero = new Units("hero", cell);
        return hero;
    }

    private void fillBoard() {
        for (int i = 0; i != width; i++) {
            for (int j = 0; j != height; j++) {
                cells.put(new Cell(i, j), new ReentrantLock());
            }
        }
    }

    public static void main(String[] args) {
        final Board board = new Board();
        final MovementHero moveHero = new MovementHero(board);
        moveHero.start();
    }
}