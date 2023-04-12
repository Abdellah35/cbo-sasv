package com.cbo.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Employee {
    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "employee_sequence")
    private Long id;
    private String givenName;
    private String fatherName;
    private String grandFatherName;
    private String position;
    private String email;
    private String cboEmail;
    private String phoneNumber;
    private String signatureImage;

    private String gender;

    private String birthDate;
    @ManyToOne
    private Division division;

    @Transient
    public String getSignImagePath() {
        if (signatureImage == null || id == null) return null;
        return "/user-photos/employee/" + id + "/" + signatureImage;
    }

}
