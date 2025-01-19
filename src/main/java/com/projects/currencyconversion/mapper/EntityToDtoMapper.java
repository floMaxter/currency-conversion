package com.projects.currencyconversion.mapper;

import java.util.List;

public interface EntityToDtoMapper<E, D> {

    D toDto(E entity);

    List<D> toDto(List<E> entities);
}
