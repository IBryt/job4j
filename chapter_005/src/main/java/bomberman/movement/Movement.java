package bomberman.movement;

import bomberman.Units;

public interface Movement<T extends MovementUnits> {
    public void move();

    public Units getUnit();
}
