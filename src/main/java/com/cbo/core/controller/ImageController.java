package com.cbo.core.controller;

import com.cbo.core.constants.URIS;
import com.cbo.core.dto.AuthorityDTO;
import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.dto.SignatureDTO;
import com.cbo.core.dto.StampDTO;
import com.cbo.core.response.ImageRes;
import com.cbo.core.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;


    @GetMapping(value = URIS.AUTHORITY_IMAGES_BY_ID)
    //@PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_VIEW')")
    public ImageRes getImage(@PathVariable("id") Long id) throws IOException {

        return imageService.getImages(id);
    }


    @PostMapping(value = URIS.UPLOAD_SIGNATURE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('SASV_ADMIN')")
    public ResponseEntity<ResultWrapper<SignatureDTO>> addEmployeeSignature(@RequestParam("signature") MultipartFile signature,
                                                                            @RequestParam("employeeId") Long employeeId) throws IOException {
        SignatureDTO signatureDTO = new SignatureDTO();
        signatureDTO.setSignature(signature);
        signatureDTO.setEmployeeId(employeeId);
        ResultWrapper<SignatureDTO> resultWrapper= imageService.addEmployeeSignature(signatureDTO);
        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @PostMapping(value = URIS.UPLOAD_STAMP,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('SASV_ADMIN')")
    public ResponseEntity<ResultWrapper<StampDTO>> addStamp(@RequestParam(name="stamp") MultipartFile stamp,
                                                            @RequestParam(name="organizationUnitId", required = false) Long organizationUnitId,
                                                            @RequestParam(name="subProcessId", required = false) Long subProcessId,
                                                            @RequestParam(name="processId", required = false) Long processId) throws IOException {
        StampDTO stampDTO = new StampDTO();
        stampDTO.setStamp(stamp);
        stampDTO.setProcessId(processId);
        stampDTO.setOrganizationUnitId(organizationUnitId);
        stampDTO.setSubProcessId(subProcessId);
        ResultWrapper<StampDTO> resultWrapper= imageService.addStamp(stampDTO);
        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @GetMapping(value = URIS.RETRIEVE_SIGNATURE_LIST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('SASV_ADMIN')")
    public ResponseEntity<ResultWrapper<List<SignatureDTO>>> getAllSignatures(){

        ResultWrapper<List<SignatureDTO>> resultWrapper= imageService.getAllSignatures();
        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @GetMapping(value = URIS.RETRIEVE_STAMP_LIST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('SASV_ADMIN')")
    public ResponseEntity<ResultWrapper<List<StampDTO>>> getAllStamps(){

        ResultWrapper<List<StampDTO>> resultWrapper= imageService.getAllStamps();
        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }



}
