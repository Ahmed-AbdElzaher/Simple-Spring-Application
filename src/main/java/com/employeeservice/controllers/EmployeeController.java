package com.employeeservice.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.employeeservice.Exception.EmployeeNotFoundException;
import com.employeeservice.Exception.EmployeeNotValidException;
import com.employeeservice.model.Department;
import com.employeeservice.model.DepartmentRepository;
import com.employeeservice.model.Employee;
import com.employeeservice.model.EmployeeRepository;

@RestController
@Validated
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;


    @GetMapping("/jpa/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/jpa/employees/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).get();
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found"); 
        }
        return employee;
    }

    @PostMapping("/jpa/employees")
    public ResponseEntity<Object> saveEmployee(@Valid @RequestBody Employee employee) {
        Employee checkEmpName = employeeRepository.findByEmployeeName(employee.getEmployeeName());
        Employee checkEmpEmail = employeeRepository.findByEmployeeEmail(employee.getEmployeeEmail());
        if(checkEmpName == null && checkEmpEmail == null) {
            Employee newEmployee = employeeRepository.save(employee);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("{employeesId}")
                    .buildAndExpand(newEmployee.getId())
                    .toUri();
            return ResponseEntity.created(uri).build();
        }else{
            throw new EmployeeNotValidException("Conflict");
        }
    }

    @PostMapping("/jpa/add-department/{employeeID}")
    public ResponseEntity<Object> assignEmployeeDepartment(@PathVariable Long employeeID, @RequestBody Department department) {
        Employee employee = employeeRepository.findById(employeeID).get();
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found");
        }

        department.setEmployee(employee);
        departmentRepository.save(department);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{employeesId}")
                .buildAndExpand(employee.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/jpa/employees/{employeeID}")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long employeeID , @Valid @RequestBody Employee requestBody) {
        Employee employee = employeeRepository.findById(employeeID).get();
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found");
        }

        employee.setEmployeeName(requestBody.getEmployeeName());
        employee.setEmployeeEmail(requestBody.getEmployeeEmail());
        employee.setDepartments(requestBody.getDepartments());
        Employee updatedEmployee = employeeRepository.save(employee);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("{employeesId}")
        .buildAndExpand(updatedEmployee.getId())
        .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/jpa/employee/{employeeID}")
    public void deleteEmployee(@PathVariable Long employeeID){
        Employee employee = employeeRepository.findById(employeeID).get();
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        employeeRepository.delete(employee);
    }
    
}
