package com.cbo.core.service;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.Process;

import java.util.List;

public interface ProcessService {


    ResultWrapper<List<Process>> findAllProcesses();

    ResultWrapper<Process> findProcessById(Long processId);

    Process findProcess(Long id);

}
