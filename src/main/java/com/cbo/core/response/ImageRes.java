package com.cbo.core.response;

import com.cbo.core.persistence.model.Employee;
import com.cbo.core.persistence.model.OrganizationalUnit;
import com.cbo.core.persistence.model.Process;
import com.cbo.core.persistence.model.SubProcess;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ImageRes {
    private byte[] stamp;

    private byte[] signature;
    private int id;
    private Employee employee;
    private OrganizationalUnit organizationalUnit;
    private SubProcess subProcess;
    private Process process;
    private boolean isActive;

}
