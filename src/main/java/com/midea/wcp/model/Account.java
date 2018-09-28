package com.midea.wcp.model;

import lombok.Data;

import javax.persistence.*;

/**
 * 公众号。
 */
@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    private Role role;
}
