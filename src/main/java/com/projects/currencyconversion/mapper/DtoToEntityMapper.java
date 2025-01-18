package com.projects.currencyconversion.mapper;

public interface DtoToEntityMapper<D, E> {

    E toEntity(D dto);
}
