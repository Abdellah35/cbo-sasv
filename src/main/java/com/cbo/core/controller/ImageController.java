package com.cbo.core.controller;

import com.cbo.core.constants.URIS;
import com.cbo.core.dto.AuthorityDTO;
import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.dto.SignatureDTO;
import com.cbo.core.dto.StampDTO;
import com.cbo.core.response.ImageRes;
import com.cbo.core.service.ImageService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ImageController {

    private ImageService imageService;


    @GetMapping(value = URIS.AUTHORITY_IMAGES_BY_ID)
    //@PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_VIEW')")
    public ImageRes getImage(@PathVariable("id") Long id) throws IOException {

        return imageService.getImages(id);

    }


    @PostMapping(value = URIS.UPLOAD_SIGNATURE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('SASV_ADMIN')")
    public ResponseEntity<ResultWrapper<SignatureDTO>> addEmployeeSignature(@RequestAttribute  SignatureDTO signatureDTO) throws IOException {


        ResultWrapper<SignatureDTO> resultWrapper= imageService.addEmployeeSignature(signatureDTO);
        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @PostMapping(value = URIS.UPLOAD_STAMP,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('SASV_ADMIN')")
    public ResponseEntity<ResultWrapper<StampDTO>> addStamp(@RequestAttribute  StampDTO stampDTO) throws IOException {

        ResultWrapper<StampDTO> resultWrapper= imageService.addStamp(stampDTOg);
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
