package bomberman;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public final class Cell {
    @GuardedBy("this")
    private final int x;
    private final int y;
    private final TypeCell type;

    public Cell(final int x, final int y) {
        this.x = x;
        this.y = y;
        this.type = TypeCell.EMPTY;
    }

    public Cell(int x, int y, TypeCell type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TypeCell getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return String.format("Cell{x=%s, y=%s}", x, y);
    }
}
