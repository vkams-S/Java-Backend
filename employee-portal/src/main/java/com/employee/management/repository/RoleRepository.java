package com.employee.management.repository;

import com.employee.management.entites.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findByRole(String role);
}
