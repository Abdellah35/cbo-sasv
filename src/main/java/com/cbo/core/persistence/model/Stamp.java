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

    private Long processId;

    private Long subProcessId;

    private Long organizationUnitId;

    private String stampLink;

    private boolean isActive;
}
