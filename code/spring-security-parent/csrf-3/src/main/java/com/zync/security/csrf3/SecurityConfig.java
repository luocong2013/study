package com.zync.security.csrf3;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author luocong
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                //.loginPage("/login.html")
                .successHandler((req,resp,authentication)->{
                    resp.getWriter().write("success");
                })
                .permitAll()
                .and()
                // 前后端分离项目，将 _csrf 放在Cookie中返回前端
                //.csrf().csrfTokenRepository(new LazyCsrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                ;
    }
}
