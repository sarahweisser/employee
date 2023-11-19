package com.demo.employee.employee.controllers;

import com.demo.employee.employee.models.Employee;
import com.demo.employee.employee.models.EmployeeResponse;
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
    ResponseEntity<EmployeeResponse> createEmployee(@RequestBody Employee employee) {
        EmployeeResponse createdEmployee = new EmployeeResponse(employeeService.createEmployee(employee), null);
        return new ResponseEntity<>(createdEmployee, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<EmployeeResponse> findEmployeeById(@PathVariable Long id) {
        EmployeeResponse employee = new EmployeeResponse(employeeService.findEmployeeById(id).get(), null);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EmployeeResponse> deleteEmployeeById(@PathVariable Long id) {
        EmployeeResponse deletedEmployee = new EmployeeResponse(employeeService.deleteEmployeeById(id), null);
        return new ResponseEntity<>(deletedEmployee, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<EmployeeResponse> updateEmployee(@RequestBody Employee employee) {
        EmployeeResponse updatedEmployee = new EmployeeResponse(employeeService.updateEmployee(employee), null);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }
}
