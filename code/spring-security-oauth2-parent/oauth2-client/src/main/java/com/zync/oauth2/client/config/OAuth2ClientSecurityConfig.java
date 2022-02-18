package com.zync.oauth2.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The OAuth2.0 Client Configuration.
 *
 * @author luocong
 * @version v1.0
 * @date 2022/2/15 16:17
 */
@EnableWebSecurity(debug = true)
public class OAuth2ClientSecurityConfig {

    /**
     * 放开对{@code redirect_uri}的访问，否则会出现{@code 403}，授权服务器需要回调该地址
     *
     * @param httpSecurity the http security
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain oauth2ClientSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/foo/bar").anonymous()
                .anyRequest().authenticated()
                .and()
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/messaging-client-oidc"))
                // OAuth2 Client
                .oauth2Client();
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/webjars/**");
    }
}
