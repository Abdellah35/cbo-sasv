package com.cbo.core.service;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.SubProcess;

import java.util.List;

public interface SubProcessService {
    ResultWrapper<List<SubProcess>> findAllSubProcesses();

    ResultWrapper<List<SubProcess>> findSubProcessByProcessId(Long processId);

    ResultWrapper<SubProcess> findSubProcessById(Long subProcessId);

    SubProcess findProcess(Long id);

}
