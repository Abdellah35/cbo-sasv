package com.cbo.core.dto;

import com.cbo.core.persistence.model.*;
import com.cbo.core.persistence.model.Process;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StampDTO extends BaseDTO{
    private Long organizationUnitId;
    private Long subProcessId;
    private Long processId;
    private Long branchId;
    private Long districtId;

    //
    private OrganizationalUnit organizationalUnit;
    private SubProcess subProcess;
    private Process process;
    private Branch branch;
    private District district;

    private MultipartFile stamp;
}
