package com.employeeservice.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(value = HttpStatus.CONFLICT)
public class EmployeeNotValidException extends RuntimeException {
    public EmployeeNotValidException(String message){
        super(message);
    }
}
