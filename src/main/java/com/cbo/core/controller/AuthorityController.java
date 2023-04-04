package com.cbo.core.controller;

import com.cbo.core.exception.ImageNotFoundException;
import com.cbo.core.model.*;
import com.cbo.core.response.ImageRes;
import com.cbo.core.service.AuthorityDbService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
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
    @PreAuthorize("hasAnyRole('ADMIN','USER','DIRECTOR')")
    public List<AuthorityDB> getAllAuthority(){
        return authorityDbService.findAllAuthority();
    }

    @GetMapping(path = "/all/{isActive}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','DIRECTOR')")
    public ResponseEntity<List<AuthorityDB>> getAllAuthorityByState(@PathVariable(required = false) boolean isActive){

        List<AuthorityDB> allAuth = authorityDbService.findAllAuthorityByState(isActive);

        return ResponseEntity.ok(allAuth);
    }

    @GetMapping(path = "/find/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds Authority by id",
            notes = "Provide an id to look up specific Authority from the Authority table",
            response = AuthorityDB.class)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<AuthorityDB> getAuthorityById(@PathVariable("id") Long id){

        AuthorityDB getAuth = authorityDbService.findAuthorityById(id);
        return ResponseEntity.ok(getAuth);
    }


    @PostMapping(path = "/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ApiOperation(value = "Add new Authority",
        notes = "The new Added Authority must be exist in Employee Table",
        response = AuthorityDB.class)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorityDB> addAuthority(@RequestParam("divisionId") Long divisionId,
                                    @RequestParam("employeeId") Long employeeId) throws IOException {

        AuthorityDB newAuth =authorityDbService.addAuthority(divisionId, employeeId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newAuth.getId()).toUri();

        return ResponseEntity.created(location).body(newAuth);
    }



    @PutMapping(path = "/update")
    @ApiOperation(value = "Update Existing Authority",
            notes = "All fields can be updated except User Id",
            response = AuthorityDB.class)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAuthority(@RequestParam("authId") Integer authId,
                                          @RequestParam("isActive") Boolean isActive
                                       ) throws IOException {
        System.out.println("isActive " + isActive);
        authorityDbService.updateAuthority( authId.longValue(), isActive);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping(path = "/delete/{id}",  produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Existing Authority",
            notes = "Delete Authority by Id",
            response = User.class)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAuthority(@PathVariable("id") Long id){

        authorityDbService.deleteAuthority(id);
        return ResponseEntity.noContent().build();
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
}
