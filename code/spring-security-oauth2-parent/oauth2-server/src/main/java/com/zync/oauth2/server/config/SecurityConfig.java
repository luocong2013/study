package com.zync.oauth2.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security 核心配置类
 *
 * @author luocong
 * @version v1.0
 * @date 2022/2/15 15:54
 */
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests.anyRequest().authenticated()
        ).formLogin();
        return http.build();
    }

    /**
     * 创建默认登录用户
     *
     * @return
     */
    @Bean
    public UserDetailsService users() {
        UserDetails userDetails = User.builder()
                .username("lalita")
                .password("123456")
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                .authorities("USER")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }

    /**
     * Web security customizer web security customizer.
     *
     * @return the web security customizer
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/actuator/health", "/h2-console/**");
    }

}
