package com.cbo.core.repo;

import com.cbo.core.model.Division;
import com.cbo.core.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findEmployeeById(Long id);

    List<Employee> findEmployeeByDivision(Division division);

    List<Employee> findEmployeeByGender(String gender);
}
