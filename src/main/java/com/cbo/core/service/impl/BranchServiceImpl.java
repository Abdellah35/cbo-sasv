package com.cbo.core.service.impl;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.Branch;
import com.cbo.core.persistence.repository.BranchRepository;
import com.cbo.core.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("branchService")
@Transactional
public class BranchServiceImpl implements BranchService {
    @Autowired
    private BranchRepository processRepository;

    @Override
    public ResultWrapper<List<Branch>> findAllBranches() {
        ResultWrapper<List<Branch>> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(processRepository.findAll());
        return resultWrapper;
    }

    @Override
    public ResultWrapper<Branch> findBranchById(Long processId) {
        ResultWrapper<Branch> resultWrapper = new ResultWrapper<>();
        Branch process = processRepository.findById(processId).orElse(null);
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
    public Branch findBranch(Long id) {
        return processRepository.findById(id).orElse(null);
    }
}
