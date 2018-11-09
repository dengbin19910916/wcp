package com.midea.wcp.commons.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Account {

    private String appId;

    private String name;

    @OneToMany(mappedBy = "account")
    private List<Tag> tags;

    @ManyToMany
    private List<User> users;

    @Data
    public static class Tag {
        private Integer id;
        private String name;
        @ManyToOne
        private Account account;
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
