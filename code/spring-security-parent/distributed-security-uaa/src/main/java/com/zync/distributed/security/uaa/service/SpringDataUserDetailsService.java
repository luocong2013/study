package com.zync.distributed.security.uaa.service;

import com.zync.distributed.security.uaa.dao.UserDAO;
import com.zync.distributed.security.uaa.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 自定义UserDetailsService  方式二
 * @date 2020/7/18 16:44
 */
@Service
public class SpringDataUserDetailsService implements UserDetailsService {

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
        String[] perarray = new String[permissions.size()];
        permissions.toArray(perarray);
        return User.withUsername(user.getUsername()).password(user.getPassword()).authorities(perarray).build();
    }


}
