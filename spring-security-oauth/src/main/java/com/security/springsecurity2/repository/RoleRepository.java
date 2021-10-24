package com.security.springsecurity2.repository;

import com.security.springsecurity2.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findByRole(String role);
}
