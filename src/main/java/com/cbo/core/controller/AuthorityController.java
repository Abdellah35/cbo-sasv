package com.cbo.core.controller;

import com.cbo.core.exception.ImageNotFoundException;
import com.cbo.core.model.*;
import com.cbo.core.response.ImageRes;
import com.cbo.core.service.AuthorityDbService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/authority")
public class AuthorityController {


    private final AuthorityDbService authorityDbService;
    @Autowired
    public AuthorityController( AuthorityDbService authorityDbService) {
        this.authorityDbService = authorityDbService;

    }
    @ApiOperation(value = "List all Authorities",
            notes = "List of all Authorities",
            response = AuthorityDB.class)

    @GetMapping(path = "/all", produces=MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<AuthorityDB> getAllAuthority(){
        return authorityDbService.findAllAuthority();
    }

    @GetMapping(path = "/all/{isActive}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<AuthorityDB> getAllAuthorityByState(@PathVariable(required = false) boolean isActive){
        return authorityDbService.findAllAuthorityByState(isActive);
    }
    @GetMapping(path = "/find/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds Authority by id",
            notes = "Provide an id to look up specific Authority from the Authority table",
            response = AuthorityDB.class)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public AuthorityDB getAuthorityById(@PathVariable("id") Long id){

        return authorityDbService.findAuthorityById(id);
    }

    @PostMapping(path = "/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ApiOperation(value = "Add new Authority",
        notes = "The new Added Authority must be exist in Employee Table",
        response = AuthorityDB.class)
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorityDB addAuthority(@RequestParam("divisionId") Long divisionId,
                                    @RequestParam("employeeId") Long employeeId) throws IOException {
        return  authorityDbService.addAuthority(divisionId, employeeId);
    }
    @GetMapping(value = "/sid/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ImageRes getImage(@PathVariable("id") Long id) throws IOException {
        AuthorityDB autho = authorityDbService.findAuthorityById(id);
        if(autho.getIsActive()){
            ImageRes imgRes = new ImageRes();
            BufferedImage stampImage = ImageIO.read(new File("user-photos/division/"+ autho.getDivision().getId() +"/"+ autho.getDivision().getStampImage()));
            ByteArrayOutputStream stbos = new ByteArrayOutputStream();
            ImageIO.write(stampImage, "png", stbos );
            byte [] stadata = stbos.toByteArray();

            imgRes.setStamp(stadata);

            BufferedImage sigImage = ImageIO.read(new File("user-photos/employee/"+ autho.getEmployee().getId() +"/"+ autho.getEmployee().getSignatureImage()));
            ByteArrayOutputStream sibos = new ByteArrayOutputStream();
            ImageIO.write(sigImage, "png", sibos );
            byte [] sidata = sibos.toByteArray();

            imgRes.setSignature(sidata);
            imgRes.setEmployee(autho.getEmployee());
            imgRes.setDivision(autho.getDivision());
            System.out.println(imgRes.getSignature());
            System.out.println(imgRes.getStamp());
            return imgRes;
        }
        else {
            throw new ImageNotFoundException("Authority with Id: "+id + " is not Active.");
        }
    }
    @PutMapping(path = "/update")
    @ApiOperation(value = "Update Existing Authority",
            notes = "All fields can be updated except User Id",
            response = AuthorityDB.class)
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorityDB updateAuthority(@RequestParam("authId") Integer authId,
                                       @RequestParam("isActive") Boolean isActive
                                       ) throws IOException {
        System.out.println("isActive " + isActive);
        return authorityDbService.updateAuthority( authId.longValue(), isActive);
    }
    @DeleteMapping(path = "/delete/{id}",  produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Existing Authority",
            notes = "Delete Authority by Id",
            response = User.class)
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAuthority(@PathVariable("id") Long id){

        return authorityDbService.deleteAuthority(id);
    }
}
