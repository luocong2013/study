package com.mountain.web.configuration.security;

import com.mountain.web.configuration.MountainProperties;
import com.mountain.web.configuration.security.authentication.CustomizeAuthenticationManager;
import com.mountain.web.configuration.security.authorization.RbacAuthorizationManager;
import com.mountain.web.configuration.security.configurers.UsernamePasswordLoginConfigurer;
import com.mountain.web.configuration.security.customize.CustomizeAccessDeniedHandler;
import com.mountain.web.configuration.security.customize.CustomizeAuthenticationEntryPoint;
import com.mountain.web.configuration.security.customize.CustomizeAuthenticationFailureHandler;
import com.mountain.web.configuration.security.customize.CustomizeAuthenticationSuccessHandler;
import com.mountain.web.configuration.security.filter.JwtAuthenticationTokenFilter;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;

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
    private final UserDetailsService userDetailsService;
    private final RbacAuthorizationManager rbacAuthorizationManager;
    private final List<AuthenticationProvider> authenticationProviders;
    private final CustomizeAuthenticationSuccessHandler successHandler;
    private final CustomizeAuthenticationFailureHandler failureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/**")
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(mountainProperties.getAuth().permitUrls()).permitAll()
                        .anyRequest().access(rbacAuthorizationManager))
                .with(new UsernamePasswordLoginConfigurer<>(), configurer ->
                        configurer.successHandler(successHandler).failureHandler(failureHandler))
                .exceptionHandling(exception ->
                    exception.authenticationEntryPoint(new CustomizeAuthenticationEntryPoint()).accessDeniedHandler(new CustomizeAccessDeniedHandler()))
                .addFilterAt(new JwtAuthenticationTokenFilter(mountainProperties, userDetailsService), BasicAuthenticationFilter.class)
                .authenticationManager(new CustomizeAuthenticationManager(authenticationProviders));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
