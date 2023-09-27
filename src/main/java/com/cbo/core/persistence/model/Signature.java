package com.cbo.core.persistence.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sasv_signature")
public class Signature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private String signatureLink;

    private boolean isActive;
}
