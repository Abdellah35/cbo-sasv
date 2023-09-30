package com.cbo.core.service.impl;

import com.cbo.core.dto.AuthorityDTO;
import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.enums.AuthorityStatus;
import com.cbo.core.mappers.AuthorityMapper;
import com.cbo.core.persistence.model.*;
import com.cbo.core.persistence.repository.*;
import com.cbo.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("authorityService")
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepo;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SignatureRepository signatureRepository;
    @Autowired
    private StampRepository stampRepository;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private SubProcessService subProcessService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private OrganizationalUnitRepository organizationalUnitRepository;


    @Override
    public ResultWrapper<AuthorityDTO> registerAuthority(AuthorityDTO authorityDTO) {

        ResultWrapper<AuthorityDTO> resultWrapper = new ResultWrapper<>();
        LocalDateTime now = LocalDateTime.now();
        //check employee
        Long employeeId = authorityDTO.getEmployee().getId();
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        Authority authority = new Authority();
        if (employee == null) {
            resultWrapper.setStatus(false);
            resultWrapper.setMessage("No such employee exists!");
            return resultWrapper;
        } else if (authorityDTO.getOrganizationalUnit() != null) {
            long orgId = authorityDTO.getOrganizationalUnit().getId();
            if (isOrgUnitFree(orgId) && doesOrgUNitHaveStamp(orgId)) {
                authority.setOrganizationalUnit(organizationService.findOrganizationUnitById(orgId));
            } else {
                resultWrapper.setStatus(false);
                resultWrapper.setMessage("Selected OrgUnit doesn't have stamp image or have active authority pair");
                return resultWrapper;
            }
        } else if (authorityDTO.getSubProcess() != null) {
            long subProcId = authorityDTO.getSubProcess().getId();
            if (isSubProcessFree(subProcId) && doesSubProcessHaveStamp(subProcId)) {
                authority.setSubProcess(subProcessService.findSubProcess(subProcId));
            } else {
                resultWrapper.setStatus(false);
                resultWrapper.setMessage("Selected SubProcess doesn't have stamp image or have active authority pair");
                return resultWrapper;
            }
        } else if (authorityDTO.getProcess() != null) {
            long procId = authorityDTO.getProcess().getId();
            if (isProcessFree(procId) && doesProcessHaveStamp(procId)) {
                authority.setProcess(processService.findProcess(procId));
            } else {
                resultWrapper.setStatus(false);
                resultWrapper.setMessage("Selected Process doesn't have stamp image or have active authority pair");
                return resultWrapper;
            }
        }else if (authorityDTO.getBranch() != null) {
            long branchId = authorityDTO.getBranch().getId();
            if (isBranchFree(branchId) && doesBranchHaveStamp(branchId)) {
                authority.setBranch(branchService.findBranch(branchId));
            } else {
                resultWrapper.setStatus(false);
                resultWrapper.setMessage("Selected Branch doesn't have stamp image or have active authority pair");
                return resultWrapper;
            }
        }else if (authorityDTO.getDistrict() != null) {
            long districtId = authorityDTO.getDistrict().getId();
            if (isDistrictFree(districtId) && doesDistrictHaveStamp(districtId)) {
                authority.setDistrict(districtService.findDistrict(districtId));
            } else {
                resultWrapper.setStatus(false);
                resultWrapper.setMessage("Selected District doesn't have stamp image or have active authority pair");
                return resultWrapper;
            }
        } else {
            resultWrapper.setStatus(false);
            resultWrapper.setMessage("You have to provide one of stamp source processes info");
            return resultWrapper;
        }

        authority.setCreatedTimestamp(now);
        authority.setEmployee(employee);
        authority.setStatus(AuthorityStatus.Active.name());
        Authority newauthority = authorityRepo.save(authority);
        AuthorityDTO authorityDTORes = AuthorityMapper.INSTANCE.toDTO(newauthority);

        resultWrapper.setMessage("Authority successfully registered.");
        resultWrapper.setStatus(true);
        resultWrapper.setResult(authorityDTORes);
        return resultWrapper;
    }


    @Override
    public ResultWrapper<List<AuthorityDTO>> findAllAuthorities() {

        ResultWrapper<List<AuthorityDTO>> resultWrapper = new ResultWrapper<>();
        List<Authority> authorities = authorityRepo.findAll();
        List<AuthorityDTO> authorityDTOS = AuthorityMapper.INSTANCE.authoritiesToAuthorityDTOs(authorities);

        resultWrapper.setResult(authorityDTOS);
        return resultWrapper;
    }

    @Override
    public ResultWrapper<AuthorityDTO> updateAuthority(AuthorityDTO authorityDTO) {
        ResultWrapper<AuthorityDTO> resultWrapper = new ResultWrapper<>();

        Authority oldAuthority = authorityRepo.findById(authorityDTO.getId()).orElse(null);
        if (oldAuthority == null) {
            resultWrapper.setStatus(false);
            resultWrapper.setMessage("Provided authority doesn't exist");
        } else {
            LocalDateTime now = LocalDateTime.now();
            oldAuthority.setModifiedTimestamp(now);
            oldAuthority.setStatus(authorityDTO.getStatus());
            Authority authority = authorityRepo.save(oldAuthority);

            AuthorityDTO authorityDTORes = AuthorityMapper.INSTANCE.toDTO(authority);
            resultWrapper.setResult(authorityDTORes);
            resultWrapper.setStatus(true);
        }
        return resultWrapper;
    }


    @Override
    public ResultWrapper<AuthorityDTO> findAuthorityById(AuthorityDTO authorityDTO) {
        ResultWrapper<AuthorityDTO> resultWrapper = new ResultWrapper<>();

        Optional<Authority> authority = authorityRepo.findById(authorityDTO.getId());
        if (authority.isPresent()) {

            AuthorityDTO authorityDTORes = AuthorityMapper.INSTANCE.toDTO(authority.get());
            resultWrapper.setResult(authorityDTORes);
            resultWrapper.setStatus(true);
        } else {
            resultWrapper.setStatus(false);
            resultWrapper.setMessage("Authority not found.");
        }
        return resultWrapper;
    }

    @Override
    public Authority findAuthority(Long id) {
        Optional<Authority> authority = authorityRepo.findById(id);

        return authority.get();
    }

    @Override
    public ResultWrapper<List<AuthorityDTO>> findAllActiveAuthorities() {
        ResultWrapper<List<AuthorityDTO>> resultWrapper = new ResultWrapper<>();
        List<Authority> authorities = authorityRepo.findAllActiveAuthorities(AuthorityStatus.Active.getType());
        List<AuthorityDTO> authorityDTOS = AuthorityMapper.INSTANCE.authoritiesToAuthorityDTOs(authorities);

        resultWrapper.setResult(authorityDTOS);
        return resultWrapper;
    }

    @Override
    public ResultWrapper<AuthorityDTO> deleteAuthority(AuthorityDTO authorityDTO) {
        return null;
    }

    private boolean isEmployeeFree(Long employeeId) {
        Authority authority = authorityRepo.findAuthorityByEmployeeAndState(employeeId, AuthorityStatus.Active.name());
        return authority == null;
    }

    private boolean doesEmployeeHaveSignature(Long employeeId) {
        Signature signature = signatureRepository.findByEmployeeAndIsActive(employeeId, true);
        return signature != null;
    }

    private boolean isProcessFree(Long employeeId) {
        Authority authority = authorityRepo.findAuthorityByProcessAndState(employeeId, AuthorityStatus.Active.name());
        return authority == null;
    }

    private boolean doesProcessHaveStamp(Long employeeId) {
        Stamp stamp = stampRepository.findByProcessIdAndIsActive(employeeId, true);
        return stamp != null;
    }

    private boolean isBranchFree(Long branchId) {
        Authority authority = authorityRepo.findAuthorityByBranchAndState(branchId, AuthorityStatus.Active.name());
        return authority == null;
    }

    private boolean doesBranchHaveStamp(Long branchId) {
        Stamp stamp = stampRepository.findByBranchIdAndIsActive(branchId, true);
        return stamp != null;
    }

    private boolean isDistrictFree(Long districtId) {
        Authority authority = authorityRepo.findAuthorityByDistrictAndState(districtId, AuthorityStatus.Active.name());
        return authority == null;
    }

    private boolean doesDistrictHaveStamp(Long districtId) {
        Stamp stamp = stampRepository.findByDistrictIdAndIsActive(districtId, true);
        return stamp != null;
    }

    private boolean isSubProcessFree(Long employeeId) {
        Authority authority = authorityRepo.findAuthorityBySubProcessAndState(employeeId, AuthorityStatus.Active.name());
        return authority == null;
    }

    private boolean doesSubProcessHaveStamp(Long employeeId) {
        Stamp stamp = stampRepository.findBySubProcessIdAndIsActive(employeeId, true);
        return stamp != null;
    }

    private boolean isOrgUnitFree(Long employeeId) {
        Authority authority = authorityRepo.findAuthorityByOrganizationalUnitAndState(employeeId, AuthorityStatus.Active.name());
        return authority == null;
    }

    private boolean doesOrgUNitHaveStamp(Long employeeId) {
        Stamp stamp = stampRepository.findByOrganizationUnitIdIdAndIsActive(employeeId, true);
        return stamp != null;
    }
}
