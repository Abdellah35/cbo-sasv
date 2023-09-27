package com.cbo.core.dto;

import com.cbo.core.persistence.model.Employee;
import com.cbo.core.persistence.model.OrganizationalUnit;
import com.cbo.core.persistence.model.Process;
import com.cbo.core.persistence.model.SubProcess;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorityDTO extends BaseDTO {

    private Employee employee;

    private OrganizationalUnit organizationalUnit;

    private SubProcess subProcess;

    private Process process;

    private String createdAt;

    private String updatedAt;

    private String status;
}
