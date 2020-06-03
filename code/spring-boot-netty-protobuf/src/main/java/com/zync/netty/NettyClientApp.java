package com.zync.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luocong
 * @version V1.0.0
 * @description Netty客户端
 * @date 2020/6/3 16:27
 */
@SpringBootApplication(scanBasePackages = {"com.zync.netty.client"})
public class NettyClientApp {

    public static void main(String[] args) {
        SpringApplication.run(NettyClientApp.class);
    }
}
