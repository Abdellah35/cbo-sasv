package com.cbo.core.service;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.District;

import java.util.List;

public interface DistrictService {

    ResultWrapper<List<District>> findAllDistricts();

    ResultWrapper<District> findDistrictById(Long processId);

    District findDistrict(Long id);
}
