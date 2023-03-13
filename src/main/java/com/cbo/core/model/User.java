package com.cbo.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true , length = 45)
    private String email;
    @Column(nullable = false, length = 64)
    private String password;
    @Column(name = "isActive")
    private boolean active;
    @Column(name = "username",unique = true, nullable = false, length = 20)
    @ApiModelProperty(notes = "Unique userName for the user")
    private String username;
    @ManyToOne
    private Employee employee;

    @JsonInclude()
    @Transient
    private String role;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public boolean isActive() {
        return active;
    }
/*
    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true)
    private ERole role;
*/
    public User(String userName, String email, String password) {
        this.username = userName;
        this.password = password;
        this.email = email;
    }

    public User(Long id,String userName, String email, String password) {
        this.username = userName;
        this.password = password;
        this.email = email;
        this.id = id;
    }
}