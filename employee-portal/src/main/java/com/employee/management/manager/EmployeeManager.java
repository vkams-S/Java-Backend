package com.employee.management.manager;

import com.employee.management.entites.EmployeeResponse;
import com.employee.management.entites.EmployeeRequest;
import com.employee.management.exception.NotFoundException;

public interface EmployeeManager {
    EmployeeResponse create(EmployeeRequest employeeRequest);
    EmployeeResponse get(String employeeId) throws NotFoundException;
    Float getRating(String employeeId) throws NotFoundException;
    Long getSalary(String employeeId) throws NotFoundException;
    void updateRating(String employeeId, Float rating) throws NotFoundException;
    void updateSalary(String employeeId, Long salary) throws NotFoundException;
    void assignManager(String employeeId, String managerId) throws NotFoundException;
}
