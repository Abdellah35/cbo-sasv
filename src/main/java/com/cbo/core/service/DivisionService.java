package com.cbo.core.service;

import com.cbo.core.exception.NoSuchUserExistsException;
import com.cbo.core.exception.ResourceNotFoundException;
import com.cbo.core.exception.UserAlreadyExistsException;
import com.cbo.core.model.Division;
import com.cbo.core.model.Employee;
import com.cbo.core.repo.DivisionRepository;
import com.cbo.core.repo.EmployeeRepository;
import com.cbo.core.utility.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DivisionService {

    private final DivisionRepository divisionRepository;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public DivisionService(DivisionRepository divisionRepository, EmployeeRepository employeeRepository) {
        this.divisionRepository = divisionRepository;
        this.employeeRepository = employeeRepository;
    }


public Division addDivision(Division division, MultipartFile stamp, Long parentId) throws IOException {
    Division savedD = new Division();

    Division existingDivision = null;
    if(division.getId() != null){
        existingDivision = divisionRepository.findById(division.getId()).orElse(null);

    }
    List<Division> divByName = divisionRepository.findDivisionByName(division.getName());
    for (int i=0; i < divByName.size(); i++){
        if(divByName != null && divByName.get(i).getParent().getId() == parentId)
            throw new UserAlreadyExistsException("Division already exists!");
    }

        if (existingDivision != null)
            throw new UserAlreadyExistsException("Division already exists!");
        else {
            if(parentId != null && parentId != 0){
                Division parent = divisionRepository.findById(parentId).orElse(null);
                if (parent == null)
                    throw new NoSuchUserExistsException("No Such Division exists!");
                else if (parent.getName().equals(division.getName())) {
                    throw new NoSuchUserExistsException("The division name and parent division name cannot be the same.");

                } else {

                    savedD.setParent(parent);
                }
            }
            savedD.setName(division.getName());
            if(stamp != null){
                savedD.setStampImage(division.getStampImage());
                Division weSave = divisionRepository.save(savedD);
                String uploadDir = "user-photos/division/" + weSave.getId();
                System.out.println(uploadDir);
                FileUploadUtil.saveFile(uploadDir, division.getStampImage(), stamp);
                return weSave;
            }else{
                Division weSave = divisionRepository.save(savedD);
                return weSave;

            }




        }

}


public List<Division> findAllDivision(){
    return divisionRepository.findAll();
}


public Division updateDivision(Division division, MultipartFile stampImage, Long parentId){

    Division oldDivision = divisionRepository.findById(division.getId()).orElse(null);
    List<Division> divByName = divisionRepository.findDivisionByName(division.getName());

    if (oldDivision == null)
        throw new NoSuchUserExistsException("No such division exists!");
    else {
        oldDivision.setName(division.getName());
        if(parentId != null){
            Division parent = divisionRepository.findById(parentId).orElse(null);
            if (parent == null)
                throw new NoSuchUserExistsException("Parent: No such division exists!");
            else {
                oldDivision.setParent(parent);
            }
        }

        if(stampImage != null){
            if(oldDivision.getStampImage() != null){
                try{

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
                    LocalDateTime now = LocalDateTime.now();

                    Path source = Paths.get("user-photos/division/"+ oldDivision.getId() +"/"+ oldDivision.getStampImage());
                    Files.move(source, source.resolveSibling("old_"+dtf.format(now)+"_"+oldDivision.getStampImage()));

                } catch (Exception e) {
                    throw new ResourceNotFoundException(e.getMessage());
                }


            }
            try{
                oldDivision.setStampImage(division.getStampImage());
                Division weSave = divisionRepository.save(oldDivision);
                String uploadDir = "user-photos/division/" + weSave.getId();
                System.out.println(uploadDir);
                FileUploadUtil.saveFile(uploadDir, division.getStampImage(), stampImage);
                return weSave;
            } catch (Exception e) {
                throw new ResourceNotFoundException(e.getMessage());
            }

        }else{
            Division weSave = divisionRepository.save(oldDivision);
            return weSave;

        }
    }
}


public Division findDivisionById(Long id){
    return divisionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Division By id "+ id + " was not found"));
}


public String deleteDivision(Long id){

    Division existingDivision = divisionRepository.findById(id).orElse(null);
    if (existingDivision == null)
        throw new NoSuchUserExistsException("No such division exists!");
    else {
        List<Division> childDivision = divisionRepository.findDivisionByParent(existingDivision);
        List<Employee> employeesInDiv  =  employeeRepository.findEmployeeByDivision(existingDivision);
        if (childDivision.isEmpty() && employeesInDiv.isEmpty()){
            divisionRepository.deleteById(id);

            return "Record deleted Successfully";
        }else if (!childDivision.isEmpty()){
            throw new NoSuchUserExistsException("A division with a child can't be deleted. First, delete the child divisions.");
        }else {
            throw new NoSuchUserExistsException("A division with an employee can't be deleted. First-delete employees belong to this division.");
        }

    }
}

}
