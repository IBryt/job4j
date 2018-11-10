package bomberman;

import bomberman.movement.Movement;
import bomberman.movement.MovementHero;
import bomberman.movement.MovementMonsters;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
@ThreadSafe
public final class Board {
    public final static String HERO = "hero";
    @GuardedBy("this")
    private final int width = 10;
    private final int height = 10;
    private final int monsters;
    private final int blockCell;
    private final Map<Cell, ReentrantLock> cells;
    private final Map<String, Units> units;
    private final AtomicBoolean isClose = new AtomicBoolean(false);
    private final Units hero;
    private final MovementHero movementHero;
    private final List<Movement> movements;
    private volatile boolean deadHero = false;

    private Board(final int monsters, final int blockCell) {
        this.monsters = monsters;
        this.blockCell = blockCell;
        this.cells = initCells();
        List<Cell> emptyCells = cells.entrySet().stream().
                filter(e -> e.getKey().getType() == TypeCell.EMPTY).
                map(Map.Entry::getKey).collect(Collectors.toCollection(LinkedList<Cell>::new));
        this.hero = initHero(emptyCells);
        this.units = initUnits(emptyCells);
       // this.units.put(Board.HERO, getHero());
        this.movements = initMovements();
        this.movementHero = findMovementHero();
    }

    public boolean isDeadHero() {
        return deadHero;
    }

    public void setDeadHero(boolean deadHero) {
        this.deadHero = deadHero;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMonsters() {
        return monsters;
    }

    public int getBlockCell() {
        return blockCell;
    }

    public Map<Cell, ReentrantLock> getCells() {
        return cells;
    }

    public Map<String, Units> getUnits() {
        return units;
    }

    public AtomicBoolean getIsClose() {
        return isClose;
    }

    public Units getHero() {
        return hero;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public MovementHero getMovementHero() {
        return movementHero;
    }

    public boolean getDeadHero() {
        return deadHero;
    }

    private MovementHero findMovementHero() {
        Movement res = null;
        for (Movement m : movements) {
            if (hero.getCell().equals(m.getUnit().getCell())) {
                res = m;
            }
        }
        return (MovementHero) res;
    }

    private List<Movement> initMovements() {
        return units.entrySet().stream().map(unit ->
                unit.getValue().getName().equals(Board.HERO)
                        ? new MovementHero(this, unit.getValue())
                        : new MovementMonsters(this, unit.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Units initHero(List<Cell> emptyCells) {
        return getUnitRandomPosition(emptyCells, Board.HERO);
    }

    private Units getUnitRandomPosition(List<Cell> emptyCells, String name) {
        int index = getRandomIntInRange(emptyCells.size());
        Cell cell = emptyCells.get(index);
        Units unit = new Units(name, cell);
        emptyCells.remove(cell);
        return unit;
    }

    private int getRandomIntInRange(int max) {
        Random r = new Random();
        return r.nextInt(max);
    }

    private Map<String, Units> initUnits(List<Cell> emptyCells) {
        Map<String, Units> units = new Hashtable<>();
        for (int i = 1; i != monsters + 1; i++) {
            String name = String.format("monster %d", i);
            units.put(name, getUnitRandomPosition(emptyCells, name));
        }
        return units;
    }

    private Map<Cell, ReentrantLock> initCells() {
        Map<Cell, ReentrantLock> cells = new HashMap<>((int) (width * height / 0.75) + 1);
        Set<Cell> block =  blockCell();
        for (int i = 0; i != width; i++) {
            for (int j = 0; j != height; j++) {
                TypeCell type = block.contains(new Cell(i, j)) ? TypeCell.BLOCK : TypeCell.EMPTY;
                ReentrantLock lock = new ReentrantLock();
                cells.put(new Cell(i, j, type), lock);
                if (type == TypeCell.BLOCK) {
                    lock.lock();
                }
            }
        }
        return cells;
    }

    private Set<Cell> blockCell() {
        Set<Cell> block = new HashSet<>();
        while (block.size() < blockCell) {
            block.add(randomCell());
        }
        return block;
    }

    private Cell randomCell() {
        return  new Cell((int) (Math.random() * width), (int) (Math.random() * height));
    }

    private void close() {
        isClose.set(true);
    }

    public static void main(String[] args) {
        final Board board = new Board(6, 6);
        MovementHero movementHero = new MovementHero(board, board.getHero());
        final HandlerMovements handler = new HandlerMovements(board);
        board.getMovements().stream().map((Movement m) -> (Runnable) m::move).forEach(handler::execute);
        Thread threadHero = new Thread() {
            @Override
            public void run() {
                movementHero.move();
                handler.close();
                System.out.println("The hero is dead!");
            }
        };
        threadHero.start();

//        try {
//            Thread.sleep(1600);
//            handler.close();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}