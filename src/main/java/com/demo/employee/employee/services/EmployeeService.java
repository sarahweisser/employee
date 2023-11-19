package com.demo.employee.employee.services;

import com.demo.employee.employee.models.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> findAllEmployees();
    Optional<Employee> findEmployeeById(Long id);
    Employee createEmployee(Employee employee);
    Employee updateEmployee(Employee employeeToUpdate);
    Employee deleteEmployeeById(Long id);
}
