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

    @Enumerated
    private GenderType gender;

    @ManyToOne
    private Role role;

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}
