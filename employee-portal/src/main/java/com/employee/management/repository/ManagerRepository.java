package com.employee.management.repository;

import com.employee.management.entites.Manager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends CrudRepository<Manager,Long> {
    Optional<Manager> findByEmployeeId(String employeeId);
}
