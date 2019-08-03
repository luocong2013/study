package com.zync.ibed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author luoc
 * @date 2019/06/16 19:38
 */
@MapperScan(basePackages = {"com.zync.ibed.mapper"})
@SpringBootApplication
public class IbedApplication {

    public static void main(String[] args) {
        SpringApplication.run(IbedApplication.class, args);
    }

}
