package com.midea.wcp.site.model.projection;

import com.midea.wcp.site.model.Account;
import com.midea.wcp.site.model.GenderType;
import com.midea.wcp.site.model.User;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@SuppressWarnings("unused")
@Projection(types = User.class)
public interface InlineTags {

    Integer getId();
    String getName();
    GenderType getGender();
    List<Account> getAccounts();
    List<User.Tag> getTags();
}
