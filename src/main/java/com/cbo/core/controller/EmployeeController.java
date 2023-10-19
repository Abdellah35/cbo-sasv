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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = URIS.EMPLOYEE_LIST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_USER')")
    public ResponseEntity<ResultWrapper<List<Employee>>> getEmployees() {
        ResultWrapper<List<Employee>> resultWrapper = employeeService.getEmployees();

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @GetMapping(value = URIS.EMPLOYEE_BY_PROCESS_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultWrapper<List<Employee>>> getEmployeesByProcessId(@PathVariable("processId") Long processId) {
        ResultWrapper<List<Employee>> resultWrapper = employeeService.getEmployeesByProcessId(processId);

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @GetMapping(value = URIS.EMPLOYEE_BY_SUB_PROCESS_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultWrapper<List<Employee>>> getEmployeesBySubProcessId(@PathVariable("subProcessId") Long subProcessId) {
        ResultWrapper<List<Employee>> resultWrapper = employeeService.getEmployeesBySubProcessId(subProcessId);

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @GetMapping(value = URIS.EMPLOYEE_BY_NAME,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultWrapper<List<Employee>>> getEmployeesByName(@RequestParam("nameStart") String nameStart) {
        ResultWrapper<List<Employee>> resultWrapper = employeeService.getEmployeesByName(nameStart);

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @GetMapping(value = URIS.EMPLOYEE_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_USER')")
    public ResponseEntity<ResultWrapper<Employee>> getEmployeeById(@PathVariable("employeeId") Long employeeId) {
        ResultWrapper<Employee> resultWrapper = employeeService.getEmployeeById(employeeId);

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

}
