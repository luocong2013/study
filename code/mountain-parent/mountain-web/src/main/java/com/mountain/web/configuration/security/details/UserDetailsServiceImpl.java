package com.mountain.web.configuration.security.details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义 UserDetailsService
 *
 * @author luocong
 * @version 1.0
 * @since 2026/2/24 16:26
 **/
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}
