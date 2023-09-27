package com.cbo.core.controller;

import com.cbo.core.constants.URIS;
import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.Process;
import com.cbo.core.service.ProcessService;
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
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @GetMapping(value = URIS.PROCESS_LIST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_VIEW')")
    public ResponseEntity<ResultWrapper<List<Process>>> getProcesses() {
        ResultWrapper<List<Process>> resultWrapper = processService.findAllProcesses();

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @GetMapping(value = URIS.PROCESS_BY_ID, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_VIEW')")
    public ResponseEntity<ResultWrapper<Process>> getProcessById(@PathVariable("processId") Long processId) {
        ResultWrapper<Process> resultWrapper = processService.findProcessById(processId);

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

}
