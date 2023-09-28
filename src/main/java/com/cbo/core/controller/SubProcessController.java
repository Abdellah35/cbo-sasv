package com.cbo.core.controller;

import com.cbo.core.constants.URIS;
import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.SubProcess;
import com.cbo.core.service.SubProcessService;
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
public class SubProcessController {

    @Autowired
    private SubProcessService subProcessService;

    @GetMapping(value = URIS.Sub_PROCESS_LIST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_VIEW')")
    public ResponseEntity<ResultWrapper<List<SubProcess>>> getSubProcesses() {
        ResultWrapper<List<SubProcess>> resultWrapper = subProcessService.findAllSubProcesses();

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @GetMapping(value = URIS.Sub_PROCESS_BY_ID, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_VIEW')")
    public ResponseEntity<ResultWrapper<SubProcess>> getSubProcessById(@PathVariable("subProcessId") Long subProcessId) {
        ResultWrapper<SubProcess> resultWrapper = subProcessService.findSubProcessById(subProcessId);

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }
}
