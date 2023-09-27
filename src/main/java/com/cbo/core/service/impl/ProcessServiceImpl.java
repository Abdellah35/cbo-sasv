package com.cbo.core.service.impl;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.Process;
import com.cbo.core.persistence.repository.ProcessRepository;
import com.cbo.core.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("processService")
@Transactional
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    @Override
    public ResultWrapper<List<Process>> findAllProcesses() {
        ResultWrapper<List<Process>> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(processRepository.findAll());
        return resultWrapper;
    }

    @Override
    public ResultWrapper<Process> findProcessById(Long processId) {
        ResultWrapper<Process> resultWrapper = new ResultWrapper<>();
        Process process = processRepository.findById(processId).orElse(null);
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
    public Process findProcess(Long id) {
        return processRepository.findById(id).orElse(null);
    }
}
