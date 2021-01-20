package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

/**
 * The interface Common dao.
 *
 * @param <T> the type parameter
 */
public interface CommonDao<T> {
    /**
     * Add entity to Db.
     *
     * @param entity the entity
     * @return the t
     */
    T add(T entity);

    /**
     * Delete entity by id from Db.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteById(int id);

    /**
     * Find entity by id in Db.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(int id);

    /**
     * Find all entities in Db.
     *
     * @return the list
     */
    List<T> findAll();
}