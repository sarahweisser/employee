package com.demo.employee.employee.controllers;

import com.demo.employee.employee.models.Employee;
import com.demo.employee.employee.repositories.EmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeJpaRepository employeeJpaRepository;

    @GetMapping("")
    Iterable<Employee> findAllEmployees() {
        return employeeJpaRepository.findAll();
    }

    @PostMapping("")
    Employee createEmployee(@RequestBody Employee employee) {
        Employee resp = employeeJpaRepository.saveAndFlush(employee);
        // TODO handle validation
        // TODO handle sys error
        return resp;
    }

    @GetMapping("/{id}")
    Optional<Employee> findEmployeeById(@PathVariable Long id) {
        Optional<Employee> resp = employeeJpaRepository.findById(id);
        return resp;
    }

}
