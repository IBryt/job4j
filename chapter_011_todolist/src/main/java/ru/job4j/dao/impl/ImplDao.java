package ru.job4j.dao.impl;

import ru.job4j.dao.AbstractDao;
import ru.job4j.models.Model;
import ru.job4j.models.impl.Item;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Basic implementation Dao operations.
 */
public class ImplDao<T extends Model> extends AbstractDao<T> {
    private static final ImplDao INSTANCE = new ImplDao();

    private ImplDao() {
        super();
    }

    public static ImplDao getInstance() {
        return INSTANCE;
    }

    @Override
    public T remove(final T t, final Class<T> persistentClass) {
        return this.wrapper(em ->  {
                    final T entity  = em.find(persistentClass, t.getId(), LockModeType.OPTIMISTIC);
                    em.remove(em.contains(entity) ? entity : em.merge(entity));
                    return t;
                }
        );
    }

    @Override
    public T add(final T t) {
        return this.wrapper(em ->  {
                    em.persist(t);
                    return t;
                }
        );
    }

    @Override
    public List<T> getAll(final Class<T> persistentClass) {
        return getAll(persistentClass, Collections.EMPTY_MAP);
    }

    @Override
    public List<T> getAll(final Class<T> persistentClass, final Map<String, Object> sectionWhere) {
        return this.wrapper(em -> {
                    final CriteriaBuilder builder = em.getCriteriaBuilder();
                    final CriteriaQuery<T> query = builder.createQuery(persistentClass);
                    final Root<T> root = query.from(persistentClass);
                    final List<Predicate> predicates = setConditions(builder, root, sectionWhere);
                    final CriteriaQuery<T> all = query.select(root);
                    all.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
                    all.orderBy(builder.asc(root.get("id")));
                    final TypedQuery<T> allQuery = em.createQuery(all);
                    return allQuery.getResultList();
                }
        );
    }

    @Override
    public T findByID(final int id, final Class<T> persistentClass) {
        return this.wrapper(em ->  em.find(persistentClass, id));
    }

    @Override
    public T update(final T t) {
        return this.wrapper(em ->  {
                    em.find(Item.class, t.getId(), LockModeType.OPTIMISTIC);
                    em.merge(t);
                    t.setVersion(t.getVersion() + 1);
                    return t;
                }
        );
    }
}
