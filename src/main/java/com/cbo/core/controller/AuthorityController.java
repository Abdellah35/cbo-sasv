package com.cbo.core.controller;

import com.cbo.core.constants.URIS;
import com.cbo.core.dto.AuthorityDTO;
import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorityController extends BaseApplicationController {

    @Autowired
    private AuthorityService authorityService;

    @PostMapping(value = URIS.AUTHORITY_REGISTER, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SASV_ADMIN')")
    public ResponseEntity<ResultWrapper<AuthorityDTO>> registerAuthority(HttpEntity<AuthorityDTO> requestData) {

        validateRequest(requestData);

        ResultWrapper<AuthorityDTO> resultWrapper = new ResultWrapper<>();
        AuthorityDTO authorityDTO = requestData.getBody();
        if (authorityDTO != null) {
            resultWrapper = authorityService.registerAuthority(authorityDTO);
        }
        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @GetMapping(value = URIS.AUTHORITY_LIST_ALL,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SASV_ADMIN')")
    public ResponseEntity<ResultWrapper<List<AuthorityDTO>>> listAllAuthorities() {

        ResultWrapper<List<AuthorityDTO>> listAccounts = authorityService.findAllAuthorities();

        return new ResponseEntity<>(listAccounts, HttpStatus.OK);
    }

    @GetMapping(value = URIS.AUTHORITY_LIST_ACTIVE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_VIEW')")
    public ResponseEntity<ResultWrapper<List<AuthorityDTO>>> findAllActiveAuthorities() {

        ResultWrapper<List<AuthorityDTO>> listAccounts = authorityService.findAllActiveAuthorities();

        return new ResponseEntity<>(listAccounts, HttpStatus.OK);
    }

    @PostMapping(value = URIS.AUTHORITY_BY_ID, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_VIEW')")
    public ResponseEntity<ResultWrapper<AuthorityDTO>> findAuthorityById(HttpEntity<AuthorityDTO> requestData) {

        ResultWrapper<AuthorityDTO> accountInfoById = authorityService.findAuthorityById(requestData.getBody());

        return new ResponseEntity<>(accountInfoById, HttpStatus.OK);
    }


    @PostMapping(value = URIS.AUTHORITY_UPDATE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN')")
    public ResponseEntity<ResultWrapper<AuthorityDTO>> updateAuthority(HttpEntity<AuthorityDTO> requestData) {

        validateRequest(requestData);

        AuthorityDTO authorityDTO = requestData.getBody();
        ResultWrapper<AuthorityDTO> resultWrapper = authorityService.updateAuthority(authorityDTO);
        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @PostMapping(value = URIS.AUTHORITY_DELETE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN')")
    public ResponseEntity<ResultWrapper<AuthorityDTO>> deleteAuthority(HttpEntity<AuthorityDTO> requestData) {

        validateRequest(requestData);
        AuthorityDTO authorityDTO = requestData.getBody();
        ResultWrapper<AuthorityDTO> resultWrapper = authorityService.deleteAuthority(authorityDTO);

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }


//    @GetMapping(value = "/dashboard")
//    @PreAuthorize("hasRole('SASV_ADMIN')")
//    public ResponseEntity<dashboard> getDashData(){
//
//        dashboard nr = authorityDbService.getdashData();
//        return ResponseEntity.ok(nr);
//    }
}
