package com.midea.wcp.model;

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

    private String name;

    @ManyToOne
    private Role role;
}
