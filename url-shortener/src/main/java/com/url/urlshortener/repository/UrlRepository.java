package com.url.urlshortener.repository;

import com.url.urlshortener.entity.Url;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends CrudRepository<Url,Long> {
    Optional<Url> findByUrl(String url);
}
