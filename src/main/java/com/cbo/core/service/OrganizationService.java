package com.cbo.core.service;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.persistence.model.OrganizationalUnit;

import java.util.List;

public interface OrganizationService {

    ResultWrapper<List<OrganizationalUnit>> getOrganizationUnits();

    ResultWrapper<List<OrganizationalUnit>> getOrganizationUnitsBySubProcessId(Long subProcessId);

    ResultWrapper<OrganizationalUnit> getOrganizationUnitById(Long organizationId);

    OrganizationalUnit findOrganizationUnitById(Long organizationId);
}
