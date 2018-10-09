package com.midea.wcp.model;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "managerDetails", types = Manager.class)
public interface ManagerDetails {

    Integer getId();
    String getName();
    GenderType getGender();
    Role getRole();
    default List<Account> getAccounts() {
        return getRole().getAccounts();
    }
}
