package com.demo.employee.employee.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class AppControllerAdvice {
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse handleExceptions(Exception e) {
        System.out.println("HEY!!!");
        return ErrorResponse.create(e, HttpStatusCode.valueOf(500), e.getMessage());
    }
}
