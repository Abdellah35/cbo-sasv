package com.cbo.core.controller;

import com.cbo.core.constants.URIS;
import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.Team;
import com.cbo.core.service.TeamService;
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
public class TeamController {


    @Autowired
    private TeamService branchService;

    @GetMapping(value = URIS.TEAM_LIST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_USER')")
    public ResponseEntity<ResultWrapper<List<Team>>> getTeams() {
        ResultWrapper<List<Team>> resultWrapper = branchService.findAllTeams();

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }

    @GetMapping(value = URIS.TEAM_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SASV_ADMIN','SASV_USER')")
    public ResponseEntity<ResultWrapper<Team>> getTeamById(@PathVariable("branchId") Long branchId) {
        ResultWrapper<Team> resultWrapper = branchService.findTeamById(branchId);

        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }
}
