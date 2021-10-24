package com.employee.management.repository;

import com.employee.management.entites.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long> {
    Optional<Employee> findByEmployeeId(String employeeId);
}
