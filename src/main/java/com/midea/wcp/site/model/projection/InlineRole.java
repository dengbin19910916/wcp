package com.midea.wcp.site.model.projection;

import com.midea.wcp.site.model.Account;
import com.midea.wcp.site.model.GenderType;
import com.midea.wcp.site.model.Manager;
import com.midea.wcp.site.model.Role;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@SuppressWarnings("unused")
@Projection(types = Manager.class)
public interface InlineRole {

    Integer getId();
    String getUid();
    String getName();
    GenderType getGender();
    Role getRole();
    default List<Account> getAccounts() {
        return getRole().getAccounts();
    }
}