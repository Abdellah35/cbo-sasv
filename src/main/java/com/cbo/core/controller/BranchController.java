package com.cbo.core.controller;

import com.cbo.core.constants.URIS;
import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.Branch;
import com.cbo.core.service.BranchService;
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
public class BranchController {


    @Autowired
    private BranchService branchService;

    @GetMapping(value = URIS.BRANCH_LIST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_USER')")
    public ResponseEntity<ResultWrapper<List<Branch>>> getBranches() {
        ResultWrapper<List<Branch>> resultWrapper = branchService.findAllBranches();

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }
    @GetMapping(value = URIS.BRANCH_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_USER')")
    public ResponseEntity<ResultWrapper<Branch>> getBranchById(@PathVariable("branchId") Long branchId) {
        ResultWrapper<Branch> resultWrapper = branchService.findBranchById(branchId);

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }
}
