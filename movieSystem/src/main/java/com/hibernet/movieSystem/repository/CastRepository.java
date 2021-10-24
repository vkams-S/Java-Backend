package com.hibernet.movieSystem.repository;

import com.hibernet.movieSystem.entity.Cast;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CastRepository extends CrudRepository<Cast,Long> {
    Optional<Cast> findByName(String name);
}
