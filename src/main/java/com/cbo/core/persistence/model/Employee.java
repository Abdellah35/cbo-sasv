package com.cbo.core.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long employeeId;
    private String employeeSapUserName;
    private String employeeFullName;
    private Long supervisorId;
    private String supervisorFullName;
    private Long hrManagerId;
    private String hrManagerFullName;
    private String companyEntryDate;
    private String latestPositionEntryDate;
    private String gender;
    private String salutation;
    @ManyToOne
    @JoinColumn(name="job_id")
    private Job job;
    @ManyToOne
    @JoinColumn(name="branch_id")
    private Branch branch;
    @ManyToOne
    @JoinColumn(name="position_id")
    private Position position;
    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;
    @ManyToOne
    @JoinColumn(name="sub_process_id")
    private SubProcess subProcess;
    @ManyToOne
    @JoinColumn(name="process_id")
    private Process process;
}

