package bomberman.movement;


import bomberman.Board;
import bomberman.Units;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public final class MovementHero extends MovementUnits {

    public MovementHero(final Board board, final Units unit) {
        super(board, unit);
    }

    @Override
    public void move() {
        moveUnit();
    }

    public void directionLeft() {
        this.direction = Directions.LEFT;
    }

    public void directionRight() {
        this.direction = Directions.RIGHT;
    }

    public void directionTop() {
        this.direction = Directions.TOP;
    }

    public void directionButton() {
        this.direction = Directions.BUTTON;
    }
}
