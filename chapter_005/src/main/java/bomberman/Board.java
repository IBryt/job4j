package bomberman;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public final class Board {
    private final int width = 10;
    private final int height = 10;
    private final ReentrantLock[][] board;
    private final Map<Cell, ReentrantLock> cells = new HashMap<>((int) (width * height / 0.75) + 1);
    private Units hero;

    public Board() {
        this.board = new ReentrantLock[width][height];
        fillBoard();
        this.hero = initHero();
    }

    private Units initHero() {
        Cell cell = new Cell(0, 0);
        Units hero = new Units("hero", cell);
        cells.get(cell).lock();
        return hero;
    }

    private void fillBoard() {
        for (int i = 0; i != width; i++) {
            for (int j = 0; j != height; j++) {
                board[i][j] = new ReentrantLock();
                cells.put(new Cell(i, j), board[i][j]);
            }
        }
    }

    public boolean move(final Cell src, final Cell dst) throws InterruptedException {
        boolean res = false;
        ReentrantLock lock = cells.get(dst);
        if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
            hero.setCell(dst);
            cells.get(dst).unlock();
            res = true;
        }
        return res;
    }
}
