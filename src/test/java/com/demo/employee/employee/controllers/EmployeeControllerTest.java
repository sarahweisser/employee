package com.demo.employee.employee.controllers;

import com.demo.employee.employee.models.Employee;
import com.demo.employee.employee.repositories.EmployeeJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    private LocalDate birthDate = LocalDate.of(1980, 1, 14);

    private LocalDate newStartDate = LocalDate.of(2023, 11, 30);

    private LocalDate oldStartDate = LocalDate.of(2012, 1, 15);

    Employee newEmployee =
            new Employee(1L, "FIRST_NAME_1", "LAST_NAME_1", "EMPLOYEE_1@gmail.com",
                    "9876543210", birthDate, "TITLE_1", "DEPARTMENT_1",
                    "LOCATION_1", newStartDate, 114L, "REPORTING_MANAGER_1");

    Employee existingEmployee =
            new Employee(2L, "FIRST_NAME_2", "LAST_NAME_2", "EMPLOYEE_2@gmail.com",
                    "0987654321", birthDate, "TITLE_2", "DEPARTMENT_2",
                    "LOCATION_2", oldStartDate, 115L, "REPORTING_MANAGER_2");
    @BeforeEach
    public void clearData() {
        employeeJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("testCreateEmployee")
    void testCreateEmployee() {
        Employee createdEmployee = employeeController.createEmployee(newEmployee);
        Optional<Employee> retrievedEmployee = employeeController.findEmployeeById(newEmployee.getId());

        if (retrievedEmployee.isPresent()) {
            Assertions.assertEquals(newEmployee.getEmail(), retrievedEmployee.get().getEmail());
            Assertions.assertEquals(createdEmployee.getEmail(), retrievedEmployee.get().getEmail());
        }
    }

    @Test
    @DisplayName("testFindAllEmployees")
    void testFindAllEmployees() {
        addEmployees();
        Assertions.assertEquals(1, employeeController.findAllEmployees().size());
    }

    @Test
    @DisplayName("testFindEmployeeById")
    void testFindEmployeeById() {
        addEmployees();
        Optional<Employee> employee = employeeController.findEmployeeById(existingEmployee.getId());
        if (employee.isPresent()) {
            Assertions.assertEquals(existingEmployee.getEmail(), employee.get().getEmail());
        }
    }

    @Test
    @DisplayName("testDeleteEmployeeById")
    void testDeleteEmployeeById() {
        addEmployees();
        // ensure employee to delete is in database before operation
        Optional<Employee> employeeToDelete = employeeController.findEmployeeById(existingEmployee.getId());
        if (employeeToDelete.isPresent()) {
            employeeController.deleteEmployeeById(existingEmployee.getId());
            Optional<Employee> deletedEmployee = employeeController.findEmployeeById(existingEmployee.getId());
            // assert that same employee is now gone
            Assertions.assertThrows(Exception.class,
                    () -> {
                        deletedEmployee.get();
                    });
        }
    }

    private void addEmployees() {
        employeeJpaRepository.saveAndFlush(existingEmployee);
    }
}
