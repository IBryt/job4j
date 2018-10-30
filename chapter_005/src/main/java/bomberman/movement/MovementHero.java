package bomberman.movement;

import bomberman.Board;
import bomberman.Cell;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MovementHero extends MovementUnits {
    public MovementHero(Board board) {
        super(board);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            move(board.getHero().getCell(), moveTo());
        }
    }

    public boolean move(final Cell src, final Cell dst) {
        if(!firstLock) {
            firstLockUnit();
        }
        boolean res = false;
        ReentrantLock lock = board.getCells().get(dst);
        try {
            if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                board.getHero().setCell(dst);
                ReentrantLock temp = board.getCells().get(src);
                board.getCells().get(src).unlock();
                //System.out.println(String.format("src = %s, dst = %s", dst, src));
                res = true;
            } else {
                while(true) {
                    res = move(src, moveTo());
                    if (res) {
                        break;
                    }
                    changeDirection();
                }

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    private void firstLockUnit() {
        firstLock = !firstLock;
        board.getCells().get(board.getHero().getCell()).lock();
    }
}
