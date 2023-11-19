package com.demo.employee.employee.exceptions;

import com.demo.employee.employee.utils.ValidationMessages;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException() {
        super(ValidationMessages.EMPLOYEE_NOT_FOUND);
    }
}
