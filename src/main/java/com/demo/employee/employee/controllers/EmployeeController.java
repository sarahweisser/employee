package com.demo.employee.employee.controllers;

import com.demo.employee.employee.models.Employee;
import com.demo.employee.employee.services.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeServiceImpl employeeService;

    @GetMapping("")
    List<Employee> findAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @PostMapping("")
    ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Employee> findEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Employee> deleteEmployeeById(@PathVariable Long id) {
        Employee deletedEmployee = employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(deletedEmployee, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }
}
