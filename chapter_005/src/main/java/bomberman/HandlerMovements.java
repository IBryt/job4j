package bomberman;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ThreadSafe
public class HandlerMovements implements Executor {
    @GuardedBy("this")
    private final Board board;
    private final ExecutorService pool;

    public HandlerMovements(Board board) {
        this.board = board;
        this.pool = Executors.newFixedThreadPool(board.getUnits().size());
    }

    @Override
    public void execute(Runnable command) {
        pool.submit(command);
    }

    public void close() {
        board.getIsClose().set(true);
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
