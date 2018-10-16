package com.midea.wcp.site.model.projection;

import com.midea.wcp.site.model.Account;
import com.midea.wcp.site.model.Assistant;
import com.midea.wcp.site.model.Role;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@SuppressWarnings("unused")
@Projection(types = Role.class)
public interface InlineAccounts {

    Integer getId();
    String getName();
    Role.Type getType();
    List<Assistant> getManagers();
    List<Account> getAccounts();
}
