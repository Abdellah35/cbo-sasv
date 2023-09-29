package com.cbo.core.service.impl;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.exception.NoSuchUserExistsException;
import com.cbo.core.persistence.model.Employee;
import com.cbo.core.persistence.repository.EmployeeRepository;
import com.cbo.core.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ResultWrapper<List<Employee>> getEmployees() {
        ResultWrapper<List<Employee>> resultWrapper = new ResultWrapper<>();
        List<Employee> employees = employeeRepository.findAll();
        if (!employees.isEmpty()) {
            resultWrapper.setStatus(true);
            resultWrapper.setResult(employees);
        } else {
            resultWrapper.setStatus(false);
        }
        return resultWrapper;
    }

    @Override
    public ResultWrapper<Employee> getEmployeeById(Long id) {
        ResultWrapper<Employee> resultWrapper = new ResultWrapper<>();

        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            resultWrapper.setStatus(false);
            resultWrapper.setMessage("Employee not found");
        } else {
            resultWrapper.setStatus(true);
            resultWrapper.setResult(employee);
        }
        return null;
    }


    public List<Employee> findAllEmployee() {
        return employeeRepository.findAll();
    }


    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }


}
