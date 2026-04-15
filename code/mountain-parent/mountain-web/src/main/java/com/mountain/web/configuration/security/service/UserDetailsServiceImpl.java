package com.mountain.web.configuration.security.service;

import com.mountain.web.basic.pojo.po.Role;
import com.mountain.web.basic.pojo.po.User;
import com.mountain.web.basic.service.UserService;
import com.mountain.web.configuration.security.details.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Strings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义 UserDetailsService
 *
 * @author luocong
 * @version 1.0
 * @since 2026/2/24 16:26
 **/
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (Strings.CS.equals(username, "admin")) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("$2a$10$4LIDO0l20XhSnDvqHSAT0eOKvimziM.0H.WjgK.MPR6s68jhx78NW");
            user.setFullName("超级管理员");
            Role role = new Role();
            role.setRoleName("admin");
            role.setRoleCode("admin");
            return new SecurityUser(user, List.of(role));
        }
        return null;
    }

}
