package com.zync.example.springbootdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 01396453(luocong)
 * @version 1.0.0
 * @description 异步请求线程池配置类
 * @date 2020/7/7 12:00
 */
@SpringBootConfiguration
public class RequestAsyncPoolConfig implements WebMvcConfigurer {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // 处理callable超时
        configurer.setDefaultTimeout(60 * 1000);
        configurer.setTaskExecutor(threadPoolTaskExecutor);
        configurer.registerCallableInterceptors(timeoutCallableProcessingInterceptor());
    }

    /**
     * 异步处理拦截
     * @return
     */
    @Bean
    public TimeoutCallableProcessingInterceptor timeoutCallableProcessingInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }

    /**
     * 异步线程池
     * @return
     */
    //@Bean
    //public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
    //    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    //    executor.setCorePoolSize(5);
    //    executor.setMaxPoolSize(10);
    //    executor.setThreadNamePrefix("NEAL");
    //    executor.setQueueCapacity(20000000);
    //    return executor;
    //}
}
