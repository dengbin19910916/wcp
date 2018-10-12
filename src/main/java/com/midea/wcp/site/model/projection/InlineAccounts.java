package com.midea.wcp.site.model.projection;

import com.midea.wcp.site.model.Account;
import com.midea.wcp.site.model.Manager;
import com.midea.wcp.site.model.Role;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@SuppressWarnings("unused")
@Projection(types = Role.class)
public interface InlineAccounts {

    Integer getId();
    String getName();
    Role.Type getType();
    List<Manager> getManagers();
    List<Account> getAccounts();
}
