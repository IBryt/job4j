package cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Model control manager.
 * Based on a non-blocking algorithm.
 * @version $Id$
 * @since 0.1
 * @author Bryt Ihor
 */

public class NonBlockingCache {
    private Map<Integer, Base> cache = new ConcurrentHashMap();

    /**
     * Added new model if no exist model with this key.
     * @return true if added model.
     * @throws OptimisticException if the model existed before
     */
    public boolean add(Base model) {
        return cache.putIfAbsent(model.getId(), addModel(model)) == null;
    }

    private Base addModel(Base model) {
        if (cache.containsKey(model.getId())) {
            throw new OptimisticException();
        }
        return model;
    }

    /**
     * Update model if exist model with this key.
     * @throws OptimisticException if chang version model
     */
    public boolean update(Base model) {
        int version = model.getVersion();
        Base temp = updateModel(model);
        cache.computeIfPresent(model.getId(), (k, v) -> temp);
        return version != model.getVersion();
    }

    private Base updateModel(Base model) {
        checkVersion(model);
        return model.changeVersion();
    }
    /**
     * Delete model if exist model with this key.
     * @return true if delete model.
     * @throws OptimisticException if chang version model
     */
    public boolean delete(Base model) {
        Base temp = deleteModel(model);
        return null != cache.remove(temp.getId());
    }

    private Base deleteModel(Base model) {
        checkVersion(model);
        return model;
    }

    private void checkVersion(Base model) {
        Base find = cache.get(model.getId());
        if (find.getId() != model.getId()) {
            throw new OptimisticException();
        }
    }

    private class OptimisticException extends RuntimeException {

    }
}