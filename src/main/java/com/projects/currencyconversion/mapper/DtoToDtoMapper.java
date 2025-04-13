package com.projects.currencyconversion.mapper;

public interface DtoToDtoMapper<D1, D2> {

    D2 toDto(D1 baseDto);
}
