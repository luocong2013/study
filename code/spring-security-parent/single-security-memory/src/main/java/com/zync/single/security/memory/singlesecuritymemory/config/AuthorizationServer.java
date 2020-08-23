package com.zync.single.security.memory.singlesecuritymemory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 认证服务
 * @date 2020/8/16 21:13
 */
@SpringBootConfiguration
// 开启认证服务
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // ① 授权码模式
                // 客户端 client_id
                .withClient("client")
                // 客户端 secret
                .secret(passwordEncoder.encode("secret"))
                // 授权类型: 授权码
                .authorizedGrantTypes("authorization_code")
                // 范围
                .scopes("app")
                // 重定向地址
                .redirectUris("http://www.baidu.com/")
                // ② 密码模式
                .and()
                .withClient("client_password")
                .secret(passwordEncoder.encode("123456"))
                .authorizedGrantTypes("password", "client_credentials", "refresh_token")
                // 配置资源ID
                .resourceIds("rid")
                .scopes("all")
                .redirectUris("http://www.cuit.edu.cn/")
                // ③ 客户端模式
                .and()
                .withClient("client_client_credentials")
                .secret(passwordEncoder.encode("123456"))
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .resourceIds("rid")
                .scopes("all")
                .redirectUris("http://www.cuit.edu.cn/")
                // ④ 简化模式
                .and()
                .withClient("client_implicit")
                .secret(passwordEncoder.encode("123456"))
                .authorizedGrantTypes("implicit", "refresh_token")
                .resourceIds("rid")
                .scopes("all")
                .redirectUris("http://www.cuit.edu.cn/");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 密码模式需要
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许表单认证
        security.allowFormAuthenticationForClients();
    }
}
