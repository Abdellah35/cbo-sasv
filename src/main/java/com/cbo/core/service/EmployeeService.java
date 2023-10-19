package com.cbo.core.service;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.Employee;

import java.util.List;

public interface EmployeeService {

    ResultWrapper<List<Employee>> getEmployees();

    ResultWrapper<List<Employee>> getEmployeesByProcessId(Long processId);

    ResultWrapper<List<Employee>> getEmployeesByName(String namePattern);

    ResultWrapper<List<Employee>> getEmployeesBySubProcessId(Long subProcessId);

    ResultWrapper<Employee> getEmployeeById(Long id);

    Employee findEmployeeById(Long id);
}
