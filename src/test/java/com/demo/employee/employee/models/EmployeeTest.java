package com.demo.employee.employee.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class EmployeeTest {
    private LocalDate birthDate = LocalDate.of(1980, 1, 14);
    private LocalDate oldStartDate = LocalDate.of(2012, 1, 15);

    Employee existingEmployee =
            new Employee(2L, "FIRST_NAME_2", "LAST_NAME_2", "EMPLOYEE_2@gmail.com",
                    "0987654321", birthDate, "TITLE_2", "DEPARTMENT_2",
                    "LOCATION_2", oldStartDate, 115L, "REPORTING_MANAGER_2");

    @Test
    @DisplayName("testEmployeeConstructor")
    void testEmployeeConstructor() {
        Assertions.assertEquals(2L, existingEmployee.getId());
    }
}
