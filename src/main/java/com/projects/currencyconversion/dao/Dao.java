package com.projects.currencyconversion.dao;

import java.util.List;

public interface Dao<K, E> {
    E save(E entity);
    List<E> findAll();
    E update(E entity);
}
