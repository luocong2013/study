package com.zync.single.security.jdbc.singlesecurityjdbc.config;

import com.zync.single.security.jdbc.singlesecurityjdbc.web.mapper.UserDAO;
import com.zync.single.security.jdbc.singlesecurityjdbc.web.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Objects;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 用户
 * @date 2020/8/29 22:23
 */
//@Component
public class JdbcUserDetails implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userDAO.getUserByUsername(username);
        if (Objects.isNull(user)) {
            // 如果用户查不到，返回null，由provider来抛出异常
            return null;
        }
        List<String> permissions = userDAO.getPermissionsByUserId(user.getId());
        String[] perarray = permissions.toArray(new String[0]);
        return User.withUsername(user.getUsername()).password(user.getPassword()).authorities(perarray).build();
    }
}
