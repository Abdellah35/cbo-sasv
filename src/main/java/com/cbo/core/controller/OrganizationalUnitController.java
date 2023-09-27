package com.cbo.core.controller;

import com.cbo.core.constants.URIS;
import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.OrganizationalUnit;
import com.cbo.core.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrganizationalUnitController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping(value = URIS.ORG_UNIT_LIST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_VIEW')")
    public ResponseEntity<ResultWrapper<List<OrganizationalUnit>>> getOrganizationalUnits() {
        ResultWrapper<List<OrganizationalUnit>> resultWrapper = organizationService.getOrganizationUnits();

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @GetMapping(value = URIS.ORG_UNIT_BY_ID, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_VIEW')")
    public ResponseEntity<ResultWrapper<OrganizationalUnit>> getOrganizationalUnitById(@PathVariable("orgUnitId") Long orgUnitId) {
        ResultWrapper<OrganizationalUnit> resultWrapper = organizationService.getOrganizationUnitById(orgUnitId);

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }
}
