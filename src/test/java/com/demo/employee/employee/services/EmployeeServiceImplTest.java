package com.demo.employee.employee.services;

import com.demo.employee.employee.exceptions.EmailNotUniqueException;
import com.demo.employee.employee.exceptions.EmployeeNotFoundException;
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
public class EmployeeServiceImplTest {
    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    @Autowired
    private EmployeeServiceImpl employeeService;

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
        Assertions.assertEquals(1, employeeService.findAllEmployees().size());
    }

    @Test
    @DisplayName("testFindEmployeeById")
    void testFindEmployeeById() {
        Optional<Employee> employee = employeeJpaRepository.findEmployeeByEmail(existingEmployee.getEmail());
        if (employee.isPresent()) {
            Employee existingEmployee = employeeService.findEmployeeById(employee.get().getId()).get();
            Assertions.assertEquals(existingEmployee.getEmployeeId(), employee.get().getEmployeeId());
        }
    }
    @Test
    @DisplayName("testCreateEmployee")
    void testCreateEmployee() {
        Employee createdEmployee = employeeService.createEmployee(newEmployee);
        Employee retrievedEmployee = employeeService.findEmployeeById(createdEmployee.getId()).get();

        Assertions.assertEquals(newEmployee.getEmail(), retrievedEmployee.getEmail());
        Assertions.assertEquals(createdEmployee.getEmail(), retrievedEmployee.getEmail());
    }

    @Test
    @DisplayName("testCreateEmployeeNonUniqueEmail")
    void testCreateEmployeeNonUniqueEmail() {
        Assertions.assertThrows(EmailNotUniqueException.class, () -> {
            Employee createdEmployee = employeeService.createEmployee(nonUniqueEmailEmployee);
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
            Employee updatedEmployee = employeeService.updateEmployee(employeeUpdate);
            Employee retrievedEmployee = employeeService.findEmployeeById(existingEmployee.get().getId()).get();

            Assertions.assertEquals("LAST_NAME_2_UPDATED", retrievedEmployee.getLastName());
        }
    }

    @Test
    @DisplayName("testUpdateEmployeeNotFound")
    void testUpdateEmployeeNotFound() {
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            Employee updatedEmployee = employeeService.updateEmployee(newEmployee);
        });
    }

    @Test
    @DisplayName("testDeleteEmployeeById")
    void testDeleteEmployeeById() {
        // create specific employee to delete is in database
        Employee employeeToDelete = employeeService.createEmployee(newEmployee);
        // confirm employee is created
        Employee retrievedEmployee = employeeService.findEmployeeById(employeeToDelete.getId()).get();
        employeeService.deleteEmployeeById(retrievedEmployee.getId());
        // assert that same employee is now gone
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            Employee deletedEmployee = employeeService.findEmployeeById(retrievedEmployee.getId()).get();
        });
    }

    @Test
    @DisplayName("testDeleteEmployeeByIdNotFound")
    void testDeleteEmployeeByIdNotFound() {
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            Employee deletedEmployee = employeeService.deleteEmployeeById(5000L);
        });
    }

    private void addEmployees() {
        employeeJpaRepository.saveAndFlush(existingEmployee);
    }


}
