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

    @Enumerated
    private Type type;

    @ManyToMany
    private List<Account> accounts;

    @OneToMany(mappedBy = "role")
    private List<Manager> managers;

    public enum Type {
        SUPER,
        COMMON
    }
}
