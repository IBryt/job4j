package ru.job4j.dao;

import ru.job4j.models.Model;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Main methods CRUD operations.
 */
public interface Dao<T extends Model> {

     /**
      * @param builder builder query.
      * @param root A root type in the from clause.
      * @param sectionWhere parameters query.
      * @return list of predicates to add to the query terms.
      */
     List<Predicate> setConditions(final CriteriaBuilder builder, final Root root, final Map<String, Object> sectionWhere);

     /**
      * @param t instance for added.
      * @return added instance.
      */
     T add(final T t);

     /**
      * @param t instance for remove.
      * @return removed object.
      */
     T remove(final T t, final Class<T> persistentClass);

     /**
      * @param persistentClass class for selection values.
      * @return list values
      */
     List<T> getAll(final Class<T> persistentClass);

     /**
      * @param persistentClass class for selection values.
      * @param sectionWhere search term names.
      * @return list values
      */
     List<T> getAll(final Class<T> persistentClass, final Map<String, Object> sectionWhere);

     /**
      * @param id primary key for object searching.
      * @param persistentClass object class.
      * @return found object.
      */
     T findByID(final int id, final Class<T> persistentClass);

     /**
      * @param t object for update.
      * @return updated object.
      */
     T update(final T t);
}
