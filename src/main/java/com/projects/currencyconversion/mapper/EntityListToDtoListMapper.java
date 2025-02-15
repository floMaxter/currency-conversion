package com.projects.currencyconversion.mapper;

import java.util.List;

public interface EntityListToDtoListMapper<E, D> {

    List<D> toDto(List<E> entities);
}
