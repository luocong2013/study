package com.zync.nacos.mutiple.datasource.config;

import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * DataSource配置
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/19 17:45
 */
@SpringBootConfiguration
public class DataSourceProxyConfig {



    @Bean("originStock")
    @ConfigurationProperties(prefix = "spring.datasource.stock")
    public DataSource dataSourceStock() {
        return new HikariDataSource();
    }

    @Bean("originPay")
    @ConfigurationProperties("spring.datasource.pay")
    public DataSource dataSourcePay() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("originOrder")
    @ConfigurationProperties("spring.datasource.order")
    public DataSource dataSourceOrder() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("stock")
    public DataSourceProxy stockDataSourceProxy(@Qualifier("originStock") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean("pay")
    public DataSourceProxy payDataSourceProxy(@Qualifier("originPay") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean("order")
    public DataSourceProxy orderDataSourceProxy(@Qualifier("originOrder") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Primary
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("stock") DataSource dataSourceStock,
                                        @Qualifier("pay") DataSource dataSourcePay,
                                        @Qualifier("order") DataSource dataSourceOrder) {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(8);
        dataSourceMap.put(DataSourceKey.STOCK.name(), dataSourceStock);
        dataSourceMap.put(DataSourceKey.PAY.name(), dataSourcePay);
        dataSourceMap.put(DataSourceKey.ORDER.name(), dataSourceOrder);

        dynamicRoutingDataSource.setDefaultTargetDataSource(dataSourceOrder);
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        return dynamicRoutingDataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dynamicDataSource") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

}
