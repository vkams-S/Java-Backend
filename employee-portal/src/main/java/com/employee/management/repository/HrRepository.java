package com.employee.management.repository;

import com.employee.management.entites.HumanResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HrRepository extends CrudRepository<HumanResource,Long> {
    Optional<HumanResource> findByEmployeeId(String employeeId);
}
