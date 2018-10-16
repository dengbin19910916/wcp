package com.midea.wcp.site.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 用户（粉丝）。
 */
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String openid;

    private String name;

    @Enumerated
    private GenderType gender;

    @ManyToMany
    private List<Tag> tags;

    @ManyToMany(mappedBy = "users")
    private List<Account> accounts;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tags=" + tags +
                '}';
    }

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

        @Override
        public String toString() {
            return "Tag{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
