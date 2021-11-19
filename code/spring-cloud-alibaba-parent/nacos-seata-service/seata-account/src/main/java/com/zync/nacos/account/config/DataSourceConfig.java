package com.zync.nacos.account.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/18 23:35
 */
@Configuration
public class DataSourceConfig {

    /**
     * 一定要配置DataSource
     * 要不然 {@link io.seata.spring.boot.autoconfigure.SeataDataSourceAutoConfiguration} 不会自动配置
     * @param properties
     * @return
     */
    @Bean
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

}
