package com.cbo.core.service.impl;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.District;
import com.cbo.core.persistence.model.District;
import com.cbo.core.persistence.repository.DistrictRepository;
import com.cbo.core.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("districtService")
@Transactional
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictRepository processRepository;

    @Override
    public ResultWrapper<List<District>> findAllDistricts() {
        ResultWrapper<List<District>> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(processRepository.findAll());
        return resultWrapper;
    }

    @Override
    public ResultWrapper<District> findDistrictById(Long processId) {
        ResultWrapper<District> resultWrapper = new ResultWrapper<>();
        District process = processRepository.findById(processId).orElse(null);
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
    public District findDistrict(Long id) {
        return processRepository.findById(id).orElse(null);
    }
}
