package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

public interface CommonDao<T> {
    boolean add(T entity);

    boolean deleteById(int id);

    Optional<T> findById(int id);

    List<T> findAll();
}