package com.cbo.core.service.impl;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.SubProcess;
import com.cbo.core.persistence.repository.SubProcessRepository;
import com.cbo.core.service.SubProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("subProcessService")
@Transactional
public class SubProcessServiceImpl implements SubProcessService {

    @Autowired
    private SubProcessRepository subProcessRepository;

    @Override
    public ResultWrapper<List<SubProcess>> findAllSubProcesses() {

        ResultWrapper<List<SubProcess>> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(subProcessRepository.findAll());
        return resultWrapper;
    }

//    @Override
//    public ResultWrapper<List<SubProcess>> findSubProcessByProcessId(Long processId) {
//        ResultWrapper<List<SubProcess>> resultWrapper = new ResultWrapper<>();
//        resultWrapper.setResult(subProcessRepository.findByProcessId(processId));
//        return resultWrapper;
//    }

    @Override
    public ResultWrapper<SubProcess> findSubProcessById(Long subProcessId) {
        ResultWrapper<SubProcess> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(subProcessRepository.findById(subProcessId).orElse(null));
        return resultWrapper;
    }

    @Override
    public SubProcess findSubProcess(Long id) {
        return subProcessRepository.findById(id).orElse(null);
    }
}
