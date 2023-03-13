package com.cbo.core.service;

import com.cbo.core.exception.NoSuchUserExistsException;
import com.cbo.core.exception.ResourceNotFoundException;
import com.cbo.core.model.Division;
import com.cbo.core.model.Employee;
import com.cbo.core.repo.EmployeeRepository;
import com.cbo.core.utility.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService{
    private final EmployeeRepository employeeRepository;

    private final DivisionService divisionService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, DivisionService divisionService) {
        this.employeeRepository = employeeRepository;
        this.divisionService = divisionService;
    }

    public Employee addEmployee(Employee employee, MultipartFile signature, Long divisionId){
        System.out.println(employee.getSignatureImage());
        Employee savedEmployee = null;
        if (employee.getId() != null){
            savedEmployee = employeeRepository.findById(employee.getId()).orElse(null);
        }
        if (savedEmployee != null)
            throw new ResourceNotFoundException("Employee Already exist");
        else {
            if (divisionId != null) {
                Division division = divisionService.findDivisionById(divisionId);
                employee.setDivision(division);
            } else
                throw new ResourceNotFoundException(
                        "Division Not Found. Change Id and try again.");

            if(!signature.isEmpty()){
                employee.setSignatureImage(employee.getSignatureImage());
                Employee finalEmp = employeeRepository.save(employee);
                try{
                    String uploadDir = "user-photos/employee/" + finalEmp.getId();
                    System.out.println(uploadDir);
                    FileUploadUtil.saveFile(uploadDir, employee.getSignatureImage(), signature);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else{
                employeeRepository.save(employee);

            }
            return employee;
        }

    }

    public List<Employee> findAllEmployee(){
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(Employee employee, MultipartFile signatureImage, Long divisionId){

        System.out.println("Division Id " + divisionId);
        Employee oldEmployee = employeeRepository.findById(employee.getId()).orElse(null);
        if (oldEmployee == null)
            throw new NoSuchUserExistsException("No Such Employee exists!!");
        else {

            if (divisionId != null){
                Division division = divisionService.findDivisionById(divisionId);
                oldEmployee.setDivision(division);

            }
            else
                throw new ResourceNotFoundException(
                        "Division Not Found. Change Id and try again.");

            oldEmployee.setEmail(employee.getEmail());
            oldEmployee.setGivenName(employee.getGivenName());
            oldEmployee.setFatherName(employee.getFatherName());
            oldEmployee.setGrandFatherName(employee.getGrandFatherName());
            oldEmployee.setPosition(employee.getPosition());
            oldEmployee.setPhoneNumber(employee.getPhoneNumber());

            if((oldEmployee.getSignatureImage() != null) && !signatureImage.isEmpty()){
                try{
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
                    LocalDateTime now = LocalDateTime.now();

                    Path source = Paths.get("user-photos/employee/"+ oldEmployee.getId() +"/"+ oldEmployee.getSignatureImage());
                    Files.move(source, source.resolveSibling("old_"+dtf.format(now)+"_"+oldEmployee.getSignatureImage()));

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }
            if(!signatureImage.isEmpty()){
                oldEmployee.setSignatureImage(employee.getSignatureImage());
                Employee finalEmp = employeeRepository.save(oldEmployee);
                try{
                    String uploadDir = "user-photos/employee/" + finalEmp.getId();
                    System.out.println(uploadDir);
                    FileUploadUtil.saveFile(uploadDir, employee.getSignatureImage(), signatureImage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else{
                employeeRepository.save(oldEmployee);

            }

            employeeRepository.save(oldEmployee);

            return oldEmployee;
        }
    }

    public Employee findEmployeeById(Long id){
        return employeeRepository.findById(id).orElseThrow(() -> new NoSuchUserExistsException("NO Employee PRESENT WITH ID = " + id));
    }
    public String deleteEmployee(Long id){

        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if (existingEmployee == null)
            throw new NoSuchUserExistsException("No Such Employee exists!!");
        else {
            employeeRepository.deleteById(id);

            return "Record deleted Successfully";
        }
    }

}
