package com.zync.security.withjpa.dao;

import com.zync.security.withjpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author luocong
 */
public interface UserDao extends JpaRepository<User,Long> {
    User findUserByUsername(String username);
}
