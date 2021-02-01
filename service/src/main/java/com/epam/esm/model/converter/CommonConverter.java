package com.epam.esm.model.converter;

import java.util.List;

public interface CommonConverter<S, T> {
    T convertTo(S entity);

    S convertFrom(T entity);

    List<T> convertTo(List<S> entities);

    List<S> convertFrom(List<T> entities);
}