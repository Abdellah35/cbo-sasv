package com.cbo.core.response;

import com.cbo.core.persistence.model.*;
import com.cbo.core.persistence.model.Process;
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
    private Branch branch;
    private District district;
    private boolean isActive;

}
