package com.midea.wcp.site.model;

import lombok.Data;

import javax.persistence.*;

/**
 * 协作员。
 */
@Data
@Entity
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uid;

    private String name;

    @Enumerated
    private GenderType gender;

    @ManyToOne
    private Role role;
}
