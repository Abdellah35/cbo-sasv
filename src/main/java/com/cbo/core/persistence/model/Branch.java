package com.cbo.core.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "branches")
public class Branch implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false, length = 64)
    private String code;
    @Column(nullable = false, length = 45)
    private String name;
    @Column(nullable = false, length = 45)
    private String mnemonic;
    @Column(nullable = false, length = 256)
    private String zone;
    @Column(nullable = false, length = 256)
    private String town;
    @Column(nullable = false, length = 256)
    private String telephone;
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    public Branch(String code, String name, String mnemonic, String zone, String town, String telephone, District district) {
        this.code = code;
        this.name = name;
        this.mnemonic = mnemonic;
        this.zone = zone;
        this.town = town;
        this.telephone = telephone;
        this.district = district;
    }
}
