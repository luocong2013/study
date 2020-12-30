package com.zync.security.session2.dao;

import com.zync.security.session2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author luocong
 */
public interface UserDao extends JpaRepository<User,Long> {
    User findUserByUsername(String username);
}
