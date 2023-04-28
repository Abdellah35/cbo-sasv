package com.cbo.core.controller;

import com.cbo.core.model.Division;
import com.cbo.core.model.Employee;
import com.cbo.core.model.User;
import com.cbo.core.service.DivisionService;
import com.cbo.core.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final DivisionService divisionService;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(DivisionService divisionService, EmployeeService employeeService) {
        this.divisionService = divisionService;
        this.employeeService = employeeService;
    }


    //This method will return all Employees on the database
    @GetMapping("/all")
    @ApiOperation(value = "List all Employees",
            notes = "List of all Employees",
            response = Employee.class)
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR')")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        List<Employee> employees = employeeService.findAllEmployee();
        return ResponseEntity.ok(employees);
    }


    //This method used to get a specific Employee By Id
    @GetMapping("/find/{id}")
    @ApiOperation(value = "Finds Employees by id",
            notes = "Provide an id to look up specific employee from the Employee table",
            response = Employee.class)
    @PreAuthorize("hasAnyRole('ADMIN','USER','DIRECTOR')")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id){
        Employee specEmpl = employeeService.findEmployeeById(id);

        return ResponseEntity.ok(specEmpl);
    }


    //This Function will create a new Employee row on database entity with provided info
    @PostMapping("/add")
    @ApiOperation(value = "Add new Employee",
            notes = "When you create an Employee u need to provide Division Id",
            response = Employee.class)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> addEmployee(@RequestAttribute @Valid
                                    @RequestParam(name="signatureImage", required = false) MultipartFile signatureImage,
                                                @RequestParam("givenName") String givenName,
                                                @RequestParam("fatherName") String fatherName,
                                                @RequestParam("grandFatherName") String grandFatherName,
                                                @RequestParam("position") String position,
                                                @RequestParam(name="email", required = false) String email,
                                                @RequestParam(name="phoneNumber", required = false) String phoneNumber,
                                                @RequestParam("divisionId") String divisionId,
                                                @RequestParam("birthDate") Date birthDate,
                                                @RequestParam("cboEmail") String cboEmail,
                                                @RequestParam("gender") String gende
                                ){

        System.out.println("in add employee");


        Employee employee1 = new Employee();
        employee1.setEmail(email);
        employee1.setPosition(position);
        employee1.setGivenName(givenName);
        employee1.setFatherName(fatherName);
        employee1.setGrandFatherName(grandFatherName);
        employee1.setPhoneNumber(phoneNumber);
        employee1.setCboEmail(cboEmail);
        employee1.setGender(gende);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println(formatter.format(birthDate));

        employee1.setBirthDate(formatter.format(birthDate));
        if(signatureImage != null){
            String signatureName = StringUtils.cleanPath(signatureImage.getOriginalFilename());
            employee1.setSignatureImage(signatureName);

        }

        long l = 0L;
        if(!divisionId.equals("") && !divisionId.isEmpty() && !divisionId.equals("undefined")){
            l=Long.parseLong(divisionId);
        }
        Employee newEmp=  employeeService.addEmployee(employee1, signatureImage, l);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newEmp.getId()).toUri();



        return ResponseEntity.created(location).body(newEmp);
    }


    //This method used to update an existing Employee on database
    @PutMapping("/update")
    @ApiOperation(
            value = "Update Existing Employee",
            notes = "All fields can be updated except Employee Id",
            response = Employee.class)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateEmployee(@RequestAttribute @Valid
                                   @RequestParam(name="signatureImage", required = false) MultipartFile signatureImage,
                               @RequestParam("givenName") String givenName,
                               @RequestParam("fatherName") String fatherName,
                               @RequestParam("grandFatherName") String grandFatherName,
                               @RequestParam("position") String position,
                               @RequestParam("email") String email,
                               @RequestParam("phoneNumber") String phoneNumber,
                               @RequestParam("id") String id,
                               @RequestParam("divisionId") String divisionId,
                               @RequestParam("birthDate") Date birthDate,
                               @RequestParam("gender") String gender){

        Employee employee1 = new Employee();
        if(signatureImage != null){
            String signatureName = StringUtils.cleanPath(signatureImage.getOriginalFilename());
            employee1.setSignatureImage(signatureName);
        }
        long l = 0L;
        if(!divisionId.equals("") && !divisionId.isEmpty() && !divisionId.equals("undefined")){
            l=Long.parseLong(divisionId);
        }
        employee1.setId(Long.parseLong(id));
        employee1.setEmail(email);
        employee1.setPosition(position);
        employee1.setGivenName(givenName);
        employee1.setFatherName(fatherName);
        employee1.setGrandFatherName(grandFatherName);
        employee1.setPhoneNumber(phoneNumber);
        employee1.setGender(gender);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(formatter.format(birthDate));


        employee1.setBirthDate(formatter.format(birthDate));
        employeeService.updateEmployee(employee1, signatureImage, l);
        return ResponseEntity.noContent().build();
    }


    //This method used to delete an employee with the given id
    @DeleteMapping("/delete/{id}")
@ApiOperation(value = "Delete Existing Employee",
            notes = "Delete Employee by Id",
            response = Employee.class)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id){

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }


}
