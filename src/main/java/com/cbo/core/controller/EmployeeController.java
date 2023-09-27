package com.cbo.core.controller;

import com.cbo.core.constants.URIS;
import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.Employee;
import com.cbo.core.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping(value = URIS.EMPLOYEE_LIST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_VIEW')")
    public ResponseEntity<ResultWrapper<List<Employee>>> getEmployees() {
        ResultWrapper<List<Employee>> resultWrapper = employeeService.getEmployees();

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @GetMapping(value = URIS.EMPLOYEE_BY_ID, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_VIEW')")
    public ResponseEntity<ResultWrapper<Employee>> getEmployeeById(@PathVariable("employeeId") Long employeeId) {
        ResultWrapper<Employee> resultWrapper = employeeService.getEmployeeById(employeeId);

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

}
