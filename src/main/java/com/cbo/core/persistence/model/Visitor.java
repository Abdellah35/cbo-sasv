package com.cbo.core.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "visitors")
public class Visitor {
    @Id
    @SequenceGenerator(
            name = "visitor_sequence",
            sequenceName = "visitor_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "visitor_sequence")
    private Long id;

    private String Application;
    private String appUser;
    private String ip;
    private String method;
    private String url;
    private String page;
    private String queryString;
    private String refererPage;
    private String userAgent;
    private String loggedTimetime;
    private String loggedTime;
    private boolean uniqueVisit;
}
