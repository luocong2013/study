package com.zync.distributed.security.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

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
     * 认证管理器 webSecurityConfig 中配置的AuthenticationManager
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
     * token存储策略
     */
    @Autowired
    private TokenStore tokenStore;

    /**
     * 客户端详情服务
     */
    @Autowired
    private ClientDetailsService clientDetailsService;

    /**
     * webSecurityConfig 中配置的 passwordEncoder(使用MD5加密)
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 对 oauth_client_details 表的一些操作
     * @return ClientDetailsService
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 授权码服务
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        // 采用内存方式
        return new InMemoryAuthorizationCodeServices();
    }

    /**
     * 配置令牌服务
     * @return
     */
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 客户端信息服务
        tokenServices.setClientDetailsService(clientDetailsService);
        // 令牌存储策略
        tokenServices.setTokenStore(tokenStore);
        // 是否产生刷新令牌
        tokenServices.setSupportRefreshToken(true);
        // access_token 过期时间：20s
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.SECONDS.toSeconds(20));
        // refresh_token 过期时间，默认不过期
        // 刷新令牌是否可重用
        //tokenServices.setReuseRefreshToken(true);
        //tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.SECONDS.toSeconds(20));
        return tokenServices;
    }

    /**
     * 配置客户端详情服务，客户端详情服务在这里进行初始化
     * 可以把客户端详情写死在这里或者通过数据库来存储调取详情信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 内存方式
        clients.inMemory()
                // 客户端ID
                .withClient("c1")
                // 客户端密钥
                .secret(passwordEncoder.encode("secret"))
                // 资源列表
                .resourceIds("res1")
                // 授权方式: 授权码模式，简化模式，密码模式，客户端模式（刷新token）
                .authorizedGrantTypes("authorization_code", "implicit", "password", "client_credentials", "refresh_token")
                // 允许的授权范围
                .scopes("all")
                // false 跳转到授权页面
                .autoApprove(false)
                // 验证回调地址
                .redirectUris("http://www.baidu.com/");


        // jdbc方式
        //clients.jdbc(dataSource);
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

    /**
     * 配置令牌端点的安全约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                // 允许check_token访问
                .checkTokenAccess("permitAll()")
                // 允许表单登录
                .allowFormAuthenticationForClients();
    }

    /**
     * 配置令牌（token）的访问断点和令牌服务（token services）
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 密码模式需要
                .authenticationManager(authenticationManager)
                // 授权码模式需要
                .authorizationCodeServices(authorizationCodeServices())
                // 令牌服务
                .tokenServices(tokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }


}
