package com.demo.employee.employee.advice;

import com.demo.employee.employee.exceptions.EmailNotUniqueException;
import com.demo.employee.employee.exceptions.EmployeeNotFoundException;
import com.demo.employee.employee.models.EmployeeResponse;
import com.demo.employee.employee.utils.ValidationMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppControllerAdvice {
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<EmployeeResponse> handleEmployeeNotFoundExceptions(Exception e) {
        EmployeeResponse response = new EmployeeResponse(null, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailNotUniqueException.class)
    public ResponseEntity<EmployeeResponse> handleNonUniqueEmailExceptions(Exception e) {
        System.out.println("HEY");
        EmployeeResponse response = new EmployeeResponse(null, ValidationMessages.EMAIL_NOT_UNIQUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
