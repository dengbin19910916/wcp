package com.midea.wcp.site.model.projection;

import com.midea.wcp.site.model.Account;
import com.midea.wcp.site.model.Role;
import com.midea.wcp.site.model.User;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@SuppressWarnings("unused")
@Projection(types = Account.class)
public interface InlineUsers {

    Integer getId();
    String getName();
    Account.Type getType();
    List<Role> getRoles();
    List<User> getUsers();
}
