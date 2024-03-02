package com.example.airlineflights.Repository;

import com.example.airlineflights.Domain.Entity;

import java.util.Optional;

public interface Repository<ID, E extends Entity<ID>>{

    Optional<E> findOne(ID id);
    Iterable<E> findAll();
    Optional<E> save(E entity);

}
