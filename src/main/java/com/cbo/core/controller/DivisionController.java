package com.cbo.core.controller;

import com.cbo.core.model.Division;
import com.cbo.core.model.User;
import com.cbo.core.service.DivisionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/division")
public class DivisionController {

    private final DivisionService divisionService;

    @Autowired
    public DivisionController(DivisionService divisionService) {
        this.divisionService = divisionService;
    }

    //This method returns all the divisions saved on database
    @GetMapping("/all")
    @ApiOperation(value = "List all Divisions",
            notes = "List of all available Divisions",
            response = Division.class)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Division>> getAllDivision(){
        List<Division> divisions = divisionService.findAllDivision();
        return new ResponseEntity<>(divisions, HttpStatus.OK);
    }

    //This will return a specific division with the given ID
    @GetMapping("/find/{id}")
    @ApiOperation(value = "Finds Division by id",
            notes = "Provide an id to look up specific division from the Division table",
            response = Division.class)
    @PreAuthorize("hasRole('ADMIN')")
    public Division getDivisionById(@PathVariable("id") Long id){
        return divisionService.findDivisionById(id);
    }

    //This will return all child division(s) of the division with the given id
    @GetMapping("/{id}/children")
    @ApiOperation(value = "Division Child",
            notes = "This will return all child division(s) of the division with the given id ",
            response = Division.class)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<Division>> getDivisionChildrenById(@PathVariable("id") Long id){
        Division division = divisionService.findDivisionById(id);
        Set<Division> childs = division.getChildren();
        return new ResponseEntity<>(childs, HttpStatus.OK);
    }

    //By consuming all the required parameters this function will create a new division entity
    @PostMapping("/add")
    @ApiOperation(value = "Add new Division",
            notes = "By consuming all the required parameters this function will create a new division entity",
            response = Division.class)
    @PreAuthorize("hasRole('ADMIN')")
    public Division addDivision(@RequestBody @Valid
                                    @RequestParam("stampImage") MultipartFile stampImage ,
                                @RequestParam("name") String name,
                                @RequestParam("parent") String parentId) throws IOException {

        String stampName = StringUtils.cleanPath(stampImage.getOriginalFilename());
        Division division = new Division();
        division.setName(name);
        division.setStampImage(stampName);

        long l = 0L;
        if(!parentId.equals("")){
            l=Long.parseLong(parentId);
        }

        return  divisionService.addDivision(division, stampImage, l);
    }

    //This method used to update existing division
    @PutMapping("/update")
    @ApiOperation(value = "Update Existing Division",
            notes = "This method used to update existing division",
            response = User.class)
    @PreAuthorize("hasRole('ADMIN')")
    public Division updateDivision(@RequestBody @Valid
                                       @RequestParam("stampImage") MultipartFile stampImage ,
                                   @RequestParam("name") String name,
                                   @RequestParam("parent") String parentId,
                                   @RequestParam("id") String id){


        Division division = new Division();
        division.setName(name);

        if(!stampImage.isEmpty()){
            String stampName = StringUtils.cleanPath(stampImage.getOriginalFilename());
            division.setStampImage(stampName);
        }
        division.setId(Long.parseLong(id));

        long l = 0L;
        if(!parentId.equals("")){
            l=Long.parseLong(parentId);
        }
        return divisionService.updateDivision(division, stampImage,l);
    }

    //Delete a division with specific Id, this division must not parent of another division
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete Existing User",
            notes = "Delete a division with specific Id, this division must not parent of another division.",
            response = User.class)
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteDivision(@PathVariable("id") Long id){

        return divisionService.deleteDivision(id);
    }
}
