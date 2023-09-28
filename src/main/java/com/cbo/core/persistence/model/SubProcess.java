package com.cbo.core.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sub_processes")
public class SubProcess {
    @Id
    private Long id;
    private String code;
    private String name;
    @ManyToOne
    private Process process;
    @ManyToOne
    private WorkCenter workCenter;
}