package com.cbo.core.persistence.repository;

import com.cbo.core.persistence.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(" SELECT EMP FROM Employee EMP WHERE EMP.process.id = :processId" +
            " AND EMP.subProcess.id = null")
    List<Employee> getAllEmployeesByProcessId(@Param("processId") Long processId);

    @Query(" SELECT EMP FROM Employee EMP WHERE EMP.subProcess.id = :subProcessId")
    List<Employee> getAllEmployeesBySubProcessId(@Param("subProcessId") Long subProcessId);

    @Query ("SELECT e FROM Employee e WHERE e.employeeFullName LIKE :name%") // JPQL
    List<Employee> findEmployeesByName(@Param("name") String name, Pageable pageable) ;

    @Query (  value ="SELECT * FROM Employee e WHERE e.employeeFullName LIKE :name%", // native SQL
    nativeQuery = true)
    List<Employee> findEmployeesByNameNative(@Param ("name") String name) ;

}
