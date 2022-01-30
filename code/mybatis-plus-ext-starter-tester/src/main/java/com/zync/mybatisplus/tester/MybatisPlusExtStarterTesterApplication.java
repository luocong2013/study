package com.zync.mybatisplus.tester;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luocong
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.zync.**.mapper"})
public class MybatisPlusExtStarterTesterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusExtStarterTesterApplication.class, args);
    }

}
