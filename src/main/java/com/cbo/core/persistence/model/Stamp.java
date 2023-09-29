package com.cbo.core.persistence.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sasv_stamp")
public class Stamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PROCESS_ID")
    private Process process;

    @ManyToOne
    @JoinColumn(name = "SUB_PROCESS_ID")
    private SubProcess subProcess;

    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_UNIT_ID")
    private OrganizationalUnit organizationalUnit;

    private String stampLink;

    private boolean isActive;
}
