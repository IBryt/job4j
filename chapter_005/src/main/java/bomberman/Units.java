package bomberman;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public final class Units {
    @GuardedBy("this")
    private final String name;
    private volatile Cell cell;

    public Units(final String name, final Cell cell) {
        this.name = name;
        this.cell = cell;

    }

    public Cell getCell() {
        return cell;
    }

    public String getName() {
        return name;
    }

    public void setCell(final Cell cell) {
        this.cell = cell;
    }
}
