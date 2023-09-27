package com.cbo.core.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authorities")
public class Authority implements Serializable {
    @Id
    @SequenceGenerator(
            name = "authority_sequence",
            sequenceName = "authority_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "authority_sequence"
    )
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_UNIT_ID")
    private OrganizationalUnit organizationalUnit;

    @ManyToOne
    @JoinColumn(name = "SUB_PROCESS_ID")
    private SubProcess subProcess;

    @ManyToOne
    @JoinColumn(name = "PROCESS_ID")
    private Process process;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_TS")
    @CreationTimestamp
    private LocalDateTime createdTimestamp;

    @UpdateTimestamp
    @Column(name = "MODIFIED_TS")
    private LocalDateTime modifiedTimestamp;


    public Authority(OrganizationalUnit organizationalUnit, Employee employee, String status) {
        this.employee = employee;
        this.status = status;
        this.organizationalUnit = organizationalUnit;
    }

}
