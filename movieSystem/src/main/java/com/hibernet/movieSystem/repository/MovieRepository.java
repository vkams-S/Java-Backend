package com.hibernet.movieSystem.repository;

import com.hibernet.movieSystem.entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie,Long> {
    Optional<Movie> findByMovieName(String name);
    Optional<Movie> findByGenre(String genre);
}
