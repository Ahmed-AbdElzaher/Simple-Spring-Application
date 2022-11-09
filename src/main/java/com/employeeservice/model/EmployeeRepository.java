package com.employeeservice.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    Employee findByEmployeeName(String employeeName);
    Employee findByEmployeeEmail(String employeeEmail);
}
