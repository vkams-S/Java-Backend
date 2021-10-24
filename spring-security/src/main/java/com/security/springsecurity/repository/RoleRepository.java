package com.security.springsecurity.repository;

import com.security.springsecurity.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findByRole(String role);
}
