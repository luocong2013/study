package com.zync.emqx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author luocong
 */
@SpringBootApplication(scanBasePackages = {"com.zync.emqx.server"})
public class EmqxServerApp {

    public static void main(String[] args) {
        SpringApplication.run(EmqxServerApp.class, args);
    }

}
