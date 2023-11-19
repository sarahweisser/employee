package com.demo.employee.employee.services;

import com.demo.employee.employee.exceptions.EmailNotUniqueException;
import com.demo.employee.employee.exceptions.EmployeeNotFoundException;
import com.demo.employee.employee.models.Employee;
import com.demo.employee.employee.repositories.EmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeJpaRepository employeeJpaRepository;

    @Override
    public List<Employee> findAllEmployees() {
        return employeeJpaRepository.findAll();
    }

    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        Optional<Employee> resp = employeeJpaRepository.findById(id);
        if (resp.isPresent()) {
            return resp;
        } else {
            throw new EmployeeNotFoundException();
        }
    }

    @Override
    public Employee createEmployee(Employee employee) {
        Optional<Employee> employeeToCreate = employeeJpaRepository.findEmployeeByEmail(employee.getEmail());
        if (employeeToCreate.isPresent()) {
            throw new EmailNotUniqueException();
        } else {
            Employee employeeCreated = employeeJpaRepository.saveAndFlush(employee);
            return employeeCreated;
        }
    }

    @Override
    public Employee updateEmployee(Employee employeeToUpdate) {
        Optional<Employee> existingEmployee = employeeJpaRepository.findEmployeeByEmail(employeeToUpdate.getEmail());
        if (existingEmployee.isPresent()) {
            // TODO validate other input according to business logic
            return employeeJpaRepository.saveAndFlush(employeeToUpdate);
        } else {
            throw new EmployeeNotFoundException();
        }


    }

    @Override
    public Employee deleteEmployeeById(Long id) {
        Optional<Employee> employeeToDelete = employeeJpaRepository.findById(id);
        if (employeeToDelete.isPresent()) {
            employeeJpaRepository.deleteById(id);
            return employeeToDelete.get();
        } else {
            throw new EmployeeNotFoundException();
        }
    }

//    @ExceptionHandler(EmployeeNotFoundException.class)
//    public ResponseEntity<Employee> handleExceptions(Exception e) {
//        System.out.println("HEY!!!");
//        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//    }
}
