package com.demo.employee.employee.repositories;

import com.demo.employee.employee.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
    //Optional<Employee> findByEmployeeId(Long emloyeeId);
}
