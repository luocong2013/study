package com.zync.log4j2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * log4j2 springboot启动类
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/25 15:06
 */
@SpringBootApplication
public class SpringBootLog4j2Application {

    public static void main(String[] args) {
        // 下面语句使得日志输出使用异步处理，减少输出日志对性能的影响
        //System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        SpringApplication.run(SpringBootLog4j2Application.class, args);
    }
}
