package com.demo.employee.employee.exceptions;

import com.demo.employee.employee.utils.ValidationMessages;

public class EmailNotUniqueException extends RuntimeException {
    public EmailNotUniqueException() {
        super(ValidationMessages.EMAIL_NOT_UNIQUE);
    }
}
