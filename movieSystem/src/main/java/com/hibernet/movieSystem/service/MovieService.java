package com.hibernet.movieSystem.service;

import com.hibernet.movieSystem.NotFoundException;
import com.hibernet.movieSystem.model.MovieRequest;
import com.hibernet.movieSystem.model.MovieResponse;

public interface MovieService {

    void create(MovieRequest movieRequest);
    MovieResponse get(String name) throws NotFoundException;
    void update(String name, MovieRequest movieRequest) throws NotFoundException;
    void delete(String name) throws NotFoundException;
}
