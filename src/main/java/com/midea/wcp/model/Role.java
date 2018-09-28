package com.midea.wcp.model;

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

    @ManyToOne
    private Manager manager;

    @OneToMany(mappedBy = "role")
    private List<Account> accounts;
}
