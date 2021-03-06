package com.zync.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luocong
 * @version V1.0.0
 * @description Netty服务端
 * @date 2020/6/3 16:28
 */
@SpringBootApplication(scanBasePackages = {"com.zync.netty.server"})
public class NettyServerApp {

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApp.class);
    }
}
