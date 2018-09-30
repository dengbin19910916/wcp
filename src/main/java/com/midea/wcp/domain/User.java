package com.midea.wcp.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 用户。
 */
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "users")
    private List<Account> accounts;
}
