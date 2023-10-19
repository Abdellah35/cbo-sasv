package com.cbo.core.service;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.Team;

import java.util.List;

public interface TeamService {

    ResultWrapper<List<Team>> findAllTeams();

    ResultWrapper<Team> findTeamById(Long teamId);

    Team findTeam(Long id);
}
