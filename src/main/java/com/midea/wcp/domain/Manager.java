package com.midea.wcp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @ManyToMany
    private List<Role> roles;

    /**
     * 返回协作员的所有公众号。
     *
     * @return 公众号列表
     */
    @JsonIgnore
    public List<Account> getAccounts() {
        return roles.stream().flatMap(role -> role.getAccounts().stream()).collect(Collectors.toList());
    }

    /**
     * 向所有的公众号下的用户发送消息。
     *
     * @param message 消息
     */
    public void sendMessage(String message) {
        getAccounts().forEach(account -> System.out.println("向" + account.getName() + "全体发送消息 [" + message + "]"));
    }
}
