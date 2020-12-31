package com.zync.security.session3;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author luocong
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                // 登录成功后会话创建策略
                // migrateSession 表示在登录成功之后，创建一个新的会话，然后将旧的 session 中的信息复制到新的 session 中，「默认」。在用户匿名访问的时候是一个 sessionid，当用户成功登录之后，又是另外一个 sessionid，这样就可以有效避免会话固定攻击
                // none 表示不做任何事情，继续使用旧的 session
                // changeSessionId 表示 session 不变，但是会修改 sessionid，这实际上用到了 Servlet 容器提供的防御会话固定攻击
                // newSession 表示登录后创建一个新的 session
                .sessionFixation().migrateSession()
                .and()
                .csrf().disable();
    }
}
