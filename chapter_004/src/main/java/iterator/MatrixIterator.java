package iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIterator implements Iterator {

    private final int[][] values;
    private int external = 0;
    private int internal = 0;


    public MatrixIterator(int[][] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
         return values.length > external && values[external].length > internal;
    }

    @Override
    public Integer next() {
        int result = 0;
        if (values.length == 0 || values[external].length < internal) {
            throw new NoSuchElementException();
        }
        if (values[external].length - 1 == internal) {
            result = values[external++][internal];
            internal = 0;
        } else {
            result = values[external][internal++];
        }
        return result;
    }
}
