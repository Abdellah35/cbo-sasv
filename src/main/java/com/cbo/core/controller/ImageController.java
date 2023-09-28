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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ImageController {

    private ImageService imageService;

    @GetMapping(value = URIS.AUTHORITY_IMAGES_BY_ID)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_VIEW')")
    public ImageRes getImage(@PathVariable("id") Long id) throws IOException {

        return imageService.getImages(id);

    }


    @PostMapping(value = URIS.UPLOAD_SIGNATURE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SASV_ADMIN')")
    public ResponseEntity<ResultWrapper<SignatureDTO>> addEmployeeSignature(HttpEntity<SignatureDTO> requestData) throws IOException {


        ResultWrapper<SignatureDTO> resultWrapper= imageService.addEmployeeSignature(requestData.getBody());
        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @PostMapping(value = URIS.UPLOAD_STAMP, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SASV_ADMIN')")
    public ResponseEntity<ResultWrapper<StampDTO>> addStamp(HttpEntity<StampDTO> requestData) throws IOException {

        ResultWrapper<StampDTO> resultWrapper= imageService.addStamp(requestData.getBody());
        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }



}
