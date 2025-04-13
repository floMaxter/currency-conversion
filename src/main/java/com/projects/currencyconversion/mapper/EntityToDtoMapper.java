package com.projects.currencyconversion.mapper;

public interface EntityToDtoMapper<E, D> {

    D toDto(E entity);
}
