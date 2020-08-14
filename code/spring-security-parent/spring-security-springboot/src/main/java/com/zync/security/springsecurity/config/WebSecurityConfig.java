package com.zync.security.springsecurity.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Spring Security配置
 * @date 2020/7/11 16:12
 */
@SpringBootConfiguration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // 方法授权
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 定义用户信息服务（查询用户信息） 方式一
     */
    //@Override
    //protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.inMemoryAuthentication()
    //            .withUser("zhangsan")
    //            .password("123")
    //            .authorities("p1")
    //            .and()
    //            .withUser("lisi")
    //            .password("456")
    //            .authorities("p2");
    //}

    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 明文
        //return NoOpPasswordEncoder.getInstance();

        // BCrypt
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置安全拦截机制（最重要）
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                // 关闭防止CSRF攻击
                csrf().disable()
                .authorizeRequests()
                //.antMatchers("/r/r1").hasAuthority("p1")
                //.antMatchers("/r/r2").hasAuthority("p2")
                // 所有/r/**的请求必须认证通过
                .antMatchers("/r/**").authenticated()
                // 除了/r/**，其他的请求可以访问
                .anyRequest().permitAll()
                .and()
                // 允许表单登录
                .formLogin()
                // 自定义登录页面
                .loginPage("/login-view")
                // 自定义登录受理的地址
                .loginProcessingUrl("/login")
                // 自定义登录成功的页面地址
                .successForwardUrl("/login-success")
                .and()
                .logout()
                // 自定义触发退出操作的URL
                .logoutUrl("/logout")
                // 自定义退出之后跳转的URL（默认是 /login?logout）
                .logoutSuccessUrl("/login-view?logout")
            .and()
                .sessionManagement()
                // 会话创建方式
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                // session超时后，设置跳转的路径
                .invalidSessionUrl("/login-view?error=INVALID_SESSION");
    }
}
