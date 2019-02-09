package ru.job4j.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.dao.impl.ImplDao;
import ru.job4j.models.Model;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.function.Function;

public abstract class AbstractDao<T extends Model> implements Dao<T> {
    protected static final Logger LOG = LogManager.getLogger(ImplDao.class.getName());
    protected static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("tracker");

    public <T> T wrapper(final Function<EntityManager, T> command) {
        final EntityManager em = FACTORY.createEntityManager();
        em.getTransaction().begin();
        try {
            return command.apply(em);
        } catch (final Exception e) {
            em.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
            throw e;
        } finally {
            em.getTransaction().commit();
            em.close();
        }
    }

    public List<Predicate> setConditions(final CriteriaBuilder builder, final Root root, final Map<String, Object> sectionWhere) {
        final List<Predicate> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : sectionWhere.entrySet()) {
            list.add(builder.equal(root.get(entry.getKey()), entry.getValue()));
        }
        return list;
    }
}
