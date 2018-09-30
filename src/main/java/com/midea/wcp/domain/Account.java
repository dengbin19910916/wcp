package com.midea.wcp.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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

    @ManyToMany
    private List<User> users;

    @ManyToMany(mappedBy = "accounts")
    private List<Role> roles;
}
