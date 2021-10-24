package com.url.urlshortener.repository;

import com.url.urlshortener.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client,Long> {
    Optional<Client> findByClientName(String name);
}
