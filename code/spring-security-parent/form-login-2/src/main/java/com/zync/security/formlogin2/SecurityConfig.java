package com.zync.security.formlogin2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author luocong
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("javaboy")
                .password("123").roles("admin");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**","/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                // 设置登录页面地址为：login.html
                .loginPage("/login.html")
                // 设置登录接口地址为：doLogin
                .loginProcessingUrl("/doLogin")
                // 表单用户名参数
                .usernameParameter("name")
                // 表单密码参数
                .passwordParameter("passwd")
                // 登录成功后的回调方法（和 successForwardUrl 二选一，defaultSuccessUrl("/index", true) 和 successForwardUrl 功能一致），登录后跳转到指定页面或者前面访问的页面
                .defaultSuccessUrl("/index1")
                // 登录成功后的回调方法（和 defaultSuccessUrl 二选一），登录后一律跳转到指定页面
                //.successForwardUrl("/index2")
                // 登录失败后的回调方法（和 failureForwardUrl 二选一），登录失败之后，会发生重定向，默认是：/login?error
                .failureUrl("/f1")
                // 登录失败后的回调方法（和 failureUrl 二选一），登录失败之后会发生服务端跳转
                //.failureForwardUrl("/f2")
                .permitAll()
                .and()
                .logout()
                // 注销登录接口
                .logoutUrl("/logout")
                // logoutRequestMatcher 方法不仅可以修改注销 URL，还可以修改请求方式，实际项目中，这个方法和 logoutUrl 任意设置一个即可
                //.logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))
                // 注销成功后要跳转的页面，默认是：/login?logout
                .logoutSuccessUrl("/logoutSuccess")
                // 清除 cookie
                .deleteCookies()
                // 清除认证信息
                .clearAuthentication(true)
                // 使 HttpSession 失效，默认可以不用配置，默认就会清除
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .csrf().disable();
    }
}
