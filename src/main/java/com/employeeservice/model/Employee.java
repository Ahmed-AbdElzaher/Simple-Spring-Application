package com.employeeservice.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(min=3, max=20,message="Invalid name size")
    @Pattern(regexp = "^(?![\\s\\S]*[^\\w -]+)[\\s\\S]*?$")
    private String employeeName;

    @Email 
    private String employeeEmail;

    @JsonIgnore
    private Long nationalId;

    @OneToMany(mappedBy = "employee",cascade =  CascadeType.REMOVE)
    private List<Department> departments;

    public Employee(Long id, String employeeName, String employeeEmail){
        this.id = id;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
    }


    public Employee() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return this.employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public List<Department> getDepartments() {
        return this.departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Long getNationalId() {
        return this.nationalId;
    }

    public void setNationalId(Long nationalId) {
        this.nationalId = nationalId;
    }

    
}
