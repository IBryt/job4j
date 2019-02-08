package ru.job4j.dao;

import org.apache.logging.log4j.*;
import ru.job4j.models.Model;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Basic implementation Dao operations.
 */
public class ImplDao<T extends Model> implements Dao<T> {
    private static final Logger LOG = LogManager.getLogger(ImplDao.class.getName());
    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("tracker");
    private static final ImplDao INSTANCE = new ImplDao();

    private ImplDao() {
        super();
    }

    public static ImplDao getInstance() {
        return INSTANCE;
    }

    @Override
    public T remove(final T t, final Class<T> persistentClass) {
        final EntityManager em = FACTORY.createEntityManager();
        em.getTransaction().begin();
        try {
            final T entity = findByID(t.getId(), persistentClass);
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            em.getTransaction().commit();
        } catch (final Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        return t;
    }

    @Override
    public T add(final T t) {
        final EntityManager em = FACTORY.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(t);
            em.getTransaction().commit();
//            em.detach(t);
        } catch (final Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        return t;
    }

    @Override
    public List<T> getAll(final Class<T> persistentClass) {
        return getAll(persistentClass, Collections.EMPTY_MAP);
    }

    @Override
    public List<T> getAll(final Class<T> persistentClass, final Map<String, Object> sectionWhere) {
        final EntityManager em = FACTORY.createEntityManager();
        List<T> list = new ArrayList<T>();
        em.getTransaction().begin();
        try {
            final CriteriaBuilder builder = em.getCriteriaBuilder();
            final CriteriaQuery<T> query = builder.createQuery(persistentClass);
            final Root<T> root = query.from(persistentClass);
            final List<Predicate> predicates = setConditions(builder, root, sectionWhere);
            final CriteriaQuery<T> all = query.select(root).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
            final TypedQuery<T> allQuery = em.createQuery(all);
            list = allQuery.getResultList();
            em.getTransaction().commit();
        } catch (final Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        return list;
    }

    @Override
    public T findByID(int id, Class<T> persistentClass) {
        final EntityManager em = FACTORY.createEntityManager();
        T t = null;
        em.getTransaction().begin();
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(persistentClass);
            Root<T> from = query.from(persistentClass);
            query.select(from);
            query.where(builder.equal(from.get("id"), id));
            TypedQuery<T> resQuery = em.createQuery(query);
            t = resQuery.getSingleResult();
        } catch (final Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        return  t;
    }

    @Override
    public List<Predicate> setConditions(final CriteriaBuilder builder, final Root root, final Map<String, Object> sectionWhere) {
        final List<Predicate> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : sectionWhere.entrySet()) {
            list.add(builder.equal(root.get(entry.getKey()), entry.getValue()));
        }
        return list;
    }

    @Override
    public T update(T t) {
        final EntityManager em = FACTORY.createEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(t);
            em.getTransaction().commit();
        } catch (final Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        return t;
    }
}
