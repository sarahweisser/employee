package com.demo.employee.employee.controllers;

import com.demo.employee.employee.exceptions.EmailNotUniqueException;
import com.demo.employee.employee.exceptions.EmployeeNotFoundException;
import com.demo.employee.employee.models.Employee;
import com.demo.employee.employee.models.EmployeeResponse;
import com.demo.employee.employee.repositories.EmployeeJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class EmployeeControllerTest {
    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    @Autowired
    private EmployeeController employeeController;

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

    Employee nonUniqueEmailEmployee =
            new Employee(3L, "FIRST_NAME_3", "LAST_NAME_3", "EMPLOYEE_2@gmail.com",
                    "1098765432", birthDate, "TITLE_3", "DEPARTMENT_3",
                    "LOCATION_3", oldStartDate, 116L, "REPORTING_MANAGER_3");

    @BeforeEach
    public void resetData() {
        employeeJpaRepository.deleteAll();
        addEmployees();
    }

    @Test
    @DisplayName("testFindAllEmployees")
    void testFindAllEmployees() {
        Assertions.assertEquals(1, employeeController.findAllEmployees().size());
    }

    @Test
    @DisplayName("testFindEmployeeById")
    void testFindEmployeeById() {
        Optional<Employee> employee = employeeJpaRepository.findEmployeeByEmail(existingEmployee.getEmail());
        if (employee.isPresent()) {
            ResponseEntity<EmployeeResponse> existingEmployee = employeeController.findEmployeeById(employee.get().getId());
            Assertions.assertEquals(existingEmployee.getStatusCode(), HttpStatus.OK);
            Assertions.assertEquals(existingEmployee.getBody().getResponse().getEmail(), employee.get().getEmail());
        }
    }

    @Test
    @DisplayName("testFindEmployeeByIdNotFound")
    void testFindEmployeeByIdNotFound() {
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            ResponseEntity<EmployeeResponse> nonEmployee = employeeController.findEmployeeById(5001L);
        });
    }

    @Test
    @DisplayName("testCreateEmployee")
    void testCreateEmployee() {
        ResponseEntity<EmployeeResponse> createdEmployee = employeeController.createEmployee(newEmployee);
        ResponseEntity<EmployeeResponse> retrievedEmployee = employeeController.findEmployeeById(createdEmployee.getBody().getResponse().getId());

        Assertions.assertEquals(newEmployee.getEmail(), retrievedEmployee.getBody().getResponse().getEmail());
        Assertions.assertEquals(createdEmployee.getBody().getResponse().getEmail(), retrievedEmployee.getBody().getResponse().getEmail());
    }

    @Test
    @DisplayName("testCreateEmployeeNonUniqueEmail")
    void testCreateEmployeeNonUniqueEmail() {
        Assertions.assertThrows(EmailNotUniqueException.class, () -> {
            ResponseEntity<EmployeeResponse> createdEmployee = employeeController.createEmployee(nonUniqueEmailEmployee);
        });
    }

    @Test
    @DisplayName("testUpdateEmployee")
    void testUpdateEmployee() {
        Optional<Employee> existingEmployee = employeeJpaRepository.findEmployeeByEmail("EMPLOYEE_2@gmail.com");
        if (existingEmployee.isPresent()) {
            Employee employeeUpdate =
                    new Employee(existingEmployee.get().getId(), "FIRST_NAME_2_UPDATED", "LAST_NAME_2_UPDATED", "EMPLOYEE_2@gmail.com",
                            "0987654321", birthDate, "TITLE_2", "DEPARTMENT_2",
                            "LOCATION_2", oldStartDate, 115L, "REPORTING_MANAGER_2");
            ResponseEntity<EmployeeResponse> updatedEmployee = employeeController.updateEmployee(employeeUpdate);
            ResponseEntity<EmployeeResponse> retrievedEmployee = employeeController.findEmployeeById(existingEmployee.get().getId());

            Assertions.assertEquals("LAST_NAME_2_UPDATED", retrievedEmployee.getBody().getResponse().getLastName());
        }
    }

    @Test
    @DisplayName("testUpdateEmployeeNotFound")
    void testUpdateEmployeeNotFound() {
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            ResponseEntity<EmployeeResponse> updatedEmployee = employeeController.updateEmployee(newEmployee);
        });
    }

    @Test
    @DisplayName("testDeleteEmployeeById")
    void testDeleteEmployeeById() {
        // create specific employee to delete is in database
        ResponseEntity<EmployeeResponse> employeeToDelete = employeeController.createEmployee(newEmployee);
        // confirm employee is created
        ResponseEntity<EmployeeResponse> retrievedEmployee = employeeController.findEmployeeById(employeeToDelete.getBody().getResponse().getId());
        ResponseEntity<EmployeeResponse> deletedEmployee = employeeController.deleteEmployeeById(retrievedEmployee.getBody().getResponse().getId());
        // assert that same employee is now gone
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            ResponseEntity<EmployeeResponse> retrieveDeletedEmployee = employeeController.findEmployeeById(deletedEmployee.getBody().getResponse().getId());
        });
    }

    @Test
    @DisplayName("testDeleteEmployeeByIdNotFound")
    void testDeleteEmployeeByIdNotFound() {
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            ResponseEntity<EmployeeResponse> deletedEmployee = employeeController.deleteEmployeeById(5000L);
        });
    }

    private void addEmployees() {
        employeeJpaRepository.saveAndFlush(existingEmployee);
    }
}