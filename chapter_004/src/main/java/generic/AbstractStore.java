package generic;

import java.util.NoSuchElementException;
import java.util.Optional;

public class AbstractStore<T extends Base> implements Store<T> {
    private SimpleArray<T> arrayBase;

    public AbstractStore(SimpleArray<T> arrayBase) {
        this.arrayBase = arrayBase;
    }

    @Override
    public void add(T base) {
        this.arrayBase.add(base);
    }

    @Override
    public boolean replace(String id, T base) {
        int index = findIndex(id);
        return index == -1 ? false : this.arrayBase.set(index, base);
    }

    @Override
    public boolean delete(String id) {
        int index = findIndex(id);
        return index == -1 ? false : this.arrayBase.delete(index);
    }

    @Override
    public T findById(String id) {
        T result = null;
        for (T t : this.arrayBase) {
            if (t.getId().equals(id)) {
                result = t;
                break;
            }
        }
        if (result == null) {
            throw  new NoSuchElementException();
        }
        return result;
    }

    private int findIndex(String id) {
        return this.arrayBase.indexOf(findById(id));
    }

    public SimpleArray<T> getArrayBase() {
        return arrayBase;
    }
}
