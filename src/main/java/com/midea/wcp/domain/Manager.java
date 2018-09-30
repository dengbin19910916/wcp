package com.midea.wcp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    private Role role;

    /**
     * 返回当前协作员是否为管理员。
     *
     * @return true - 是，false - 不是
     */
    public boolean isAdmin() {
        return role.getType() != null && role.getType() == Role.Type.SUPER;
    }

    /**
     * 返回协作员的所有公众号。
     *
     * @return 公众号列表
     */
    @JsonIgnore
    public List<Account> getAccounts() {
        return role.getAccounts();
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
