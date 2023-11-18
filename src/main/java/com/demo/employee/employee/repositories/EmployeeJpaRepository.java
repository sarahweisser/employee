package com.demo.employee.employee.repositories;

import com.demo.employee.employee.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
}
