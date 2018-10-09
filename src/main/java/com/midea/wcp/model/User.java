package com.midea.wcp.model;

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

    @ManyToMany
    private List<Tag> tags;

    @ManyToMany(mappedBy = "users")
    private List<Account> accounts;

    /**
     * 用户标签。
     */
    @Data
    @Entity(name = "tag")
    public static class Tag {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String name;

        @ManyToMany(mappedBy = "tags")
        private List<User> users;
    }
}
