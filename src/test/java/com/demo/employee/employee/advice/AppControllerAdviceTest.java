package com.demo.employee.employee.advice;

import com.demo.employee.employee.exceptions.EmailNotUniqueException;
import com.demo.employee.employee.exceptions.EmployeeNotFoundException;
import com.demo.employee.employee.models.EmployeeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class AppControllerAdviceTest {
    @Autowired
    private AppControllerAdvice advice;

    EmployeeNotFoundException enfe = new EmployeeNotFoundException();
    EmailNotUniqueException enue = new EmailNotUniqueException();

    @Test
    @DisplayName("testEmployeeNotFoundException")
    void testEmployeeNotFoundException() {
        ResponseEntity<EmployeeResponse> ex = advice.handleEmployeeNotFoundExceptions(enfe);
        Assertions.assertEquals(ex.getStatusCode(), HttpStatus.NOT_FOUND);
        Assertions.assertNull(ex.getBody().getResponse());
    }

    @Test
    @DisplayName("testEmailNotUniqueException")
    void testEmailNotUniqueException() {
        ResponseEntity<EmployeeResponse> ex = advice.handleNonUniqueEmailExceptions(enue);
        Assertions.assertEquals(ex.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertNull(ex.getBody().getResponse());
    }
}
