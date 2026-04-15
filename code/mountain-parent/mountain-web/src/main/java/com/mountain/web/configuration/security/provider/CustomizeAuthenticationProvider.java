package com.mountain.web.configuration.security.provider;

import com.mountain.web.configuration.security.customize.CustomizeWebAuthenticationDetails;
import com.mountain.web.configuration.security.details.SecurityUser;
import com.mountain.web.configuration.security.details.UserLoginDTO;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 自定义AuthenticationProvider
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/15 15:20
 **/
@Slf4j
@Component
public class CustomizeAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
                "AbstractAuthenticationProvider Only UsernamePasswordAuthenticationToken is supported");
        CustomizeWebAuthenticationDetails details = (CustomizeWebAuthenticationDetails) authentication.getDetails();
        Assert.notNull(details, "登录参数获取失败");
        UserLoginDTO userLogin = details.getUserLogin();
        log.info("用户登录信息: {}", userLogin);
        SecurityUser user = (SecurityUser) userDetailsService.loadUserByUsername(userLogin.getUsername());
        if (user == null) {
            throw new BadCredentialsException("用户名或密码错误");
        }
        if (passwordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
            return UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());
        }
        throw new BadCredentialsException("用户名或密码错误");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
