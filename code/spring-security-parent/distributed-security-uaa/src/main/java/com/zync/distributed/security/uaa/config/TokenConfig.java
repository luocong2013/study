package com.zync.distributed.security.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.sql.DataSource;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 令牌配置
 * @date 2020/8/16 13:18
 */
@SpringBootConfiguration
public class TokenConfig {

    /**
     * 若项目使用数据库保存 token 等信息所以要配置数据源
     */
    @Autowired
    private DataSource dataSource;

    @Bean
    public TokenStore tokenStore() {
        // 使用内存中的 token store
         return new InMemoryTokenStore();

        // 使用JdbcToken Store
        //return new JdbcTokenStore(dataSource);
    }
}
