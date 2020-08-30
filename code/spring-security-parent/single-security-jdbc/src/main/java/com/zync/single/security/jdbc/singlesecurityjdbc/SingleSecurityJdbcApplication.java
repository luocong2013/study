package com.zync.single.security.jdbc.singlesecurityjdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luoc
 */
@SpringBootApplication
@MapperScan("com.zync.single.security.jdbc.singlesecurityjdbc.web.mapper")
public class SingleSecurityJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SingleSecurityJdbcApplication.class, args);
    }

}
