package com.midea.wcp.user;

import com.midea.wcp.commons.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, String> {
}
