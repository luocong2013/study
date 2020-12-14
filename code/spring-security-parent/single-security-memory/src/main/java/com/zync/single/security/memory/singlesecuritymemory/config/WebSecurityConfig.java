package com.zync.single.security.memory.singlesecuritymemory.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Spring Security配置
 * @date 2020/8/16 21:03
 */
@SpringBootConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 角色继承，admin角色的人员有user角色
     * TODO 暂时不是这样的效果，后续来排查  1. 引入Oauth2的原因？
     * @return
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }

    /**
     * 创建用户，方式二
     * @return
     */
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("123456")).roles("admin").build());
        manager.createUser(User.withUsername("user").password(passwordEncoder().encode("123456")).roles("user").build());
        return manager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 创建用户：
        // 方式一
        //auth.inMemoryAuthentication()
        //        .withUser("admin").password(passwordEncoder().encode("123456")).roles("admin")
        //        .and()
        //        .withUser("user").password(passwordEncoder().encode("123456")).roles("user");
        // 方式二
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }


    /**
     * 注意：
     * 在这个 Spring Security 配置和资源服务器配置中，都涉及到了 HttpSecurity。
     * 其中 Spring Security 中的配置优先级高于资源服务器中的配置，
     * 即: 请求地址先经过 Spring Security 的 HttpSecurity，再经过资源服务器的 HttpSecurity。
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().anyRequest()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .and()
                // Basic提交
                .httpBasic()
                .and()
                // 关跨域保护
                .csrf().disable();
    }
}
