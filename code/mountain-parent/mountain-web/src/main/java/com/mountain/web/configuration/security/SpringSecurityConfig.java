package com.mountain.web.configuration.security;

import com.mountain.web.configuration.MountainProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SpringSecurity 核心配置类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/2/10 16:00
 **/
@RequiredArgsConstructor
@SpringBootConfiguration
public class SpringSecurityConfig {

    private final MountainProperties mountainProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/**").authorizeHttpRequests(authorize -> authorize
                .requestMatchers(mountainProperties.getAuth().permitUrls()).permitAll()
        );
        return http.build();
    }

}
