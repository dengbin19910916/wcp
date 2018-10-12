package com.midea.wcp.site.data;

import com.midea.wcp.site.model.Account;
import com.midea.wcp.site.model.projection.InlineUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = InlineUsers.class)
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
