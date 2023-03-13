package com.cbo.core.service;

import com.cbo.core.exception.*;
import com.cbo.core.model.*;
import com.cbo.core.repo.AuthorityDbRepository;
import com.cbo.core.repo.DivisionRepository;
import com.cbo.core.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AuthorityDbService {

    private final AuthorityDbRepository authorityRepo;

    private final EmployeeRepository employeeRepository;

    private final DivisionRepository divisionRepository;


    @Autowired
    public AuthorityDbService(AuthorityDbRepository authorityRepo, EmployeeRepository employeeRepository, DivisionRepository divisionRepository) {
        this.authorityRepo = authorityRepo;

        this.employeeRepository = employeeRepository;
        this.divisionRepository = divisionRepository;

    }

public AuthorityDB addAuthority(Long divisionId, Long employeeId) throws IOException {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    Employee employee = employeeRepository.findById(employeeId).orElse(null);
    Division division = divisionRepository.findDivisionById(divisionId).orElse(null);

    //this checks if employee is not null
    if(employee == null)
        throw new NoSuchUserExistsException("No Such Employee exists!!");
    else if (division == null) {
        //throws exception if division is not present with the provided id
        throw new NoSuchUserExistsException("No Such Division exists!!");
    } else if (employee.getDivision() != null) {
        //throws exception if the division and division of the employee are differ
        if (employee.getDivision().getId() != divisionId)
            throw new NoSuchUserExistsException("This Employee is not part of the provided division!");
    }

    AuthorityDB authDb = authorityRepo.findAuthorityDBByDivisionAndIsActive(division, true).orElse(null);
    if (authDb != null){
        authDb.setIsActive(false);
        authDb.setUpdatedAt(dtf.format(now));
        authorityRepo.save(authDb);
    }

    if (employee != null) {

        if((employee.getSignatureImage() == null))
            throw new ImageNotFoundException("Signature Image is not found for this Employee");
        else if((division.getStampImage() == null))
            throw new ImageNotFoundException("Stamp Image is not found for this Division");
        else {

            AuthorityDB authorityD = new AuthorityDB( division,employee, true);
            authorityD.setDivision(division);
            authorityD.setEmployee(employee);
            authorityD.setIsActive(true);
            authorityD.setCreatedAt(dtf.format(now));
            AuthorityDB newauthority = authorityRepo.save(authorityD);
            return newauthority;
        }

    }
    else
        throw new NoSuchUserExistsException("No Such Employee exists!!");

}
    public List<AuthorityDB> findAllAuthority(){

        return authorityRepo.findAll();
    }
    public List<AuthorityDB> findAllAuthorityByState(boolean isActive){

        return authorityRepo.findAllByIsActive(isActive);
    }
    public AuthorityDB updateAuthority(Long id, Boolean isActive) {

        AuthorityDB oldAuthority = authorityRepo.findById(id).orElse(null);
        if (oldAuthority == null)
            throw new NoSuchUserExistsException("No Such Authority exists!!");
        else {
                oldAuthority.setIsActive(isActive);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                oldAuthority.setUpdatedAt(dtf.format(now));

                AuthorityDB auth0 = authorityRepo.save(oldAuthority);

                return auth0;
        }
    }

    public AuthorityDB findAuthorityById(Long id){
        return authorityRepo.findById(id).orElseThrow(() -> new NoSuchUserExistsException("Authority By id "+ id + " was not found"));
    }
    public String deleteAuthority(Long id){

        AuthorityDB oldAuthority = authorityRepo.findById(id).orElse(null);
        if (oldAuthority == null)
            throw new NoSuchUserExistsException("No Such Authority exists!!");
        else {
            authorityRepo.deleteById(id);

            return "Record deleted Successfully";
        }
    }
}
