package com.zync.serviceconsumer.configure;

import feign.Contract;
import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption feign相关配置
 *             Feign默认使用的是SpringMVC的注解，如果要使用Feign原生注解需要配置
 * @date 2019/4/21 18:23
 */
//@SpringBootConfiguration
public class FeignConfigure {

    /********************使用Feign原生注解需要配置这两个bean start***************************/
    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }

    /**
     * 改变Feign日志级别
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    /********************使用Feign原生注解需要配置这两个bean end***************************/


    /******************指定请求的eureka的url需要指定eureka的账号信息 start******************/
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("luocong", "pwd123456");
    }
    /******************指定请求的eureka的url需要指定eureka的账号信息 end******************/

}
