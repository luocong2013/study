package com.zync.serviceconsumerhystrix.feign;

import org.springframework.stereotype.Component;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption HystrixFeignClient熔断方法
 * @date 2019/4/21 21:20
 */
@Component
public class HystrixFeignClientFallback implements HystrixFeignClient {

    @Override
    public String hello(String name) {
        return "Feign客户端访问失败";
    }
}
