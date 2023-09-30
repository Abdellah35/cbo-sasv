package com.cbo.core.dto;

import com.cbo.core.persistence.model.*;
import com.cbo.core.persistence.model.Process;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorityDTO extends BaseDTO {

    private Employee employee;

    private OrganizationalUnit organizationalUnit;

    private SubProcess subProcess;

    private Process process;

    private Branch branch;

    private District district;

    private String createdAt;

    private String updatedAt;

    private String status;
}
