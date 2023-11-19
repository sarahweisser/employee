package com.demo.employee.employee.models;

public class EmployeeResponse {
    private Employee response;
    private String validationMessage;

    public EmployeeResponse(Employee response, String validationMessage) {
        this.response = response;
        this.validationMessage = validationMessage;
    }

    public EmployeeResponse() {
    }

    public Employee getResponse() {
        return response;
    }

    public void setResponse(Employee response) {
        this.response = response;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }
}
