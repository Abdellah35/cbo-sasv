package com.cbo.core.service.impl;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.Team;
import com.cbo.core.persistence.model.Team;
import com.cbo.core.persistence.repository.TeamRepository;
import com.cbo.core.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("teamService")
@Transactional
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository processRepository;

    @Override
    public ResultWrapper<List<Team>> findAllTeams() {
        ResultWrapper<List<Team>> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(processRepository.findAll());
        return resultWrapper;
    }

    @Override
    public ResultWrapper<Team> findTeamById(Long teamId) {
        ResultWrapper<Team> resultWrapper = new ResultWrapper<>();
        Team process = processRepository.findById(teamId).orElse(null);
        if (process == null) {
            resultWrapper.setStatus(false);
            resultWrapper.setMessage("not found");
        } else {
            resultWrapper.setResult(process);
            resultWrapper.setStatus(true);
        }

        return resultWrapper;
    }

    @Override
    public Team findTeam(Long id) {
        return processRepository.findById(id).orElse(null);
    }
}
