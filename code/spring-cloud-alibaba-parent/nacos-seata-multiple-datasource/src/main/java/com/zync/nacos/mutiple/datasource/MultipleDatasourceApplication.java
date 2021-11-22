package com.zync.nacos.mutiple.datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * seata多数据源
 *
 * 需排除DataSource自动配置类，否则会默认自动配置，不会使用我们自定义的DataSource，并且启动报错循环依赖
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/19 17:30
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MultipleDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipleDatasourceApplication.class, args);
    }
}
