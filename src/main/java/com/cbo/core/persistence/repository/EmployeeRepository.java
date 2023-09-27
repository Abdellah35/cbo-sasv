package com.cbo.core.persistence.repository;

import com.cbo.core.persistence.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
