package com.employeeservice.Exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice 
public class EmployeeGlopalExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
        EmployeeExceptionResponse exception = new EmployeeExceptionResponse(
            ex.getMessage(),
            request.getDescription(false), 
            new Date());
        return new ResponseEntity<Object>(exception, HttpStatus.INTERNAL_SERVER_ERROR); 
    }

    @ExceptionHandler(EmployeeNotFoundException.class) 
    public ResponseEntity<Object> handleEmployeeNotFoundExceptions(Exception ex, WebRequest request){
        EmployeeExceptionResponse exception = new EmployeeExceptionResponse(
            ex.getMessage(),
            request.getDescription(false), 
            new Date());
        return new ResponseEntity<Object>(exception, HttpStatus.NOT_FOUND); 
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( 
                                            MethodArgumentNotValidException ex, 
                                            HttpHeaders headers,
                                            HttpStatus status,
                                            WebRequest request){
        EmployeeExceptionResponse exception = new EmployeeExceptionResponse(
        "Invalid Inputs", 
        ex.getBindingResult().toString(), 
        new Date()); 
        return new ResponseEntity<Object>(exception, HttpStatus.BAD_REQUEST); 
    }

}
