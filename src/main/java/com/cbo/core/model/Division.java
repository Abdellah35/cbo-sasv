package com.cbo.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Division {
    @Id
    @SequenceGenerator(
            name = "division_sequence",
            sequenceName = "division_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "division_sequence")
    private Long id;
    private String name;
    private String stampImage;
    @ManyToOne
    private Division parent;
    @Transient
    public String getStampImagePath() {
        if (stampImage == null || id == null) return null;
        return "/user-photos/division/" + id + "/" + stampImage;
    }
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parent")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Division> children = new HashSet<>();

}
