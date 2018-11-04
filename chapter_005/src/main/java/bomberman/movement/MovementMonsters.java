package bomberman.movement;

import bomberman.Board;
import bomberman.Units;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class MovementMonsters extends MovementUnits {

    public MovementMonsters(Board board, Units unit) {
        super(board, unit);
    }

    @Override
    public void move() {
        moveUnit();
        randomDirections();
    }
}
