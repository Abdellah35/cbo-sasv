package com.cbo.core.service.impl;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.OrganizationalUnit;
import com.cbo.core.persistence.repository.OrganizationalUnitRepository;
import com.cbo.core.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service("organizationService")
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationalUnitRepository organizationalUnitRepository;

    @Override
    public ResultWrapper<List<OrganizationalUnit>> getOrganizationUnits() {
        ResultWrapper<List<OrganizationalUnit>> resultWrapper = new ResultWrapper<>();
        List<OrganizationalUnit> organizationalUnits = organizationalUnitRepository.findAll();
        if (!organizationalUnits.isEmpty()) {
            resultWrapper.setStatus(true);
            resultWrapper.setResult(organizationalUnits);
        } else {
            resultWrapper.setStatus(false);
        }
        return resultWrapper;
    }

    @Override
    public ResultWrapper<List<OrganizationalUnit>> getOrganizationUnitsBySubProcessId(Long subProcessId) {
        ResultWrapper<List<OrganizationalUnit>> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(organizationalUnitRepository.findAllBySubProcessId(subProcessId));
        return resultWrapper;
    }

    @Override
    public ResultWrapper<OrganizationalUnit> getOrganizationUnitById(Long organizationId) {
        ResultWrapper<OrganizationalUnit> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(organizationalUnitRepository.findById(organizationId).orElse(null));
        return resultWrapper;
    }

    @Override
    public OrganizationalUnit findOrganizationUnitById(Long organizationId) {
        return organizationalUnitRepository.findById(organizationId).orElse(null);
    }
}
