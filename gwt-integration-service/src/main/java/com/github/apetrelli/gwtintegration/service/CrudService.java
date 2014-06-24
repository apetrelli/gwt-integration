package com.github.apetrelli.gwtintegration.service;

import java.util.Set;


public interface CrudService<T, I> {

    T findOne(I id);

    T save(T entity);

    void delete(I id);

    void deleteAll(Set<T> items);
}
