package storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> storage;

    public UserStorage() {
        this.storage = new HashMap<>();
    }

    public synchronized boolean add(final User user) {
        return storage.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(final User user) {
        User find = storage.get(user.getId());
        if (find != null) {
            storage.put(user.getId(), user);
        }
        return find != null && !user.equals(find);
    }

    public synchronized boolean delete(final User user) {
        return storage.remove(user.getId()) != null;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User src = storage.get(fromId);
        User dst = storage.get(toId);
        boolean res = false;
        if (src != null && dst != null && src.getAmount() >= amount) {
            src.setAmount(src.getAmount() - amount);
            dst.setAmount(dst.getAmount() + amount);
            res = true;
        }
        return res;
    }
}
