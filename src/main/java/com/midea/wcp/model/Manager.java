package com.midea.wcp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "manager")
    private List<Role> roles;
}
