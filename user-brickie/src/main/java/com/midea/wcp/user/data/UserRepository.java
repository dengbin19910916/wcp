package com.midea.wcp.user.data;

import com.midea.wcp.commons.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
