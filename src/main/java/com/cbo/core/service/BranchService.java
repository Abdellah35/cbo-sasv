package com.cbo.core.service;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.Branch;

import java.util.List;

public interface BranchService {

    ResultWrapper<List<Branch>> findAllBranches();

    ResultWrapper<Branch> findBranchById(Long processId);

    Branch findBranch(Long id);
}
