package com.zync.example.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author luocong
 */
@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication
@EnableAsync
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }

    //@Primary
    //@Bean
    //public Executor executor() {
    //    ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8, 2, TimeUnit.SECONDS,
    //            new LinkedBlockingQueue<>(100), new BasicThreadFactory.Builder().namingPattern("example-pool-%d").daemon(false).build());
    //    return executor;
    //}

}
