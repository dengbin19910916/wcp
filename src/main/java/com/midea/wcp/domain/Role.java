package com.midea.wcp.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 角色。
 */
@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany
    private List<Account> accounts;
}
