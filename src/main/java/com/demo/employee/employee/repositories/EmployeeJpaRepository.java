package com.demo.employee.employee.repositories;

import com.demo.employee.employee.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findEmployeeByEmail(String email);
}
