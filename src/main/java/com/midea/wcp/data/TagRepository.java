package com.midea.wcp.data;

import com.midea.wcp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<User.Tag, Integer> {
}
