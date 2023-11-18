package com.demo.employee.employee.controllers;

import com.demo.employee.employee.models.Employee;
import com.demo.employee.employee.repositories.EmployeeJpaRepository;
import org.junit.jupiter.api.Assertions;
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

    private LocalDate birthDate = LocalDate.of(1980, 1, 14);

    private LocalDate startDate = LocalDate.of(2012, 11, 30);

    @Test
    @DisplayName("testCreateEmployee")
    void testCreateEmployee() {
        Employee newEmployee =
            new Employee(1L, "FIRST_NAME_1", "LAST_NAME_1", "EMPLOYEE_1@gmail.com",
                    "9876543210", birthDate , "TITLE_1", "DEPARTMENT_1",
                    "LOCATION_1", startDate, 114L, "REPORTING_MANAGER_1");

        Employee createdEmployee = employeeController.createEmployee(newEmployee);
        Optional<Employee> retrievedEmployee = employeeController.findEmployeeById(newEmployee.getId());

        if (retrievedEmployee.isPresent()) {
            Assertions.assertEquals(newEmployee.getEmail(), retrievedEmployee.get().getEmail());
        }
    }
}
