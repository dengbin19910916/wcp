package com.midea.wcp.site.model.projection;

import com.midea.wcp.site.model.User;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@SuppressWarnings("unused")
@Projection(types = User.Tag.class)
public interface InlineUsersCount {

    Integer getId();
    String getName();
    List<User> getUsers();
    default int getUsersCount() {
        return getUsers().size();
    }
}
