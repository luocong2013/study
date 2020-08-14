package com.zync.distributed.security.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 这个注解告诉 Spring 这个应用是 OAuth2 的授权服务器
 *              提供 /oauth/authorize,/oauth/token,/oauth/check_token,/oauth/confirm_access,/oauth/error
 * @date 2020/8/2 22:29
 */
@SpringBootConfiguration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    /**
     * webSecurityConfig 中配置的AuthenticationManager
     */
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    /**
     * 此项目使用数据库保存 token 等信息所以要配置数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * webSecurityConfig 中配置的 userDetailsService
     */
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    /**
     * webSecurityConfig 中配置的 passwordEncoder(使用MD5加密)
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public TokenStore tokenStore() {
        // 使用内存中的 token store
        // return new InMemoryTokenStore();

        // 使用JdbcToken Store
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 对 oauth_client_details 表的一些操作
     * @return ClientDetailsService
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 配置TokenServices参数
     * @return
     */
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetails());
        // access_token 过期时间：20s
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.SECONDS.toSeconds(20));
        // refresh_token 过期时间，默认不过期
        //tokenServices.setReuseRefreshToken(true);
        //tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.SECONDS.toSeconds(20));
        return tokenServices;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
                // 请求token的时候会将client_id,client_secret等信息保存到 oauth_client_details 表中，所以需要手动创建该表
                // 注意：以下注释的代码在请求了一次 token 之后则可以注释掉，否则如果不换 client 名字的话会因为主键冲突无法插入 client 信息。也可以一开始就注释，手动添加记录到数据库
                //.withClient("clientId")
                //.secret(passwordEncoder.encode("123456"))
                //.authorizedGrantTypes("authorization_code", "refresh_token", "password", "implicit") // 四种认证模式
                //.scopes("all")
                //.authorities("ROLE_admin", "ROLE_user")
                //.redirectUris("http://www.baidu.com/")
                //.accessTokenValiditySeconds(120000)
                //.refreshTokenValiditySeconds(50000);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                // 允许check_token访问
                .checkTokenAccess("permitAll()")
                // 允许表单登录
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .userDetailsService(userDetailsService)
                .tokenServices(tokenServices())
                .setClientDetailsService(clientDetails());
    }
}
